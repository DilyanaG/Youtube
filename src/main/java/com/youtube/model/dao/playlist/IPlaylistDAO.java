package com.youtube.model.dao.playlist;

import java.util.List;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.model.pojo.Playlist;

public interface IPlaylistDAO {

	Playlist getPlaylistByName(String playlist_name) throws DataBaseException;

	List<Playlist> getPlaylistsByChannelAndSortByCreationDate(int channelId) throws DataBaseException;

	List<Playlist> getAllPlaylists() throws DataBaseException;

	boolean createNewPlaylist(String playlistName, int channelId) throws DataBaseException;

	void deletePlaylist(int  playlistId) throws DataBaseException;
	
	void deleteChannelPlaylists(int channelId) throws DataBaseException;

	void renamePlaylistName(int playlistId, String newName) throws DataBaseException;

	void updataLastVideoAddDate(int playlistId) throws DataBaseException;
}
