package com.youtube.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


public class UploadController {
  
	
	@Controller
	public class FileUploadController {

		@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
		public String uploadFileHandler(@RequestParam("name") String name,
				@RequestParam("file") MultipartFile file) {

			if (!file.isEmpty()) {
				System.out.println("FAILA DOIDE TUKA");
				try {
					byte[] bytes = file.getBytes();

					// Creating the directory to store file
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "tmpFiles");
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
//
//					logger.info("Server File Location="
//							+ serverFile.getAbsolutePath());

					return "index";
				} catch (Exception e) {
					return "You failed to upload " + name + " => " + e.getMessage();
				}
			} else {
				return "You failed to upload " + name
						+ " because the file was empty.";
			}
		}

		/**
		 * Upload multiple file using Spring Controller
		 */
//		@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
//		public @ResponseBody
//		String uploadMultipleFileHandler(@RequestParam("name") String[] names,
//				@RequestParam("file") MultipartFile[] files) {
//
//			if (files.length != names.length)
//				return "Mandatory information missing";
//
//			String message = "";
//			for (int i = 0; i < files.length; i++) {
//				MultipartFile file = files[i];
//				String name = names[i];
//				try {
//					byte[] bytes = file.getBytes();
//
//					// Creating the directory to store file
//					String rootPath = System.getProperty("catalina.home");
//					File dir = new File(rootPath + File.separator + "tmpFiles");
//					if (!dir.exists())
//						dir.mkdirs();
//
//					// Create the file on server
//					File serverFile = new File(dir.getAbsolutePath()
//							+ File.separator + name);
//					BufferedOutputStream stream = new BufferedOutputStream(
//							new FileOutputStream(serverFile));
//					stream.write(bytes);
//					stream.close();
//
//					logger.info("Server File Location="
//							+ serverFile.getAbsolutePath());
//
//					message = message + "You successfully uploaded file=" + name
//							+ "<br />";
//				} catch (Exception e) {
//					return "You failed to upload " + name + " => " + e.getMessage();
//				}
//			}
//			return message;
//		}
	}
}
