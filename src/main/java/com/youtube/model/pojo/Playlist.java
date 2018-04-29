package com.youtube.model.pojo;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Playlist {
	private int playlistId;
	private String playlistName;
//TODO	private Set<Video> videos;
	private LocalDateTime lastVideoUploaded;
	private LocalDateTime creationDateTime; 

	public Playlist(String playlistName) {
		this.playlistName = playlistName;
	//TODO	this.videos = new TreeSet<Video>();
		this.creationDateTime = LocalDateTime.now();
	}

	public Playlist(int playlistId, String playlistName, LocalDateTime lastVideoUploaded, LocalDateTime creationDateTime) {
		this(playlistName);
		this.lastVideoUploaded = lastVideoUploaded;
		this.creationDateTime = creationDateTime;
		this.playlistId = playlistId;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setName(String playlistName) {
		this.playlistName = playlistName;
	}

	//TODO
//	public Set<Video> getVideos() {
//		return videos;
//	}
//
//	public void setVideos(Set<Video> videos) {
//		this.videos = videos;
//	}

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

	public int getId() {
		return this.playlistId;
	}

}
