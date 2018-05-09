package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Comment;
import com.youtube.model.pojo.Video;

public class CommentResolver implements IResolver<Comment> {

	private final IResolver<Channel> channelResolver = new ChannelResolver();
	private final IResolver<Video> videoResolver = new VideoResolver();

	@Override
	public Comment resolve(ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);

		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value
		// The "c." is the alias for the "comments" table in the DB

		final Integer commentId = selectedColumns.contains("comment_id") ? rs.getInt("comment_id") : null;
		final Video video = videoResolver.resolve(rs);
		final Channel channel = channelResolver.resolve(rs);
		final String content = selectedColumns.contains("content") ? rs.getString("content") : null;
		final LocalDateTime publicationDate = selectedColumns.contains("create_date") ? rs.getTimestamp("create_date").toLocalDateTime() : null;

		final Integer likes = selectedColumns.contains("views") ? rs.getInt("views") : null;
		final Integer dislikes = selectedColumns.contains("isDeleted") ? rs.getInt("isDeleted") : null;

		Comment comment = new Comment(commentId, video, channel, content, publicationDate, likes, dislikes);
		return comment;
	}

}
