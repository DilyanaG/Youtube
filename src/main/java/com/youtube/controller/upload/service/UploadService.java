package com.youtube.controller.upload.service;

import static java.util.Arrays.asList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.user.IUserDAO;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.pojo.User;
import com.youtube.model.pojo.Video;

@Service
public class UploadService {

	private static final Random RANDOM = new Random();
	private static final String UPLOADS = "uploads";
	private static final String IMAGES = "images";
	private static final String VIDEOS = "videos";
	private static final String PROFILES = "profiles";
	
	private static final Set<String> SUPPORTED_IMAGE_TYPES = new HashSet<>(asList("jpg", "jpeg", "png"));
	private static final Set<String> SUPPORTED_VIDEO_TYPES = new HashSet<>(asList("mp4", "avi"));

	@Autowired
	private IVideoDAO videoDAO;

	@Autowired
	private IUserDAO userDAO;

	public boolean addVideo(MultipartFile file, String title, String description, int channelId, String realPath)
			throws IllegalInputException, IOException, JCodecException, DataBaseException {

		if (file.isEmpty()) {
			throw new IllegalInputException("PLEASE UPLOAD VIDEO!");
		}
		if (title == null || title.length() < 2) {
			throw new IllegalInputException("PLEASE ENTER CORRECT TITLE FOR  VIDEO!");
		}
		if (description == null) {
			description = "";
		}

		String contentType = file.getContentType().split("/")[1];
		if (!SUPPORTED_VIDEO_TYPES.contains(contentType)) {
			throw new IllegalInputException("The allowed file types are: mp4, avi!");
		}
		
		final String uriId = UUID.randomUUID().toString();
		final Path videoPath = Paths.get(realPath, UPLOADS, VIDEOS, uriId + "." + contentType);
		final Path photoPath = Paths.get(realPath, UPLOADS, IMAGES, uriId + ".png");

		String videoUrl = UPLOADS + "/" + VIDEOS + "/" + uriId + "." + contentType;
		String photoUrl = UPLOADS + "/" + IMAGES + "/" + uriId + ".png";

		Video video = new Video(videoUrl, photoUrl, title, description);
		videoDAO.addVideo(video, channelId);

		// save video
		byte[] videoBytes = file.getBytes();
		Files.write(videoPath.toAbsolutePath(), videoBytes);

		int frameNumber = RANDOM.nextInt(100) + 100;
		Picture photo = FrameGrab.getFrameFromFile(this.convert(file), frameNumber);
		BufferedImage bufferedImage = AWTUtil.toBufferedImage(photo);

		// save video png
		ImageIO.write(bufferedImage, "png", photoPath.toFile());
		return true;
	}

	// convert multiPart file to file
	private File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public User changeProfilePicture(MultipartFile file, String realPath, String username)
			throws IllegalInputException, DataBaseException, IOException {
		if (file.isEmpty()) {
			throw new IllegalInputException("PLEASE UPLOAD PHOTO!");
		}

		String contentType = file.getContentType().split("/")[1];
		if (!SUPPORTED_IMAGE_TYPES.contains(contentType)) {
			throw new IllegalInputException("The allowed file types are: jpg, jpeg, png!");
		}

		final String uriId = UUID.randomUUID().toString();
		final Path photoPath = Paths.get(realPath, UPLOADS, PROFILES, uriId + "." + contentType);
		String photoUrl = UPLOADS + "/" + PROFILES + "/" + uriId + "." + contentType;

		User user = userDAO.getUserByUserName(username);
		String oldPhotoUrl = user.getPhotoURL();

		// Save Photo
		byte[] photoBytes = file.getBytes();
		Files.write(photoPath.toAbsolutePath(), photoBytes);

		// Delete old profile photo
		if (user.getPhotoURL() != null) {
			Path oldPhototPath = Paths.get(realPath, oldPhotoUrl);
			Files.delete(oldPhototPath);
		}

		userDAO.updateProfilePicture(photoUrl, user.getUserId());
		User updatedUser = userDAO.getUserByUserName(username);
		return updatedUser;
	}

}
