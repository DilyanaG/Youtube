package com.youtube.model.dto.video;

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


	private int getVideoId() {
		return videoId;
	}


	private void setVideoId(int videoId) {
		this.videoId = videoId;
	}


	private int getChannelId() {
		return channelId;
	}


	private void setChannelId(int channelId) {
		this.channelId = channelId;
	}


	private String getTitle() {
		return title;
	}


	private void setTitle(String title) {
		this.title = title;
	}


	private String getPhotoUrl() {
		return photoUrl;
	}


	private void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}


	private int getViews() {
		return views;
	}


	private void setViews(int views) {
		this.views = views;
	}
	
}
