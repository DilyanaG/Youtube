package com.youtube.model.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {
	private static final int DEFAULT_DISLIKES_TO_COMMENTS = 0;
	private static final int DEFAULT_LIKES_TO_COMMENTS = 0;

	private int commentId;
	private Channel channel;
	private Video video;
	private String content;
	private Date publicationDate;
	private int likes;
	private int dislikes;
	private List<Comment> responses;

	public Comment(int channelId, Video video, Channel channel, String content, Date publicationDate, int likes,int dislikes) {
		this(channel, content);
		this.commentId = channelId;
		this.publicationDate = publicationDate;
		this.likes = likes;
		this.dislikes = dislikes;
		this.video = video;

	}

	public Comment(Channel channel, String content) {
		this.channel = channel;
		this.content = content;
		this.publicationDate = new Date();
		this.likes = DEFAULT_LIKES_TO_COMMENTS;
		this.dislikes = DEFAULT_DISLIKES_TO_COMMENTS;
		this.responses = new ArrayList<Comment>();
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Video getVideo() {
		return video;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommmentId(int channelId) {
		this.commentId = channelId;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
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

	public List<Comment> getResponses() {
		return responses;
	}

	public void setResponses(List<Comment> responses) {
		this.responses = responses;
	}
}
