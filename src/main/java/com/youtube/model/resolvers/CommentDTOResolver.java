package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.youtube.model.dto.video.CommentDTO;

public class CommentDTOResolver implements IResolver<CommentDTO> {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	
	@Override
	public CommentDTO resolve(ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);

		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value

		final Integer commentId = selectedColumns.contains("comment_id") ? rs.getInt("comment_id") : null;
		final Integer channelId = selectedColumns.contains("channel_id") ? rs.getInt("channel_id") : null;
		final String username = selectedColumns.contains("user_name") ? rs.getString("user_name") : null;
		final String profilePicture = selectedColumns.contains("profile_picture_url") ? rs.getString("profile_picture_url") : null;
		final String message = selectedColumns.contains("content") ? rs.getString("content") : null;
		final LocalDateTime uploadDate = selectedColumns.contains("create_date") ? rs.getTimestamp("create_date").toLocalDateTime() : null;
		final String formattedUploadDate = uploadDate.format(formatter);
		
		CommentDTO comment = new CommentDTO(commentId, channelId, username, profilePicture, message, formattedUploadDate);
		return comment;
	}

}
