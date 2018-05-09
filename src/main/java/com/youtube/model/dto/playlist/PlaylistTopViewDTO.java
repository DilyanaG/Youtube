package com.youtube.model.dto.playlist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import com.youtube.model.pojo.Playlist;
import com.youtube.model.pojo.Video;

public class PlaylistTopViewDTO {
     
	private static final String DEFOULT_PLAYLIST_PHOTO = "FinalProject/images/defoultPlaylilits.png";
	private int playlistId;
	private int channelId;
	private String playlistName;
	private String photoUrl;
	private String createDate;
	private String lastVideoAddDate;
	
	
	
	public PlaylistTopViewDTO(Playlist playlist, List<Video> playlistVideos) {
          this.playlistId = playlist.getPlaylistId();
          this.channelId=playlist.getChannel().getChannelId();
          this.playlistName=playlist.getPlaylistName();
          this.createDate=playlist.getCreationDateTime().toString();
          this.lastVideoAddDate=playlist.getLastVideoUploaded().toString();
          this.photoUrl=playlistVideos==null||playlistVideos.isEmpty()?DEFOULT_PLAYLIST_PHOTO:playlistVideos.get(new Random().nextInt(playlistVideos.size())).getPhotoUrl();
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
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastVideoAddDate() {
		return lastVideoAddDate;
	}
	public void setLastVideoAddDate(String lastVideoAddDate) {
		this.lastVideoAddDate = lastVideoAddDate;
	}
	
}
