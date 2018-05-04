package com.youtube.controller.upload.service;

import static org.mockito.Mockito.timeout;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.video.VideoDAO;
import com.youtube.model.pojo.Video;

//@Component
public class UploadService {
	
	private static String VIDEO_UPLOADED_FOLDER = "D://uploads//videos//";
	private static String IMAGE_UPLOADED_FOLDER = "D://uploads//images//";
	
	//@Autowired
	private static AtomicInteger numFile=new AtomicInteger(0);
	//@Autowired
	//private VideoDAO videoDao;
	
	
	public boolean addVideo(MultipartFile file, String title, String description, int channelId) throws IllegalInputException, IOException, JCodecException, DataBaseException {
		
		 if (file.isEmpty()) {
	    	 throw new IllegalInputException("PLEASE UPLOAD VIDEO!");
	     }
	     if(title==null||title.length()<2){
	    	 throw new IllegalInputException("PLEASE ENTER CORRECT TITLE FOR  VIDEO!");
	     }
	     if (description==null) {
	    	 description="";
	     }
	        byte[] bytes = file.getBytes();
	        int fileNumber= numFile.incrementAndGet();
	        String videoUrl=file.getOriginalFilename()+"video"+fileNumber+".mp4";
	        String photoUrl=file.getOriginalFilename().substring(0,file.getOriginalFilename().length()-4)+"photo"+fileNumber+".png";
	         Path path = Paths.get(VIDEO_UPLOADED_FOLDER+videoUrl);
	   //save video
	         Files.write(path, bytes);
	         int frameNumber = new Random().nextInt(100)+100;
		    Picture picture = FrameGrab.getFrameFromFile(this.convert(file), frameNumber);
			BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
	  //save video png
			ImageIO.write(bufferedImage, "png", new File(IMAGE_UPLOADED_FOLDER +photoUrl));
        //addvideo to DB
		    Video video = new Video(videoUrl, photoUrl, title, description);
		    VideoDAO.getInstance().addVideo(video, channelId);
		    return true;
	}
	
	//convert multiPart file to file
	public File convert(MultipartFile file) throws IOException
	 {    
	     File convFile = new File(file.getOriginalFilename());
	     convFile.createNewFile(); 
	     FileOutputStream fos = new FileOutputStream(convFile); 
	     fos.write(file.getBytes());
	     fos.close(); 
	     return convFile;
	 }




	
}
