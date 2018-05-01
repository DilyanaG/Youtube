package com.youtube.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
//import org.jcodec.api.FrameGrab;
//import org.jcodec.common.model.Picture;
//import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadController {

	@RequestMapping(value = {"/upload","/videos/upload"}, method = RequestMethod.GET)
	public String index() {
		return "upload";
	}

	 private static String VIDEO_UPLOADED_FOLDER = "D://uploads//videos//";
	 private static String IMAGE_UPLOADED_FOLDER = "D://uploads//images//";
 @RequestMapping(value = "/upload", method = RequestMethod.POST)
 public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception {
	   System.out.println(request.getParameter("title"));
	   System.out.println(request.getParameter("description"));
         System.out.println("braoo ti doide to tuka ");
     if (file.isEmpty()) {
         redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
         throw new Exception();
        // return "redirect:upload";
     }

     try {
         
         
     
         byte[] bytes = file.getBytes();
         Path path = Paths.get(VIDEO_UPLOADED_FOLDER + "video.mp4");
         
         Files.write(path, bytes);
         int frameNumber = 200;
	        Picture picture = FrameGrab.getFrameFromFile(this.convert(file), frameNumber);
			BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
			//Path path2 = Paths.get(UPLOADED_FOLDER + "photo.png");
	        
      	ImageIO.write(bufferedImage, "png", new File(IMAGE_UPLOADED_FOLDER + "photo.png"));

         redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

     } catch (Exception e) {
         e.printStackTrace();
     }

     return "index";
 }


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
