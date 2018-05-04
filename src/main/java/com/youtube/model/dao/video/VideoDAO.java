package com.youtube.model.dao.video;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.db.DBManager;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Playlist;
import com.youtube.model.pojo.Video;
import com.youtube.model.resolvers.TagResolver;
import com.youtube.model.resolvers.VideoResolver;

//@Component
public class VideoDAO implements IVideoDAO {

	// selects
	private static final String GET_VIDEO_BY_ID = "SELECT v.*, ch.*,u.* FROM videos AS v JOIN channels AS ch ON v.channel_id = ch.channel_id JOIN users AS u ON u.user_id=ch.user_id WHERE v.video_id = ? AND v.isDeleted = 0;";

	private static final String GET_VIDEO_BY_TITLE = "SELECT v.*, ch.*,u.* FROM videos AS v JOIN channels AS ch ON v.channel_id = ch.channel_id  JOIN users AS u "
			+ "ON u.user_id=ch.user_id WHERE v.title = ? AND v.isDeleted = 0;";

	private static final String SEARCH_VIDEOS_BY_TAGS = "SELECT v.*, ch.* FROM videos AS v JOIN channels AS ch ON v.channel_id = ch.channel_id JOIN videos_has_tags AS vht ON"
			+ " (v.video_id = vht.video_id) WHERE vht.tag_id IN ( SELECT t.tag_id FROM tags AS t WHERE t.content LIKE ?) AND v.isDeleted = 0"
			+ " ORDER BY v.date DESC;";
	private static final String RECENT_VIDEOS = "SELECT v.*, ch.* FROM videos AS v JOIN channels AS ch ON v.channel_id = ch.channel_id   WHERE v.isDeleted = 0"
			+ " ORDER BY v.date DESC;";
	private static final String MOST_POPULAR_VIDEOS = "SELECT v.*, ch.* FROM videos AS v JOIN channels AS ch ON v.channel_id = ch.channel_id   WHERE v.isDeleted = 0"
			+ " ORDER BY v.views DESC;";
	
	private static final String SELECT_ALL_VIDEOS_BY_CHANNEL_ID = 
			"SELECT v.*, ch.* FROM videos AS v JOIN channels AS ch ON v.channel_id = ch.channel_id "
			+ "WHERE v.channel_id = ? AND v.isDeleted = 0;";

	private static final String SELECT_ALL_VIDEOS_BY_PLAYLIST_ID = "SELECT v.*, ch.* FROM videos AS v JOIN channels AS ch ON v.channel_id = ch.channel_id "
			+ "JOIN  playlists_has_videos AS phv ON(v.video_id = phv.video_id) WHERE phv.playlist_id = ? AND v.isDeleted = 0;";

	private static final String GET_TAG_ID = "SELECT t.* FROM tags AS t WHERE t.content = ?;";

	private static final String GET_TAG_COUNT = "SELECT COUNT(t.tag_id) as count FROM tags AS t WHERE t.content = ?;";

	private static final String COUNT_OF_LIKES = "SELECT COUNT(vhld.id) as count FROM videos_has_likes_dislikes AS vhld JOIN videos AS v ON vhld.video_id = v.video_id WHERE v.video_id = ? AND v.isDeleted = 0 AND vhld.isLike = 1;";;

	private static final String COUNT_OF_DISLIKES = "SELECT COUNT(vhld.id) as count FROM videos_has_likes_dislikes AS vhld JOIN videos AS v ON vhld.video_id = v.video_id WHERE v.video_id = ? AND v.isDeleted = 0 AND vhld.isLike = 0;";

	private static final String LIKE_EXISTS = "SELECT COUNT(vhld.id) as count FROM videos_has_likes_dislikes AS vhld JOIN videos AS v ON vhld.video_id = v.video_id WHERE vhld.video_id = ? AND vhld.channel_id = ? AND v.isDeleted = 0 AND vhld.isLike = 1;";

	private static final String DISLIKE_EXISTS = "SELECT COUNT(vhld.id) as count FROM videos_has_likes_dislikes AS vhld JOIN videos AS v ON vhld.video_id = v.video_id WHERE vhld.video_id = ? AND vhld.channel_id = ? AND v.isDeleted = 0 AND vhld.isLike = 0;";

	// inserts
	private static final String ADD_VIDEO_TO_CHANNEL = " INSERT INTO videos (channel_id, video_url, photo_url, title, date, description, views) VALUES (?,?,?,?,now(),?,?);";

	private static final String WRITE_IN_VIDEOS_HAS_TAGS = "INSERT INTO videos_has_tags (video_id,tag_id) VALUES (?,?)";

	private static final String INSERT_TAG = "INSERT INTO tags (content) VALUES (?);";

	private static final String INSERT_VIDEO_IN_PLAYLIST = "INSERT INTO playlists_has_videos (video_id,playlist_id) VALUES (?,?)";

	private static final String LIKE_VIDEO = "INSERT INTO videos_has_likes_dislikes (isLike,video_id,channel_id) VALUES (1,?,?)";

	private static final String DISLIKE_VIDEO = "INSERT INTO videos_has_likes_dislikes (isLike,video_id,channel_id) VALUES (0,?,?)";;

	// deletes
	private static final String DELETE_VIDEO = "DELETE FROM videos WHERE title = ?;";

	private static final String DELETE_VIDEO_FROM_PLAYLIST = "DELETE FROM playlists_has_videos WHERE playlist_id = ? AND video_id IN("
			+ "SELECT v.video_id FROM videos AS v WHERE v.title = ?);";

	private static final String REMOVE_LIKE_DISLIKE_FROM_VIDEO = "DELETE FROM videos_has_likes_dislikes WHERE video_id = ? AND channel_id = ?;";

	

	
	
	//@Autowired
	private static DBManager dbManager=DBManager.getInstance();

	private static VideoDAO instance;
	public synchronized static VideoDAO getInstance() {
		if(instance==null)
			instance=new VideoDAO();
		return instance;
	}
	private VideoDAO() {
	
	}

	@Override
	public Video getVideoById(int video_id) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();
		try {
			dbManager.startTransaction(connection);
			Video video = dbManager.executeSingleSelect(connection, GET_VIDEO_BY_ID, new VideoResolver(), video_id);
			dbManager.commit(connection);
			return video;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public Video getVideoByTitle(String videoname) throws IllegalInputException, DataBaseException {

		final Connection connection = dbManager.getConnection();
		try {
			dbManager.startTransaction(connection);
			Video video = dbManager.executeSingleSelect(connection, GET_VIDEO_BY_TITLE, new VideoResolver(), videoname);
			dbManager.commit(connection);
			return video;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}
 
	public List<Video> getMostPopularVideos() throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();
		try {
			dbManager.startTransaction(connection);
			List<Video> videos = dbManager.executeSelect(connection, MOST_POPULAR_VIDEOS, new VideoResolver());
			dbManager.commit(connection);
			return Collections.unmodifiableList(videos);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}
	
	public List<Video> getRecentVideos() throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();
		try {
			dbManager.startTransaction(connection);
			List<Video> videos = dbManager.executeSelect(connection, RECENT_VIDEOS, new VideoResolver());
			dbManager.commit(connection);
			return Collections.unmodifiableList(videos);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}
	
	
	@Override
	public List<Video> getVideosByTagAndSortByDate(String tag) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		// TODO to be checked!
		final String newTag = "%" + tag + "%";

		try {
			dbManager.startTransaction(connection);
			List<Video> videos = dbManager.executeSelect(connection, SEARCH_VIDEOS_BY_TAGS, new VideoResolver(),
					newTag);
			dbManager.commit(connection);
			return Collections.unmodifiableList(videos);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public List<Video> getVideosByChannelId(int channel_id) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<Video> videos = dbManager.executeSelect(connection, SELECT_ALL_VIDEOS_BY_CHANNEL_ID,
					new VideoResolver(), channel_id);
			dbManager.commit(connection);
			return Collections.unmodifiableList(videos);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public List<Video> getAllVideosFromPlaylist(Playlist playlist) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<Video> videos = dbManager.executeSelect(connection, SELECT_ALL_VIDEOS_BY_PLAYLIST_ID,
					new VideoResolver(), playlist.getPlaylistId());
			dbManager.commit(connection);
			return Collections.unmodifiableList(videos);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public int addVideo(Video video, int channelId) throws DataBaseException  {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			int inserted = dbManager.execute(connection, ADD_VIDEO_TO_CHANNEL, channelId,
					video.getVideoUrl(), video.getPhotoUrl(), video.getTitle(), video.getDescription(), 0);
			System.out.println("tuka e ");
			dbManager.commit(connection);
			Video addedVideo = dbManager.executeSingleSelect(connection, GET_VIDEO_BY_TITLE, new VideoResolver(),
					video.getTitle());
			System.out.println("tuka e 1");
			writeInVideosHasTagsTable(connection, addedVideo.getTitle() + " " + addedVideo.getDescription(),
					addedVideo.getVideoId());
			System.out.println("tuka e 3");
			dbManager.commit(connection);
			
			return inserted;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return -1;
		}
	}

	private void writeInVideosHasTagsTable(Connection connection, String videoTags, int video_id) throws SQLException {
		String[] tags = videoTags.split("[^a-zA-Z0-9]");

		for (String tag : tags) {

			int tagCount = dbManager.executeSingleSelect(connection, GET_TAG_COUNT, (rs) -> rs.getInt("count"), tag);
			if (tagCount == 0) {
				dbManager.execute(connection, INSERT_TAG, tag);
			}
			int tagId = dbManager.executeSingleSelect(connection, GET_TAG_ID, new TagResolver(), tag).getTagId();
			dbManager.execute(connection, WRITE_IN_VIDEOS_HAS_TAGS, video_id, tagId);

		}
	}

	@Override
	public int addVideoToPlaylist(Video video, Playlist playlist) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			int inserted = dbManager.execute(connection, INSERT_VIDEO_IN_PLAYLIST, video.getVideoId(),
					playlist.getPlaylistId());
			dbManager.commit(connection);
			return inserted;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return -1;
		}
	}

	@Override
	public int deleteVideoFromPlaylist(String videoTitle, Playlist playlist) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			int deleted = dbManager.execute(connection, DELETE_VIDEO_FROM_PLAYLIST, playlist.getPlaylistId(),
					videoTitle);
			dbManager.commit(connection);
			return deleted;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return -1;
		}
	}

	@Override
	public int deleteVideo(String videoTitle) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			int deleted = dbManager.execute(connection, DELETE_VIDEO, videoTitle);
			dbManager.commit(connection);
			return deleted;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return -1;
		}
	}

	@Override
	public int getLikesForVideo(int video_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			final int likesCount = dbManager.executeSingleSelect(connection, COUNT_OF_LIKES, (rs) -> rs.getInt("count"),
					video_id);
			dbManager.commit(connection);
			return likesCount;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return -1;
		}
	}

	@Override
	public int getDislikesForVideo(int video_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			final int dislikesCount = dbManager.executeSingleSelect(connection, COUNT_OF_DISLIKES,
					(rs) -> rs.getInt("count"), video_id);
			dbManager.commit(connection);
			return dislikesCount;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return -1;
		}
	}

	@Override
	public void likeVideo(int video_id, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, LIKE_VIDEO, video_id, channel_id);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void dislikeVideo(int video_id, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, DISLIKE_VIDEO, video_id, channel_id);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public boolean likeExists(int video_id, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			final int likeCount = dbManager.executeSingleSelect(connection, LIKE_EXISTS, (rs) -> rs.getInt("count"),
					video_id, channel_id);
			dbManager.commit(connection);
			return (likeCount == 1) ? true : false;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return false;
		}
	}

	@Override
	public boolean dislikeExists(int video_id, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			final int likeCount = dbManager.executeSingleSelect(connection, DISLIKE_EXISTS, (rs) -> rs.getInt("count"),
					video_id, channel_id);
			dbManager.commit(connection);
			return (likeCount == 1) ? true : false;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return false;
		}
	}

	@Override
	public void removeLikeDislikeFromVideo(int video_id, int channel_id) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, REMOVE_LIKE_DISLIKE_FROM_VIDEO, video_id, channel_id);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}
	public static void main(String[] args) throws DataBaseException, IllegalInputException {
		System.out.println(new VideoDAO().getRecentVideos());
	}

	
}
