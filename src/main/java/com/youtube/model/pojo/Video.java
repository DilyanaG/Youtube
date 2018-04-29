package com.youtube.model.pojo;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;


public class Video {

	private static final String DEFAULT_DESCRIPTION = "";
	private static final int DEFAULT_DISLIKES_TO_VIDEO = 0;
	private static final int DEFAULT_LIKES_TO_VIDEO = 0;
	private static final int DEFAULT_VIEWS_TO_VIDEO = 0;
    
	private int videoId;
	private final String url;
	private final Channel channel;
	private String title;
	private String description;
	private LocalDateTime uploadDate;
	private Set<String> tags;
	private int countOfLikes;
	private int countOfDislikes;
	private int views;

//	TODO remove this?
	public Video(int videoId, String url, 
				Channel channel, String title, String description, 
				LocalDateTime uploadDate,
							int countOfLikes, int countOfDislikes, int views) {
		this(url,channel,title,description);
		this.uploadDate=uploadDate;
		this.countOfLikes=countOfDislikes;
		this.countOfDislikes = countOfDislikes;
		this.views = views;
		this.videoId= videoId;
		
	}
	public Video(String url, Channel channel, String title, String description)  {
		this(url, channel, title);
		this.description = description;
		
	}
	public Video(String url, Channel channel, String title) {

		this.url = url;
		this.channel = channel;
		this.title = title;
		this.description = DEFAULT_DESCRIPTION;
		this.uploadDate = LocalDateTime.now();
		this.tags = new TreeSet<String>();
		this.countOfLikes = DEFAULT_LIKES_TO_VIDEO;
		this.countOfDislikes = DEFAULT_DISLIKES_TO_VIDEO;
		this.views = DEFAULT_VIEWS_TO_VIDEO;
	}
	

	
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
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

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	
	public int getCountOfLikes() {
		return countOfLikes;
	}

	public void setCountOfLikes(int countOfLikes) {
		this.countOfLikes = countOfLikes;
	}

	public int getCountOfDislikes() {
		return countOfDislikes;
	}

	public void setCountOfDislikes(int countOfDislikes) {
		this.countOfDislikes = countOfDislikes;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getUrl() {
		return url;
	}

	public Channel getChannel() {
		return channel;
	}

	

}
