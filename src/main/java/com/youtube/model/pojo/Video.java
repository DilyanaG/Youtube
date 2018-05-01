package com.youtube.model.pojo;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

public class Video {

	private static final String DEFAULT_DESCRIPTION = "";
	private static final int DEFAULT_DISLIKES_TO_VIDEO = 0;
	private static final int DEFAULT_LIKES_TO_VIDEO = 0;
	private static final int DEFAULT_VIEWS_TO_VIDEO = 0;

	private int videoId;
	private Channel channel;
	private String videoUrl;
	private String photoUrl;
	private String title;
	private LocalDateTime uploadDate;
	private String description;
	private int views;
	private Set<Tag> tags;
	private int likes;
	private int dislikes;

	public Video() {
		super();
	}

	public Video(int videoId, Channel channel, String videoUrl, String photoUrl, String title, LocalDateTime uploadDate,
			String description, int views, int likes, int dislikes) {
		this(videoId, channel, videoUrl, photoUrl, title, description);
		
		this.uploadDate = uploadDate;
		this.likes = likes;
		this.dislikes = dislikes;
		this.views = views;
	}

	public Video(int videoId, Channel channel, String videoUrl, String photoUrl, String title, String description) {
		this(videoId, channel, videoUrl, photoUrl, title);
		this.description = description;

	}

	public Video(int videoId, Channel channel, String videoUrl, String photoUrl, String title) {
		this.videoId = videoId;
		this.channel = channel;
		this.videoUrl = videoUrl;
		this.photoUrl = photoUrl;
		this.title = title;
		this.uploadDate = LocalDateTime.now();
		this.description = DEFAULT_DESCRIPTION;
		this.likes = DEFAULT_LIKES_TO_VIDEO;
		this.dislikes = DEFAULT_DISLIKES_TO_VIDEO;
		this.views = DEFAULT_VIEWS_TO_VIDEO;
		this.tags = new TreeSet<>();
	}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + dislikes;
		result = prime * result + likes;
		result = prime * result + ((photoUrl == null) ? 0 : photoUrl.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((uploadDate == null) ? 0 : uploadDate.hashCode());
		result = prime * result + videoId;
		result = prime * result + ((videoUrl == null) ? 0 : videoUrl.hashCode());
		result = prime * result + views;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Video other = (Video) obj;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dislikes != other.dislikes)
			return false;
		if (likes != other.likes)
			return false;
		if (photoUrl == null) {
			if (other.photoUrl != null)
				return false;
		} else if (!photoUrl.equals(other.photoUrl))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (uploadDate == null) {
			if (other.uploadDate != null)
				return false;
		} else if (!uploadDate.equals(other.uploadDate))
			return false;
		if (videoId != other.videoId)
			return false;
		if (videoUrl == null) {
			if (other.videoUrl != null)
				return false;
		} else if (!videoUrl.equals(other.videoUrl))
			return false;
		if (views != other.views)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Video [videoId=");
		builder.append(videoId);
		builder.append(", channel=");
		builder.append(channel);
		builder.append(", videoUrl=");
		builder.append(videoUrl);
		builder.append(", photoUrl=");
		builder.append(photoUrl);
		builder.append(", title=");
		builder.append(title);
		builder.append(", uploadDate=");
		builder.append(uploadDate);
		builder.append(", description=");
		builder.append(description);
		builder.append(", views=");
		builder.append(views);
		builder.append(", tags=");
		builder.append(tags);
		builder.append(", likes=");
		builder.append(likes);
		builder.append(", dislikes=");
		builder.append(dislikes);
		builder.append("]");
		return builder.toString();
	}

	
}
