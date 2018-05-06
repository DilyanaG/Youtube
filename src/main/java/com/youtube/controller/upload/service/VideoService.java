package com.youtube.controller.upload.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.video.VideoDTO;
import com.youtube.model.pojo.Video;

@Service
public class VideoService {

	@Autowired
	private IVideoDAO videoDAO;

	public VideoDTO playVideo(int videoId) throws DataBaseException, IllegalInputException {
		videoDAO.increaseViewsForVideo(videoId);
		Video video = videoDAO.getVideoById(videoId);
		
		int channelId = video.getChannel().getChannelId();
		String username = video.getChannel().getUser().getUserName();
		String profilePictureUrl = video.getChannel().getUser().getPhotoURL();
		String videoUrl = video.getVideoUrl();
		String title = video.getTitle();
		String description = video.getDescription();
		LocalDate uploadDate = video.getUploadDate().toLocalDate();
		int views = video.getViews();
		
		int likes = videoDAO.getLikesForVideo(videoId);
		int dislikes = videoDAO.getDislikesForVideo(videoId);
		
		return new VideoDTO(videoId, channelId, username, profilePictureUrl, videoUrl, title, description, uploadDate, views, likes, dislikes);
	}
	
}
