package com.youtube.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.channel.ChannelDAO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Comment;
import com.youtube.model.pojo.Video;

public class CommentDAO {
	//selects
	private static final String SELECT_ALL_BY_VIDEO_ID =
			"SELECT comment_id,channel_id, content, date, likes, dislikes FROM comments WHERE video_id = ?;";
	private static final String SELECT_ALL_BY_CHANNEL_ID = 
			"SELECT comment_id,video_id, content, date, likes, dislikes,  FROM comments WHERE channel_id = ?;"; 
	private static final String SELECT_ALL_ = 
			"SELECT comment_id,video_id,channel_id, content, date, likes, dislikes,  FROM comments WHERE channel_id = ?;";
	private static final String BY_ID =
			"SELECT comment_id,channel_id,video_id, content, date, likes, dislikes FROM comments WHERE comment_id = ?;";		
	//insert
	private static final String CREATE_NEW_PLAYLIST = 
			" INSERT INTO comments (channel_id, video_id , content , date, likes , dislikes ) VALUES (?,?,?,now(),?,?);";
	//update
	private static final String UPDATE_CONTENT = 
			"UPDATE comments SET content = ? WHERE comment_id = ? ;";
	private static final String UPDATE_LIKES = 
			"UPDATE comments SET likes = ? WHERE comment_id = ?; ";
	private static final String UPDATE_DISLIKES = 
			"UPDATE comments SET dislikes = ? WHERE comment_id = ? ;";
	//delete
	private static final String DELETE_COMMENT =
			"DELETE FROM comments WHERE comment_id = ?;";
   private static final String DELETE_ALL_BY_VIDEO_ID = 
		   "DELETE FROM comments WHERE video_id = ?;";
   private static final String DELETE_ALL_BY_CHANNEL = 
		   "DELETE FROM comments WHERE channel_id = ?;";
private static final String DELETE_USER_PROFILE = null;


 
	private static CommentDAO instance;
	private Connection connection;
	
	private CommentDAO() {
		 connection = DBManager.getInstance().getConnection();

	}

	public static CommentDAO getInstance() {
		if (instance == null) {
			instance = new CommentDAO();
		}
		return instance;
	}
	public List<Comment> getCommentsForVideo(Video video) throws SQLException, IllegalInputException {

		  List<Comment> comments = new  ArrayList<Comment>();
			PreparedStatement st = connection.prepareStatement(SELECT_ALL_BY_VIDEO_ID);
			st.setInt(1,video.getVideoId());
			ResultSet rezultSet= st.executeQuery();
			// get all channles
			while (rezultSet.next()) {
				
				Comment comment = new Comment(rezultSet.getInt("comment_id"),
						                        video,
						                        ChannelDAO.getInstance().getChannelById(rezultSet.getInt("channel_id")),
												rezultSet.getString("content"),
												rezultSet.getDate("date"),
												rezultSet.getInt("likes"),
												rezultSet.getInt("dislikes"));
				
			    comments.add(comment);

			}
			rezultSet.close();
			st.close();
          return Collections.unmodifiableList(comments);
	}

//TODO
	private List<Comment> getResponsesForComment(Comment comment) {
		//TO DO select
		return Collections.EMPTY_LIST;
	}
	public void addNewCommentForVideo(Video video,Channel channel,String  comment) throws SQLException{
		PreparedStatement st = connection.prepareStatement(CREATE_NEW_PLAYLIST);
		st.setInt(1, channel.getChannelId());
		st.setInt(2,video.getVideoId());
		st.setString(3,comment);
		st.setInt(4,0);
		st.setInt(5,0);
		st.executeUpdate();
		st.close();
	}
	//TODO UPpdate selects
	public void likeComment(Comment comment) throws SQLException{
		PreparedStatement st = connection.prepareStatement(UPDATE_LIKES);
		st.setInt(1,comment.getLikes()+1);
		st.setInt(2,comment.getCommentId());
		st.executeUpdate();
		st.close();
	}
	public void dislikeCommment(Comment comment) throws SQLException{
		PreparedStatement st = connection.prepareStatement(UPDATE_DISLIKES);
		st.setInt(1,comment.getLikes()+1);
		st.setInt(2,comment.getCommentId());
		st.executeUpdate();
		st.close();
	}
	//TODO 
	public void responseComment(Comment toResponse,Comment responser,Video video,Channel channel){
		
	}
	
	public void updateToCommentContent(Comment comment, String newContent) throws SQLException{
		PreparedStatement st = connection.prepareStatement(UPDATE_CONTENT);
		st.setString(1,newContent);
		st.setInt(2,comment.getCommentId());
		st.executeUpdate();
		st.close();
	}
	public List<Comment> getAllComments() throws SQLException, IllegalInputException{
		  List<Comment> comments = new  ArrayList<Comment>();
				PreparedStatement st = connection.prepareStatement(SELECT_ALL_);
				ResultSet rezultSet= st.executeQuery();
				
				// get all channles
				while (rezultSet.next()) {
					
					Comment comment = new Comment(rezultSet.getInt("comment_id"),
													VideoDAO.getInstance().getVideoById(rezultSet.getInt("video_id")),
							                        ChannelDAO.getInstance().getChannelById(rezultSet.getInt("channel_id")),
													rezultSet.getString("content"),
													rezultSet.getDate("date"),
													rezultSet.getInt("likes"),
													rezultSet.getInt("dislikes"));
					
				    comments.add(comment);

				}
				rezultSet.close();
				st.close();
				return comments;
		
	}
	
	public List<Comment> voidGetAllCommentsForChannel(Channel channel) throws SQLException, IllegalInputException{

		  List<Comment> comments = new  ArrayList<Comment>();
			PreparedStatement st = connection.prepareStatement(SELECT_ALL_BY_CHANNEL_ID);
			st.setInt(1,channel.getChannelId());
			ResultSet rezultSet= st.executeQuery();
			
			// get all channles
			while (rezultSet.next()) {
				
				Comment comment = new Comment(rezultSet.getInt("comment_id"),
                 VideoDAO.getInstance().getVideoById(rezultSet.getInt("video_id")),
						                         channel,
												rezultSet.getString("content"),
												rezultSet.getDate("date"),
												rezultSet.getInt("likes"),
												rezultSet.getInt("dislikes"));
				
			    comments.add(comment);

			}
			rezultSet.close();
			st.close();
        return Collections.unmodifiableList(comments);
	}
   
	public void deleteComment(Comment comment) throws SQLException{
    	deleteCommentBy(comment.getCommentId(),DELETE_COMMENT);
	}

	private void deleteCommentBy(int id,String select_string) throws SQLException {
		PreparedStatement st = connection.prepareStatement(select_string);
		st.setInt(1, id);
		st.executeUpdate();
		st.close();
	}
	
	public void deleteAllCommentsForVideo(Video video) throws SQLException{
		deleteCommentBy(video.getVideoId(), DELETE_ALL_BY_VIDEO_ID);
	}

	public void deleteChannelComments(Channel channel) throws SQLException {
		deleteCommentBy(channel.getChannelId(),DELETE_ALL_BY_CHANNEL);
		
	}

	public Comment getCommentById(int commentid) throws SQLException, IllegalInputException {
		  //System.out.println(commentid);
		  List<Comment> comments = new  ArrayList<Comment>();
			PreparedStatement st = connection.prepareStatement(BY_ID);
			st.setInt(1,commentid);
			ResultSet rezultSet= st.executeQuery();
			rezultSet.next();
				//System.out.println("assdfg");
				Channel channel = ChannelDAO.getInstance().getChannelById(rezultSet.getInt("channel_id"));
				Comment comment = new Comment(rezultSet.getInt("comment_id"),
               VideoDAO.getInstance().getVideoById(rezultSet.getInt("video_id")),
						                        channel,
												rezultSet.getString("content"),
												rezultSet.getDate("date"),
												rezultSet.getInt("likes"),
												rezultSet.getInt("dislikes"));
				
			rezultSet.close();
			st.close();
      return comment;
	}
//ok
	public void deleteUserProfileComments(int channelId) throws DataBaseException {
		try{
		PreparedStatement st = connection.prepareStatement(DELETE_USER_PROFILE);
		st.setInt(1, channelId);
		st.executeUpdate();
		st.close();
		}catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM!",e);
		}
	}

}
