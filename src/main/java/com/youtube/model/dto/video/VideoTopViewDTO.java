package com.youtube.model.dto.video;

import org.springframework.stereotype.Component;


public class VideoTopViewDTO {
     
	private int videoId;
	private int channelId;
	private String title;
	private String photoUrl;
	private int views;
	
	
	public VideoTopViewDTO(int videoId, int channelId, String title, String photoUrl, int views) {
	
		this.videoId = videoId;
		this.channelId = channelId;
		this.title = title;
		this.photoUrl = photoUrl;
		this.views = views;
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


	
}
