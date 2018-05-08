package com.youtube.model.dao.playlist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.db.DBManager;
import com.youtube.model.pojo.Playlist;
import com.youtube.model.resolvers.PlaylistResolver;
@Component
public class PlaylistDAO implements IPlaylistDAO{

	// selects
	private static final String SELECT_BY_NAME = "SELECT p.*,ch.* FROM playlists AS p JOIN channels AS ch ON ch.channel_id=p.channel_id WHERE p.name=?;";

	private static final String ALL_PLAYLISTS_SORTED_FOR_CHANNEL = "SELECT p.*,ch.* FROM playlists AS p JOIN channels AS ch ON ch.channel_id=p.channel_id WHERE p.channel_id = ? ORDER BY p.create_date DESC;";

	private static final String ALL_PLAYLISTS = "SELECT p.*,ch.* FROM playlists AS p JOIN channels AS ch ON ch.channel_id=p.channel_id ;";

	// updates
	private static final String UPDATE_NAME = "UPDATE playlists SET name = ? WHERE playlist_id = ?; ";

	private static final String UPDATE_LAST_VIDEO_ADDING_DATE = " UPDATE playlists SET last_video_add_date = now() WHERE playlist_id = ?; ";

	// insert
	private static final String CREATE_NEW_PLAYLIST = "INSERT INTO playlists (channel_id, name, create_date, last_video_add_date) VALUES (?,?,now(),now());";

	// delete
	private static final String DELETE_FROM_PLAYLIST_HAS_VIDEOS_TABLE = "DELETE FROM playlists_has_videos WHERE playlist_id = ?;";

	private static final String DELETE_PLAYLIST = "DELETE FROM playlists WHERE playlist_id = ?;";

	@Autowired
	private  DBManager dbManager;

	@Override
	public Playlist getPlaylistByName(String playlist_name) throws DataBaseException {
		final Connection connection = dbManager.getConnection();
		try {
			dbManager.startTransaction(connection);
			Playlist playlist = dbManager.executeSingleSelect(connection, SELECT_BY_NAME, new PlaylistResolver(),
					playlist_name);
			dbManager.commit(connection);
			return playlist;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public List<Playlist> getPlaylistsByChannelAndSortByCreationDate(int channelId) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<Playlist> playlists = dbManager.executeSelect(connection, ALL_PLAYLISTS_SORTED_FOR_CHANNEL,
					new PlaylistResolver(), channelId);
			dbManager.commit(connection);
			return new ArrayList<>(playlists);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public List<Playlist> getAllPlaylists() throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<Playlist> playlists = dbManager.executeSelect(connection, ALL_PLAYLISTS, new PlaylistResolver());
			dbManager.commit(connection);
			return Collections.unmodifiableList(playlists);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public boolean createNewPlaylist(String playlistName, int channelId) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			int inserted = dbManager.execute(connection, CREATE_NEW_PLAYLIST, channelId, playlistName);
			dbManager.commit(connection);
			return (inserted > 0) ? true : false;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return false;
		}
	}

	@Override
	public void deletePlaylist(int  playlistId) throws DataBaseException {
		final Connection connection = dbManager.getConnection();
		

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, DELETE_FROM_PLAYLIST_HAS_VIDEOS_TABLE,playlistId );
			dbManager.execute(connection, DELETE_PLAYLIST, playlistId);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void deleteChannelPlaylists(int channelId) throws DataBaseException {
		final Connection connection = dbManager.getConnection();
		List<Playlist> playlists = getPlaylistsByChannelAndSortByCreationDate(channelId);

		try {
			dbManager.startTransaction(connection);
			for (Playlist playlist : playlists) {
				dbManager.execute(connection, DELETE_FROM_PLAYLIST_HAS_VIDEOS_TABLE, playlist.getPlaylistId());
				dbManager.execute(connection, DELETE_PLAYLIST, playlist.getPlaylistName());
			}
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void renamePlaylistName(int playlistId, String newName) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, UPDATE_NAME, newName, playlistId);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void updataLastVideoAddDate(int playlistId) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, UPDATE_LAST_VIDEO_ADDING_DATE, playlistId);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}
	
	public static void main(String[] args) throws DataBaseException {
		System.out.println(new PlaylistDAO().getPlaylistsByChannelAndSortByCreationDate(1));
		
	}
	
}
