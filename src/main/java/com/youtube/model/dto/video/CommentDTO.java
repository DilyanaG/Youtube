package com.youtube.model.dto.video;

public class CommentDTO {
	private int commentId;
	private int channelId;
	private String username;
	private String profilePicture;
	private String message;
	private String uploadDate;
	
	public CommentDTO(int commentId, int channelId, String username, String profilePicture, String message, String uploadDate) {
		this.channelId = channelId;
		this.username = username;
		this.profilePicture = profilePicture;
		this.message = message;
		this.uploadDate = uploadDate;
		this.commentId = commentId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

}
