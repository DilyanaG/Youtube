package com.youtube.controller.upload.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
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
import com.youtube.model.dao.video.VideoDAO;
import com.youtube.model.pojo.Video;

@Service
public class UploadService {

	private static final Random RANDOM = new Random();
	private static final String UPLOADS = "uploads";//uploads
	private static final String IMAGES = "images";
	private static final String VIDEOS = "videos";

	@Autowired
	private VideoDAO videoDAO;

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

		// file.getContentType() to check if it is a video format

		final String uriId = UUID.randomUUID().toString();
		final Path photoPath = Paths.get(realPath, UPLOADS, IMAGES, uriId + ".png");
		final Path videoPath = Paths.get(realPath, UPLOADS, VIDEOS, uriId + ".mp4");

		String videoUrl = UPLOADS + "/" + VIDEOS + "/" + uriId + ".mp4";
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
	public File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}
