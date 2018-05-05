package com.youtube.model.dto.video;

import org.springframework.stereotype.Component;

import com.youtube.model.pojo.Video;


public class VideoTopViewDTO {
     
	private int videoId;
	private int channelId;
	private String username;
	private String title;
	private String photoUrl;
	private int views;
	
	
	public VideoTopViewDTO(Video v) {
	
		this.videoId = v.getVideoId();
		this.channelId = v.getChannel().getChannelId();
		this.username=v.getChannel().getUser().getUserName();
		this.title = v.getTitle();
		this.photoUrl = v.getPhotoUrl();
		this.views = v.getViews();
	}


	public int getVideoId() {
		return videoId;
	}


	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}


	public int getChannelId() {
		return channelId;
	}


	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getPhotoUrl() {
		return photoUrl;
	}


	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}


	public int getViews() {
		return views;
	}


	public void setViews(int views) {
		this.views = views;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	
}
