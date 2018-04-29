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
import com.youtube.model.pojo.Playlist;
import com.youtube.model.pojo.Video;


public class VideoDAO {
	
	//selects
	private static final String SELECT_ALL_VIDEOS_BY_CHANNEL_ID =
			"SELECT video_id,channel_id, url, title, date,description,"
			+ " views, likes, dislikes FROM videos WHERE channel_id = ?;";
			
	private static final String SELECT_ALL_VIDEOS_BY_CHANNEL_SORTED =
			"SELECT video_id,channel_id, url, title, date,description, views,"
			+ " likes, dislikes FROM videos WHERE channel_id = ? "
			+ "ORDER BY ?;";
    private static final String SEARCH_VIDEOS_BY_TAGS = 
    	 "SELECT v.video_id,v.channel_id, v.url, v.title, v.date, v.description,  v.views, v.likes, v.dislikes "+
                "FROM videos v JOIN videos_has_tags h ON"+
                       " (v.video_id = h.video_id) WHERE h.tag_id IN ( SELECT tag_id FROM tags t WHERE content = ?)"+
    			              " ORDER BY  ? DESC;";
    private static final String GET_VIDEO_BY_TITLE =
			"SELECT video_id, channel_id, title, url, date, description, views, likes, dislikes FROM videos "
			+ " WHERE title = ?;";
    private static final String GET_VIDEO_BY_ID =
			"SELECT video_id, channel_id, title, url, date, description, views, likes, dislikes FROM videos "
			+ " WHERE video_id = ?;";
    private static final String SELECT_ALL_VIDEOS_BY_PLAYLIST_ID =
			"SELECT  v.video_id,v.channel_id, v.url, v.title, v.date,v.description, v.views, v.likes, v.dislikes FROM videos v"+
                 "JOIN  playlists_has_videos p ON(v.video_id = p.video_id)  WHERE p.playlist_id = ?;";
	
    private static final String ADD_VIDEO_TO_CHANNEL = 
    		" INSERT INTO videos (channel_id, url, title, date, description ,likes, dislikes , views) VALUES (?,?,?,now(),?,?,?,?);";
	
    private static final String GET_TAG_ID = 
    		"SELECT tag_id FROM tags WHERE tags.content = ?;";
	
   private static final String GET_TAG_COUNT = 
			"SELECT COUNT(tags.tag_id) as count FROM tags WHERE tags.content = ?;";
	
	
    //INSERTS
	private static final String INSERT_TAG = 
			"INSERT INTO tags (content) VALUES (?);";
    
	private static final String WRITE_IN_VIDEOS_HAS_TAGS = 
			"INSERT INTO videos_has_tags (video_id,tag_id) VALUES (?,?)";
	
	private static final String INSERT_VIDEO_IN_PLAYLIST =
			"INSERT INTO playlists_has_videos (video_id,playlist_id) VALUES (?,?)";
    //UPDATES

    //DELETE
	private static final String DELETE_VIDEO = 
			"DELETE FROM videos WHERE title = ?;";
	private static final String DELETE_VIDEO_FROM_ALL_PLAYLIST =
			"DELETE FROM playlists_has_videos WHERE video_id = ?;";
	private static final String DELETE_VIDEO_FROM_TAGS_TABLE = 
			"DELETE FROM videos_has_tags WHERE video_id = ?;";
	private static final String DELETE_VIDEO_FROM__PLAYLIST = 
			"DELETE FROM playlists_has_videos WHERE playlist_id = ? AND video_id IN("
			+ "SELECT video_id FROM videos WHERE title = ?);";

	private static final String DELETE_USER_PROFILE = null;
    
	private static VideoDAO instance;
	private Connection connection;
	
	private VideoDAO() {
		 connection = DBManager.getInstance().getConnection();

	}

	public static VideoDAO getInstance() {
		if (instance == null) {
			instance = new VideoDAO();
		}
		return instance;
	}
	
	public List<Video> getVideosForChannelBy(Channel channel) throws SQLException, IllegalInputException {
		   List<Video> videos = new ArrayList<>();
			PreparedStatement st = connection.prepareStatement(SELECT_ALL_VIDEOS_BY_CHANNEL_ID);
			st.setInt(1, channel.getChannelId());
			ResultSet rezultSet= st.executeQuery();
			videos.addAll(createVideosFromRezultSet(rezultSet));
			rezultSet.close();
			st.close();
	        // System.out.println("Users loaded successfully");

	        return Collections.unmodifiableList(videos);
	}

	private  List<Video> createVideosFromRezultSet( ResultSet rezultSet)
			throws SQLException,IllegalInputException {
		List<Video> videos = new ArrayList();
		while (rezultSet.next()) {
			Channel channel = ChannelDAO.getInstance().getChannelById(rezultSet.getInt("channel_id"));
		//
			//System.out.println(rezultSet.getInt("video_id"));
			Video video = new Video(rezultSet.getInt("video_id"),
					                rezultSet.getString("url"), 
									channel,
									rezultSet.getString("title"), 
									rezultSet.getString("description"),
									rezultSet.getDate("date"),
									rezultSet.getInt("likes"),
									rezultSet.getInt("dislikes"),
								    rezultSet.getInt("views"));
			// to do 
		//	video.setComments(CommentDAO.getInstance().getCommentsForVideo(video));
		//	video.setTags(this.getTagsByVideo(video));
			videos.add(video);

		}
		
		return videos;
	}

	public  List<Video> getAllVideosForPlaylist(Playlist playlist) throws SQLException, IllegalInputException {
		  List<Video> videos =new ArrayList<Video>();
			PreparedStatement st = connection.prepareStatement(SELECT_ALL_VIDEOS_BY_PLAYLIST_ID);
			st.setInt(1, playlist.getId());
			ResultSet rezultSet= st.executeQuery();
			videos.addAll(createVideosFromRezultSet(rezultSet));
			rezultSet.close();
			st.close();
			return videos;
	}

	public List<Video> getVideosByTag(String tag, String sort) throws SQLException, IllegalInputException {
		
		String sortType = "";
		switch (sort) {
		case "":
			sortType = "v.views";
			break;
        case " " :
			sortType ="v.likes";
			break;
		default:
			sortType ="v.date";
			break;
		}
		List<Video> videos =new ArrayList<Video>();
		PreparedStatement st = connection.prepareStatement(SEARCH_VIDEOS_BY_TAGS);
		st.setString(1,tag);
		st.setString(2, sortType);
		ResultSet rezultSet= st.executeQuery();
		videos.addAll(createVideosFromRezultSet(rezultSet));
		rezultSet.close();
		st.close();
		return videos;
	}

	public void addVideoToChannel(Video video, Channel channel) throws SQLException{
		PreparedStatement st = connection.prepareStatement(ADD_VIDEO_TO_CHANNEL);
		st.setInt(1 ,channel.getChannelId() );
		st.setString(2,video.getUrl());
		st.setString(3,video.getTitle());
		st.setString(4,video.getDescription());
		st.setInt(5, 0);
		st.setInt(6,0);
		st.setInt(7,0);
		st.executeUpdate();
		PreparedStatement videoSt = connection.prepareStatement(GET_VIDEO_BY_TITLE);
		videoSt.setString(1,video.getTitle());
		ResultSet res = videoSt.executeQuery();
		res.next();
		int video_id = res.getInt("video_id");
		this.writeInVideosHasTagsTable(video.getTitle()+" "+video.getDescription(),video_id);
		
		res.close();
		videoSt.close();
		st.close();
	}
	
	private void writeInVideosHasTagsTable(String videoTags, int video_id) throws SQLException {
		String[] tags = videoTags.split("\\s+");
		for(String tag: tags){
		   this.insertVideoTag(tag ,video_id);
		}
		
		
	}

	private void insertVideoTag(String tag, int video_id) throws SQLException {
		 int countOfTag =this.checkForTag(tag);
		 int tagId = 0;
		if(countOfTag==0){
			tagId=this.insertTagAndGetId(tag);
		}else{
			tagId=this.getTagId(tag);
		}
		PreparedStatement st = connection.prepareStatement(WRITE_IN_VIDEOS_HAS_TAGS);
		st.setInt(1,video_id);
		st.setInt(2,tagId);
		st.executeUpdate();
		
	}

	private int getTagId(String tag) throws SQLException {
		PreparedStatement st = connection.prepareStatement(GET_TAG_ID);
		st.setString(1,tag);
	    ResultSet res = st.executeQuery();
		res.next();
		int tag_id = res.getInt("tag_id");
		res.close();
		st.close();
		return tag_id;
	}

	private int insertTagAndGetId(String tag) throws SQLException {
		PreparedStatement st = connection.prepareStatement(INSERT_TAG);
		st.setString(1,tag);
	    st.executeUpdate();
		int tag_id = getTagId(tag);
		st.close();
		return tag_id;
	}

	private int checkForTag(String tag) throws SQLException {
		PreparedStatement st = connection.prepareStatement(GET_TAG_COUNT);
		st.setString(1, tag);
		ResultSet rezultSet= st.executeQuery();
		rezultSet.next();
		int  count = rezultSet.getInt("count");
		rezultSet.close();
		st.close();
		return count;
	}
	
	public void addVideoToPlaylist(Video video,Playlist playlist) throws SQLException{
		PreparedStatement st = connection.prepareStatement(INSERT_VIDEO_IN_PLAYLIST);
		st.setInt(1,video.getVideoId());
		st.setInt(2,playlist.getId());
	    st.executeUpdate();
	}

	public void deleteVideo(String videoTitle) throws SQLException, IllegalInputException{
		Video video = getVideoByTitle(videoTitle);
	//	CommentDAO.getInstance().deleteAllCommentsForVideo(video);
		deleteVideoFromPlaylistHasVideos(video);
		deleteVideoFromTagsTable(video);
		PreparedStatement st = connection.prepareStatement(DELETE_VIDEO);
		st.setString(1,videoTitle);
	    st.executeUpdate();
	}
	
	private void deleteVideoFromTagsTable(Video video) throws SQLException {
		PreparedStatement st = connection.prepareStatement(DELETE_VIDEO_FROM_TAGS_TABLE);
		st.setInt(1,video.getVideoId());
	    st.executeUpdate();
	}

	private void deleteVideoFromPlaylistHasVideos(Video video) throws SQLException {
		PreparedStatement st = connection.prepareStatement(DELETE_VIDEO_FROM_ALL_PLAYLIST);
		st.setInt(1,video.getVideoId());
	    st.executeUpdate();
		
	}

	public void deleteChannelVideos(Channel channel) throws SQLException, IllegalInputException {
		List<Video> videos = this.getVideosForChannelBy(channel);
		for(Video video :videos){
			this.deleteVideo(video.getTitle());
		}
		
	}

	public void deleteVideoFromPlaylist(String videoTitle, Playlist playlist) throws SQLException {
		
		PreparedStatement st = connection.prepareStatement(DELETE_VIDEO_FROM__PLAYLIST);
		st.setInt(1,playlist.getId());
		st.setString(2, videoTitle);
	    st.executeUpdate();
		
	}

	public Video getVideoById(int video_id) throws SQLException, IllegalInputException {
		  List<Video> videos =new ArrayList<Video>();
			PreparedStatement st = connection.prepareStatement(GET_VIDEO_BY_ID);
			st.setInt(1, video_id);
			ResultSet rezultSet= st.executeQuery();
			videos.addAll(createVideosFromRezultSet(rezultSet));
			rezultSet.close();
			st.close();
			return videos.get(0);
	    
	}

	public Video getVideoByTitle(String videoname) throws SQLException, IllegalInputException {
		  List<Video> videos =new ArrayList<Video>();
			PreparedStatement st = connection.prepareStatement(GET_VIDEO_BY_TITLE);
			st.setString(1, videoname);
			ResultSet rezultSet= st.executeQuery();
			videos.addAll(createVideosFromRezultSet(rezultSet));
			rezultSet.close();
			st.close();
			return videos.get(0);
	}

	//todo
	public List<Video> getSortedVideosForChannel(String sort, Channel channel) throws SQLException, IllegalInputException {
		String sortType = "";
		switch (sort) {
		case "":
			sortType = "likes";
			break;
        case " " :
			sortType ="date DESC";
			break;
		default:
			sortType ="date";
			break;
		}
		List<Video> videos =new ArrayList<Video>();
		PreparedStatement st = connection.prepareStatement(SELECT_ALL_VIDEOS_BY_CHANNEL_SORTED);
		st.setInt(1,channel.getChannelId());
		st.setString(2, sortType);
		ResultSet rezultSet= st.executeQuery();
		videos.addAll(createVideosFromRezultSet(rezultSet));
		rezultSet.close();
		st.close();
		return videos;
	}

	public void deleteUserProfileVideos(int channelId) throws DataBaseException {
		try{
		PreparedStatement st = connection.prepareStatement(DELETE_USER_PROFILE);
		st.setInt(1,channelId);
		st.executeUpdate();
		}catch (SQLException e) {
			throw new DataBaseException("DATABASE PROBLEM ",e);
		}
		
	}


	
	
}

 