package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Video;

public class VideoResolver implements IResolver<Video> {

	private final IResolver<Channel> channelResolver = new ChannelResolver();

	@Override
	public Video resolve(final ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);

		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value
		// The "v." is the alias for the "videos" table in the DB

		final Integer videoId = selectedColumns.contains("v.video_id") ? rs.getInt("v.video_id") : null;
		final Channel channel = channelResolver.resolve(rs);
		final String videoUrl = selectedColumns.contains("v.video_url") ? rs.getString("v.video_url") : null;
		final String photoUrl = selectedColumns.contains("v.photo_url") ? rs.getString("v.photo_url") : null;
		final String title = selectedColumns.contains("v.title") ? rs.getString("v.title") : null;
		final String description = selectedColumns.contains("v.description") ? rs.getString("v.description") : null;
		final Integer views = selectedColumns.contains("v.views") ? rs.getInt("v.views") : null;
		final Integer isDeleted = selectedColumns.contains("v.isDeleted") ? rs.getInt("v.isDeleted") : null;

		// Use Inner selects with COUNT from the video_has_likes table. ONLY for
		// single select
		// FOR list selects use separate queries
		final Integer countOfLikes = selectedColumns.contains("vl.likes") ? rs.getInt("vl.likes") : null;
		final Integer countOfDislikes = selectedColumns.contains("vl.dislikes") ? rs.getInt("vl.dislikes") : null;

		final LocalDateTime uploadDate = selectedColumns.contains("v.date")
				? rs.getTimestamp("v.date").toLocalDateTime() : null;

		Video video = new Video(videoId, channel, videoUrl, photoUrl, title, uploadDate, description, views, isDeleted, countOfLikes, countOfDislikes);
		return video;
	}

}
