package com.youtube.model.dao.comment;

import java.util.List;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Comment;
import com.youtube.model.pojo.Video;

public interface ICommentDAO {

	Comment getCommentById(int commentid) throws IllegalInputException, DataBaseException;

	List<Comment> getCommentsForVideo(Video video) throws IllegalInputException, DataBaseException;

	List<Comment> getAllCommentsForChannel(Channel channel) throws IllegalInputException, DataBaseException;

	List<Comment> getAllComments() throws IllegalInputException, DataBaseException;

	List<Comment> getResponsesForComment(Comment comment) throws DataBaseException;
	
	void addNewCommentToVideo(Video video, Channel channel, String comment) throws DataBaseException;

	void addResponseToComment(Comment respondTo, String response, Video video, Channel channel) throws DataBaseException;

	void deleteComment(Comment comment) throws DataBaseException;
	
	void deleteAllCommentsForVideo(Video video) throws DataBaseException;

	void deleteChannelComments(Channel channel) throws DataBaseException;

	int getLikesForComment(int comment_id) throws DataBaseException;

	int getDislikesForComment(int comment_id) throws DataBaseException;

	void likeComment(Comment comment, int channel_id) throws DataBaseException;

	void dislikeCommment(Comment comment, int channel_id) throws DataBaseException;

	boolean likeExists(int comment_id, int channel_id) throws DataBaseException;

	boolean dislikeExists(int comment_id, int channel_id) throws DataBaseException;

	void removeLikeDislikeFromComment(int comment_id, int channel_id) throws DataBaseException;

	void updateCommentContent(Comment comment, Channel channel, String newContent) throws DataBaseException;

}
