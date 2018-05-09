package com.youtube.model.pojo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Comment {
	private static final int DEFAULT_DISLIKES_TO_COMMENTS = 0;
	private static final int DEFAULT_LIKES_TO_COMMENTS = 0;
	private static final Comment DEFAULT_PARENT_COMMENTS = null;

	private int commentId;
	private Channel channel;
	private Video video;
	private String content;
	private LocalDateTime publicationDate;
	private int likes;
	private int dislikes;
	private List<Comment> responses;
	private Comment parentComment;
	
	public Comment(){
	}

	public Comment(int commentId, Video video, Channel channel, String content, LocalDateTime publicationDate, int likes,int dislikes) {
		this(commentId, channel, content, video);
		this.publicationDate = publicationDate;
		this.likes = likes;
		this.dislikes = dislikes;
		this.responses = new ArrayList<Comment>(responses);
	}
	
	public Comment(int commentId, Channel channel, String content, Video video, Comment parentComment) {
		this(commentId, channel, content, video);
		this.parentComment = parentComment;
		this.publicationDate = LocalDateTime.now();
		this.likes = DEFAULT_LIKES_TO_COMMENTS;
		this.dislikes = DEFAULT_DISLIKES_TO_COMMENTS;
		this.responses = new ArrayList<Comment>();
	}

	public Comment(int commentId, Channel channel, String content, Video video) {
		this.commentId = commentId;
		this.channel = channel;
		this.content = content;
		this.video = video;
		this.publicationDate = LocalDateTime.now();
		this.parentComment = DEFAULT_PARENT_COMMENTS;
		this.likes = DEFAULT_LIKES_TO_COMMENTS;
		this.dislikes = DEFAULT_DISLIKES_TO_COMMENTS;
		this.responses = new ArrayList<Comment>();
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDateTime publicationDate) {
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

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public static int getDefaultDislikesToComments() {
		return DEFAULT_DISLIKES_TO_COMMENTS;
	}

	public static int getDefaultLikesToComments() {
		return DEFAULT_LIKES_TO_COMMENTS;
	}

	public static Comment getDefaultParentComments() {
		return DEFAULT_PARENT_COMMENTS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + commentId;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + dislikes;
		result = prime * result + likes;
		result = prime * result + ((parentComment == null) ? 0 : parentComment.hashCode());
		result = prime * result + ((publicationDate == null) ? 0 : publicationDate.hashCode());
		result = prime * result + ((responses == null) ? 0 : responses.hashCode());
		result = prime * result + ((video == null) ? 0 : video.hashCode());
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
		Comment other = (Comment) obj;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (commentId != other.commentId)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dislikes != other.dislikes)
			return false;
		if (likes != other.likes)
			return false;
		if (parentComment == null) {
			if (other.parentComment != null)
				return false;
		} else if (!parentComment.equals(other.parentComment))
			return false;
		if (publicationDate == null) {
			if (other.publicationDate != null)
				return false;
		} else if (!publicationDate.equals(other.publicationDate))
			return false;
		if (responses == null) {
			if (other.responses != null)
				return false;
		} else if (!responses.equals(other.responses))
			return false;
		if (video == null) {
			if (other.video != null)
				return false;
		} else if (!video.equals(other.video))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [commentId=");
		builder.append(commentId);
		builder.append(", channel=");
		builder.append(channel);
		builder.append(", video=");
		builder.append(video);
		builder.append(", content=");
		builder.append(content);
		builder.append(", publicationDate=");
		builder.append(publicationDate);
		builder.append(", likes=");
		builder.append(likes);
		builder.append(", dislikes=");
		builder.append(dislikes);
		builder.append(", responses=");
		builder.append(responses);
		builder.append(", parentComment=");
		builder.append(parentComment);
		builder.append("]");
		return builder.toString();
	}
}
