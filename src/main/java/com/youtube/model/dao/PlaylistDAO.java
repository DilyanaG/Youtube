package com.youtube.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Playlist;

@Component
public class PlaylistDAO {
	// DB
	// selects
	private static final String SELECT_BY_NAME = "SELECT playlist_id,name, create_date,last_video_add_date  FROM playlists "
			+ "WHERE name =?;";

	private static final String SELECT_ALL_PLAYLIST_BY_CHANNEL_ID = "SELECT playlist_id, name, last_video_add_date, create_date  FROM playlists WHERE channel_id = ?;";

	private static final String ALL_PLAYLISTS = "SELECT playlist_id,name, create_date,last_video_add_date  from playlists ;";

	private static final String ALL_PLAYLISTS_SORTED_FOR_CHANNEL = "SELECT playlist_id,name, create_date,last_video_add_date  FROM playlists WHERE channel_id =?;"
			+ "ORDER BY ? DESC;";

	// updates
	private static final String UPDATE_LAST_VIDEO_ADDING_DATE = " UPDATE playlists SET last_video_add_date = now() WHERE playlist_id = ?; ";

	private static final String UPDATE_NAME = "UPDATE playlists SET name = ? WHERE playlist_id = ?; ";
	// insert
	private static final String CREATE_NEW_PLAYLIST = "INSERT INTO playlists (channel_id, name, create_date, last_video_add_date) VALUES (?,?,now(),now());";

	// delete
	private static final String DELETE_FROM_PLAYLIST_HAS_VIDEOS_TABLE = "DELETE FROM playlists_has_videos WHERE playlist_id = ?;";
	private static final String DELETE_PLAYLIST = "DELETE FROM playlists WHERE name = ?;";

	@Autowired
	private static DBManager dbManager;

	private Connection connection = dbManager.getConnection();;

	public boolean createNewPlaylist(String playlistName, int channelId) throws DataBaseException {
		try {
			PreparedStatement st = connection.prepareStatement(CREATE_NEW_PLAYLIST);
			st.setInt(1, channelId);
			st.setString(2, playlistName);
			if (st.executeUpdate() > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM", e);
		}
	}

	public List<Playlist> getPlaylistForChannel(int channelId) throws DataBaseException {

		List<Playlist> playlists = new ArrayList<>();
		try {
			PreparedStatement st = connection.prepareStatement(SELECT_ALL_PLAYLIST_BY_CHANNEL_ID);
			st.setInt(1, channelId);
			ResultSet rezultSet = st.executeQuery();
			playlists = createPlaylistsFromRezultSet(rezultSet);
			return Collections.unmodifiableList(playlists);
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM", e);
		}

	}

	private List<Playlist> createPlaylistsFromRezultSet(ResultSet rezultSet) throws DataBaseException {
		List<Playlist> playlists = new ArrayList<>();
		try {
			while (rezultSet.next()) {

				Playlist playlist = new Playlist(rezultSet.getInt("playlist_id"), rezultSet.getString("name"),
						rezultSet.getTimestamp("last_video_add_date").toLocalDateTime(),
						rezultSet.getTimestamp("create_date").toLocalDateTime());
				playlists.add(playlist);
			}
			return playlists;
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM", e);
		}
	}

	public void updataLastVideoAddDate(int playlistId) throws DataBaseException {
		try {
			PreparedStatement st = connection.prepareStatement(UPDATE_LAST_VIDEO_ADDING_DATE);
			st.setInt(1, playlistId);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM", e);
		}
	}

	public void deletePlaylist(String playlist_name) throws DataBaseException {
		try {
			Playlist playlist = this.getPlaylistByName(playlist_name);
			this.deletePlaylistFromTable(playlist.getId());
			PreparedStatement st = connection.prepareStatement(DELETE_PLAYLIST);
			st.setString(1, playlist_name);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM", e);
		}
	}

	private void deletePlaylistFromTable(int id) throws DataBaseException {
		try {
			PreparedStatement st = connection.prepareStatement(DELETE_FROM_PLAYLIST_HAS_VIDEOS_TABLE);
			st.setInt(1, id);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM", e);
		}

	}

	public void renamePlaylistName(int playlistId, String newName) throws DataBaseException {
		try {
			PreparedStatement st = connection.prepareStatement(UPDATE_NAME);
			st.setString(1, newName);
			st.setInt(2, playlistId);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM", e);
		}
	}

	// TODO
	public List<Playlist> getSortedPlaylistForChannelBy(Channel channel, String sortBy) throws DataBaseException {

		String sort = "";
		switch (sortBy) {
		// case:{
		// sort="name";
		//
		// }
		// case LASTADDEDVIDEODATE:{
		// sort="last_video_add_date";
		// }
		default: {
			sort = "create_date";
		}
		}

		List<Playlist> playlists = new ArrayList<>();
		try {
			PreparedStatement st = connection.prepareStatement(ALL_PLAYLISTS_SORTED_FOR_CHANNEL);
			st.setInt(1, channel.getChannelId());
			st.setString(2, sort);
			ResultSet rezultSet = st.executeQuery();
			playlists = createPlaylistsFromRezultSet(rezultSet);
			return Collections.unmodifiableList(playlists);
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM", e);
		}
	}

	public List<Playlist> getAllPlaylists() throws SQLException {
		List<Playlist> playlists = new ArrayList<>();
		PreparedStatement st = connection.prepareStatement(ALL_PLAYLISTS);
		ResultSet rezultSet = st.executeQuery();
		// get all playlist for channel
		playlists = createPlaylistsFromRezultSet(rezultSet);
		rezultSet.close();
		st.close();

		return Collections.unmodifiableList(playlists);
	}

	public void deleteChannelPlaylists(int channelId) throws SQLException {

		for (Playlist playlist : this.getPlaylistForChannel(channelId)) {
			this.deletePlaylist(playlist.getPlaylistName());
		}

	}

	public Playlist getPlaylistByName(String playlist_name) throws SQLException {
		PreparedStatement st = connection.prepareStatement(SELECT_BY_NAME);
		st.setString(1, playlist_name);
		ResultSet rezultSet = st.executeQuery();
		rezultSet.next();

		Playlist playlist = new Playlist(rezultSet.getInt("playlist_id"), rezultSet.getString("name"),
				rezultSet.getTimestamp("last_video_add_date").toLocalDateTime(),
				rezultSet.getTimestamp("create_date").toLocalDateTime());
		return playlist;
	}

}
