package com.youtube.model.dao.video;

import java.sql.SQLException;
import java.util.List;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.pojo.Playlist;
import com.youtube.model.pojo.Video;

public interface IVideoDAO {

	Video getVideoById(int video_id) throws IllegalInputException, DataBaseException;

	Video getVideoByTitle(String videoname) throws IllegalInputException, DataBaseException;

	List<Video> getVideosByTagAndSortByDate(String tag) throws IllegalInputException, DataBaseException;

	List<Video> getVideosByChannelId(int channel_id) throws IllegalInputException, DataBaseException;
	
	List<Video> ByChannelIdByViews(int channel_id) throws IllegalInputException, DataBaseException, SQLException;
	
	List<Video> ByChannelIdByDate(int channel_id) throws IllegalInputException, DataBaseException, SQLException;
	
	public List<Video> ByChannelIdByTitle(int channel_id) throws IllegalInputException, DataBaseException, SQLException;
	
	List<Video> getAllVideosFromPlaylist(Playlist playlist) throws IllegalInputException, DataBaseException;

	List<Video> getMostPopularVideos() throws IllegalInputException, DataBaseException;

	int addVideo(Video video, int channelId) throws DataBaseException;

	List<Video> getRecentVideos() throws IllegalInputException, DataBaseException;

	int addVideoToPlaylist(Video video, Playlist playlist) throws DataBaseException;

	int deleteVideo(int videoTitle) throws IllegalInputException, DataBaseException;

	int deleteVideoFromPlaylist(String videoTitle, Playlist playlist) throws DataBaseException;

	int getLikesForVideo(int video_id) throws DataBaseException;

	int getDislikesForVideo(int video_id) throws DataBaseException;

	void likeVideo(int video_id, int channel_id) throws DataBaseException;

	void dislikeVideo(int video_id, int channel_id) throws DataBaseException;

	void removeLikeDislikeFromVideo(int video_id, int channel_id) throws DataBaseException;

	boolean likeExists(int video_id, int channel_id) throws DataBaseException;

	boolean dislikeExists(int video_id, int channel_id) throws DataBaseException;

	void increaseViewsForVideo(int video_id) throws DataBaseException;



}
