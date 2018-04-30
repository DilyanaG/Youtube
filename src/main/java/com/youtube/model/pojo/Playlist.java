package com.youtube.model.pojo;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

public class Playlist {
	private int playlistId;
	private Channel channel;
	private String playlistName;
    private Set<Video> videos;
	private LocalDateTime lastVideoUploaded;
	private LocalDateTime creationDateTime; 

	
	public Playlist(){
		super();
	}
	
	public Playlist(int playlistId, Channel channel, String playlistName, LocalDateTime lastVideoUploaded, LocalDateTime creationDateTime) {
		this(playlistId, channel, playlistName, lastVideoUploaded);
		this.creationDateTime = creationDateTime;
	}

	public Playlist(int playlistId, Channel channel, String playlistName, LocalDateTime lastVideoUploaded) {
		this(playlistId, channel, playlistName);
		this.lastVideoUploaded = lastVideoUploaded;
	}

	public Playlist(int playlistId, Channel channel , String playlistName) {
		this.playlistId = playlistId;
		this.channel = channel;
		this.playlistName = playlistName;
	    this.videos = new TreeSet<Video>();
		this.creationDateTime = LocalDateTime.now();
	}

	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public Set<Video> getVideos() {
		return videos;
	}

	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}

	public LocalDateTime getLastVideoUploaded() {
		return lastVideoUploaded;
	}

	public void setLastVideoUploaded(LocalDateTime lastVideoUploaded) {
		this.lastVideoUploaded = lastVideoUploaded;
	}

	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((creationDateTime == null) ? 0 : creationDateTime.hashCode());
		result = prime * result + ((lastVideoUploaded == null) ? 0 : lastVideoUploaded.hashCode());
		result = prime * result + playlistId;
		result = prime * result + ((playlistName == null) ? 0 : playlistName.hashCode());
		result = prime * result + ((videos == null) ? 0 : videos.hashCode());
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
		Playlist other = (Playlist) obj;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (creationDateTime == null) {
			if (other.creationDateTime != null)
				return false;
		} else if (!creationDateTime.equals(other.creationDateTime))
			return false;
		if (lastVideoUploaded == null) {
			if (other.lastVideoUploaded != null)
				return false;
		} else if (!lastVideoUploaded.equals(other.lastVideoUploaded))
			return false;
		if (playlistId != other.playlistId)
			return false;
		if (playlistName == null) {
			if (other.playlistName != null)
				return false;
		} else if (!playlistName.equals(other.playlistName))
			return false;
		if (videos == null) {
			if (other.videos != null)
				return false;
		} else if (!videos.equals(other.videos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Playlist [playlistId=");
		builder.append(playlistId);
		builder.append(", channel=");
		builder.append(channel);
		builder.append(", playlistName=");
		builder.append(playlistName);
		builder.append(", videos=");
		builder.append(videos);
		builder.append(", lastVideoUploaded=");
		builder.append(lastVideoUploaded);
		builder.append(", creationDateTime=");
		builder.append(creationDateTime);
		builder.append("]");
		return builder.toString();
	}

}
