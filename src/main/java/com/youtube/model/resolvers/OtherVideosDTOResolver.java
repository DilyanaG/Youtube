package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.youtube.model.dto.video.OtherVideosDTO;

public class OtherVideosDTOResolver implements IResolver<OtherVideosDTO>{
	
	@Override
	public OtherVideosDTO resolve(ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);

		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value
		
		final Integer videoId = selectedColumns.contains("video_id") ? rs.getInt("video_id") : null;
		final Integer channelId = selectedColumns.contains("channel_id") ? rs.getInt("channel_id") : null;
		final String username = selectedColumns.contains("user_name") ? rs.getString("user_name") : null;
		final String profilePictureUrl = selectedColumns.contains("photo_url") ? rs.getString("photo_url") : null;
		final String title = selectedColumns.contains("title") ? rs.getString("title") : null;
		final Integer views = selectedColumns.contains("views") ? rs.getInt("views") : null;
		
		OtherVideosDTO otherVideos = new OtherVideosDTO(videoId, channelId, username, profilePictureUrl, title, views);
		return otherVideos;
	}
}
