package com.youtube.model.dto.playlist;

public class ChannelPlaylistDTO {
	
	private int playlistId;
	private int channelId;
	private String playlistName;
	
	public ChannelPlaylistDTO(int playlistId, int channelId, String playlistName) {
		this.playlistId = playlistId;
		this.channelId = channelId;
		this.playlistName = playlistName;
	}

	public int getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	@Override
	public String toString() {
		return "ChannelPlaylistDTO [playlistId=" + playlistId + ", channelId=" + channelId + ", playlistName="
				+ playlistName + "]";
	}
	
	
	
}
