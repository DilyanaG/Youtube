package com.youtube.model.dao.playlist;

import java.util.List;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dto.playlist.PlaylistTopViewDTO;
import com.youtube.model.pojo.Playlist;

public interface IPlaylistDAO {

	List<PlaylistTopViewDTO> getPlaylistByName(String playlist_name) throws DataBaseException, IllegalInputException;

	List<Playlist> getPlaylistsByChannelAndSortByCreationDate(int channelId) throws DataBaseException;

	List<Playlist> getAllPlaylists() throws DataBaseException;

	boolean createNewPlaylist(String playlistName, int channelId) throws DataBaseException;

	void deletePlaylist(int  playlistId) throws DataBaseException;
	
	void deleteChannelPlaylists(int channelId) throws DataBaseException;

	void renamePlaylistName(int playlistId, String newName) throws DataBaseException;

	void updataLastVideoAddDate(int playlistId) throws DataBaseException;
}
