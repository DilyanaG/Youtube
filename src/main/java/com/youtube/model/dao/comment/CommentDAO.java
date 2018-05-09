package com.youtube.model.dao.comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.db.DBManager;
import com.youtube.model.dto.video.CommentDTO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Comment;
import com.youtube.model.pojo.Video;
import com.youtube.model.resolvers.CommentDTOResolver;
import com.youtube.model.resolvers.CommentResolver;

@Component
public class CommentDAO implements ICommentDAO {

	// selects
	private static final String GET_COMMENT_BY_ID = "SELECT c.*, ch.*, v.* FROM comments AS c JOIN channels AS ch ON c.channel_id=ch.channel_id JOIN videos AS v ON c.video_id=v.video_id WHERE c.comment_id = ?;";

	private static final String SELECT_ALL_BY_VIDEO_ID = "SELECT c.*, ch.channel_id, u.user_name, u.profile_picture_url FROM comments AS c JOIN channels AS ch ON c.channel_id=ch.channel_id JOIN users AS u ON ch.user_id=u.user_id WHERE c.video_id = ? ORDER BY create_date DESC;";

	private static final String SELECT_ALL_BY_CHANNEL_ID = "SELECT c.*, ch.*, v.* FROM comments AS c JOIN channels AS ch ON c.channel_id=ch.channel_id JOIN videos AS v ON c.video_id=v.video_id WHERE c.channel_id = ?;";

	private static final String SELECT_ALL_COMMENTS = "SELECT c.*, ch.*, v.* FROM comments AS c JOIN channels AS ch ON c.channel_id=ch.channel_id JOIN videos AS v ON c.video_id=v.video_id;";

	private static final String SELECT_ALL_RESPONSES_FOR_COMMENT = "SELECT c.*, ch.*, v.* FROM comments AS c JOIN channels AS ch ON c.channel_id=ch.channel_id JOIN videos AS v ON c.video_id=v.video_id WHERE response_to_comment_id = ?;";
	
	private static final String COUNT_OF_LIKES = "SELECT COUNT(chld.id) as count FROM comment_has_likes_dislikes AS chld JOIN comments AS c ON chld.comment_id = c.comment_id WHERE c.comment_id = ? AND chld.isLike = 1;";;

	private static final String COUNT_OF_DISLIKES = "SELECT COUNT(chld.id) as count FROM comment_has_likes_dislikes AS chld JOIN comments AS c ON chld.comment_id = c.comment_id WHERE c.comment_id = ? AND chld.isLike = 0;";;

	private static final String LIKE_EXISTS = "SELECT COUNT(chld.id) as count FROM comment_has_likes_dislikes AS chld JOIN comments AS c ON chld.comment_id = c.comment_id WHERE chld.comment_id = ? AND chld.channel_id = ? AND chld.isLike = 1;";

	private static final String DISLIKE_EXISTS = "SELECT COUNT(chld.id) as count FROM comment_has_likes_dislikes AS chld JOIN comments AS c ON chld.comment_id = c.comment_id WHERE chld.comment_id = ? AND chld.channel_id = ? AND chld.isLike = 0;";

	
	// insert
	private static final String CREATE_NEW_COMMENT = "INSERT INTO comments (channel_id, video_id , content , create_date ) VALUES (?,?,?,now());";
	
	private static final String ADD_RESPONSE_TO_COMMENT = "INSERT INTO comments (channel_id, video_id , content , create_date, response_to_comment_id ) VALUES (?,?,?,now(),?);";
	
	private static final String LIKE_COMMENT = "INSERT INTO comment_has_likes_dislikes (isLike,comment_id,channel_id) VALUES (1,?,?)";
	
	private static final String DISLIKE_COMMENT = "INSERT INTO comment_has_likes_dislikes (isLike,comment_id,channel_id) VALUES (0,?,?)";;

	// update
	private static final String UPDATE_CONTENT = "UPDATE comments SET content = ? WHERE comment_id = ? AND channel_id = ?;";

	// delete
	private static final String DELETE_COMMENT = "DELETE FROM comments WHERE comment_id = ?;";

	private static final String DELETE_ALL_BY_VIDEO_ID = "DELETE FROM comments WHERE video_id = ?;";

	private static final String DELETE_ALL_BY_CHANNEL = "DELETE FROM comments WHERE channel_id = ?;";

	private static final String REMOVE_LIKE_DISLIKE_FROM_COMMENT = "DELETE FROM comment_has_likes_dislikes WHERE comment_id = ? AND channel_id = ?;";



	@Autowired
	private DBManager dbManager;

	@Override
	public Comment getCommentById(int commentid) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();
		try {
			dbManager.startTransaction(connection);
			Comment comment = dbManager.executeSingleSelect(connection, GET_COMMENT_BY_ID, new CommentResolver(),
					commentid);
			dbManager.commit(connection);
			return comment;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}
	
	@Override
	public List<CommentDTO> getCommentsForVideo(int videoId) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<CommentDTO> comments = dbManager.executeSelect(connection, SELECT_ALL_BY_VIDEO_ID, new CommentDTOResolver(),
					videoId);
			dbManager.commit(connection);
			return Collections.unmodifiableList(comments);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public List<Comment> getAllCommentsForChannel(Channel channel) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<Comment> comments = dbManager.executeSelect(connection, SELECT_ALL_BY_CHANNEL_ID, new CommentResolver(),
					channel.getChannelId());
			dbManager.commit(connection);
			return Collections.unmodifiableList(comments);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public List<Comment> getAllComments() throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<Comment> comments = dbManager.executeSelect(connection, SELECT_ALL_COMMENTS, new CommentResolver());
			dbManager.commit(connection);
			return Collections.unmodifiableList(comments);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}
	
	@Override
	public List<Comment> getResponsesForComment(Comment comment) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<Comment> comments = dbManager.executeSelect(connection, SELECT_ALL_RESPONSES_FOR_COMMENT, new CommentResolver(), comment.getCommentId());
			dbManager.commit(connection);
			return Collections.unmodifiableList(comments);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public void addNewCommentToVideo(int videoId, int channelId, String comment) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, CREATE_NEW_COMMENT, channelId, videoId, comment);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void addResponseToComment(Comment respondTo, String response, Video video, Channel channel) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, ADD_RESPONSE_TO_COMMENT, channel.getChannelId(), video.getVideoId(), response, respondTo.getCommentId());
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void deleteComment(Comment comment) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			//TODO should we check the channel_id here or in the controller?
			dbManager.execute(connection, DELETE_COMMENT, comment.getCommentId());
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void deleteAllCommentsForVideo(Video video) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, DELETE_ALL_BY_VIDEO_ID, video.getVideoId());
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void deleteChannelComments(Channel channel) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, DELETE_ALL_BY_CHANNEL, channel.getChannelId());
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public int getLikesForComment(int comment_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			final int likesCount = dbManager.executeSingleSelect(connection, COUNT_OF_LIKES, (rs) -> rs.getInt("count"),
					comment_id);
			dbManager.commit(connection);
			return likesCount;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return -1;
		}
	}

	@Override
	public int getDislikesForComment(int comment_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			final int dislikesCount = dbManager.executeSingleSelect(connection, COUNT_OF_DISLIKES, (rs) -> rs.getInt("count"),
					comment_id);
			dbManager.commit(connection);
			return dislikesCount;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return -1;
		}
	}

	@Override
	public void likeComment(Comment comment, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, LIKE_COMMENT, comment.getCommentId(), channel_id);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void dislikeCommment(Comment comment, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, DISLIKE_COMMENT, comment.getCommentId(), channel_id);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public boolean likeExists(int comment_id, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			final int likeCount = dbManager.executeSingleSelect(connection, LIKE_EXISTS, (rs) -> rs.getInt("count"),
					comment_id, channel_id);
			dbManager.commit(connection);
			return (likeCount == 1) ? true : false;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return false;
		}
	}

	@Override
	public boolean dislikeExists(int comment_id, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			final int dislikeCount = dbManager.executeSingleSelect(connection, DISLIKE_EXISTS, (rs) -> rs.getInt("count"),
					comment_id, channel_id);
			dbManager.commit(connection);
			return (dislikeCount == 1) ? true : false;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return false;
		}
	}

	@Override
	public void removeLikeDislikeFromComment(int comment_id, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, REMOVE_LIKE_DISLIKE_FROM_COMMENT, comment_id, channel_id);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void updateCommentContent(Comment comment, Channel channel, String newContent) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, UPDATE_CONTENT, newContent, comment.getCommentId(), channel.getChannelId());
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

}
