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

		final Integer videoId = selectedColumns.contains("video_id") ? rs.getInt("video_id") : null;
		System.out.println("resolversss"+videoId);
		final Channel channel = channelResolver.resolve(rs);
		final String videoUrl = selectedColumns.contains("video_url") ? rs.getString("video_url") : null;
		final String photoUrl = selectedColumns.contains("photo_url") ? rs.getString("photo_url") : null;
		final String title = selectedColumns.contains("title") ? rs.getString("title") : null;
		final String description = selectedColumns.contains("description") ? rs.getString("description") : null;
		final Integer views = selectedColumns.contains("views") ? rs.getInt("views") : null;
		
		// Use Inner selects with COUNT from the video_has_likes table. ONLY for
		// single select
		// FOR list selects use separate queries
		final Integer countOfLikes = selectedColumns.contains("likes") ? rs.getInt("likes") : 0;
		final Integer countOfDislikes = selectedColumns.contains("dislikes") ? rs.getInt("dislikes") : 0;

		final LocalDateTime uploadDate = selectedColumns.contains("date")
				? rs.getTimestamp("date").toLocalDateTime() : null;
        
				
				System.out.println("resolversss"+videoId);
				System.out.println(videoUrl);
				System.out.println(photoUrl);
				System.out.println(title);
				System.out.println(description);
				System.out.println(views);
				System.out.println(countOfLikes);
				System.out.println(countOfDislikes);
				System.out.println(uploadDate);
				
								
		Video video = new Video(videoId, channel, videoUrl, photoUrl, title, uploadDate, description, views,  countOfLikes, countOfDislikes);
		return video;
	}

}
