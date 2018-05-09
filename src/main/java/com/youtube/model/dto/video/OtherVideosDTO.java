package com.youtube.model.dto.video;

public class OtherVideosDTO {

	private int videoId;
	private int channelId;
	private String username;
	private String profilePictureUrl;
	private String title;
	private int views;
	
	
	public OtherVideosDTO(int videoId, int channelId, String username, String profilePictureUrl, String title,
			int views) {
		this.videoId = videoId;
		this.channelId = channelId;
		this.username = username;
		this.profilePictureUrl = profilePictureUrl;
		this.title = title;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	
	
}
