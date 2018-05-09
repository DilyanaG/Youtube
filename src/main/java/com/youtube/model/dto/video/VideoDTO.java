package com.youtube.model.dto.video;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VideoDTO {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	private int videoId;
	private int channelId;
	private String username;
	private String profilePictureUrl;
	private String videoUrl;
	private String title;
	private String description;
	private String uploadDate;
	private int views;
	private int likes;
	private int dislikes;
	private List<CommentDTO> comments;

	public VideoDTO(int videoId, int channelId, String username, String profilePictureUrl, String videoUrl, String title,
			String description, LocalDate uploadDate, int views, int likes, int dislikes, List<CommentDTO> comments) {
		
		this.videoId = videoId;
		this.channelId = channelId;
		this.username = username;
		this.profilePictureUrl = profilePictureUrl;
		this.videoUrl = videoUrl;
		this.title = title;
		this.description = description;
		this.uploadDate = uploadDate.format(formatter);
		this.views = views;
		this.likes = likes;
		this.dislikes = dislikes;
		this.comments = comments;
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

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

}
