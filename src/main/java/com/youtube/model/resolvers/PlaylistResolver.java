package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Playlist;

public class PlaylistResolver implements IResolver<Playlist>{

	private final IResolver<Channel> channelResolver = new ChannelResolver();
	
	@Override
	public Playlist resolve(final ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);

		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value
		// The "p." is the alias for the "playlists" table in the DB

		final Integer playlistId = selectedColumns.contains("playlist_id") ? rs.getInt("playlist_id") : null;
		final Channel channel = channelResolver.resolve(rs);
		final String playlistName = selectedColumns.contains("name") ? rs.getString("name") : null;
		
		 final LocalDateTime lastVideoUploaded = selectedColumns.contains("last_video_add_date") ? rs.getTimestamp("last_video_add_date").toLocalDateTime() : null;
		 final LocalDateTime creationDateTime = selectedColumns.contains("create_date") ? rs.getTimestamp("create_date").toLocalDateTime() : null;
		
		Playlist playlist = new Playlist(playlistId, channel, playlistName, lastVideoUploaded, creationDateTime);
		return playlist;
	}

}
