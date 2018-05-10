package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.youtube.model.dto.playlist.ChannelPlaylistDTO;

public class ChannelPlaylistDTOResolver implements IResolver<ChannelPlaylistDTO>{

	@Override
	public ChannelPlaylistDTO resolve(ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);

		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value

		final Integer playlistId = selectedColumns.contains("playlist_id") ? rs.getInt("playlist_id") : null;
		final Integer channelId = selectedColumns.contains("channel_id") ? rs.getInt("channel_id") : null;
		final String playlistName = selectedColumns.contains("name") ? rs.getString("name") : null;
		
		ChannelPlaylistDTO channelPlaylists = new ChannelPlaylistDTO(playlistId, channelId, playlistName);
		return channelPlaylists;
	}
}
