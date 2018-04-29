package com.youtube.model.dao.channel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.exceptions.verification.NeverWantedButInvoked;
import org.springframework.beans.factory.annotation.Autowired;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.CommentDAO;
import com.youtube.model.dao.DBManager;
import com.youtube.model.dao.VideoDAO;
import com.youtube.model.dao.user.IUserDAO;
import com.youtube.model.pojo.*;

public class ChannelDAO implements IChannelDAO {
	// for DB
	// selects
	private static final String ALL_CHANNELS = "SELECT channel_id,user_id FROM channels  WHERE isDeleted = 0 ;";

	private static final String BY_USER_ID = "SELECT channel_id FROM channels  WHERE user_id = ?"
			+ "AND isDeleted = 0;";

	private static final String BY_CHANNEL_ID = "SELECT channel_id, user_name FROM channels JOIN users "
			+ "ON users.user_id=channels.user_id WHERE channels.channel_id = ? AND channels.isDeleted = 0;";

	private static final String COUNT_OF_FOLLOWERS = "SELECT COUNT(follower_channel_id) as count FROM channels_followed_channels"
			+ " WHERE followed_channel_id = ? AND isDeleted = 0;";

	private static final String FOLLOWED_CHANNELS = "SELECT user_name,f.channel_id as channel_id FROM users u "
			+ "JOIN channels f ON f.user_id=u.user_id"
			+ " JOIN channels_followed_channels t ON f.channel_id = followed_channel_id WHERE follower_channel_id = ?"
			+ " AND f.isDeleted = 0 ;";
	// inserts
	private static final String INSERT_INTO_CHANNELS = "INSERT INTO channels (user_id) VALUES (?);";

	private static final String FOLLOW_CHANNEL = "INSERT INTO channels_followed_channels"
			+ " (follower_channel_id, followed_channel_id) VALUES (?,?);";

	// delete

	private static final String UNFOLLOW_CHANNEL = "DELETE FROM channels_followed_channels where follower_channel_id = ? and followed_channel_id = ? ;";

	private static final String DELETE_USER_PROFILE = "UPDATE channels SET isDeleted = 1 WHERE channel_id = ?;";

	private static final String DELETE_FOLLOWERS = "DELETE FROM channels_followed_channels where follower_channel_id = ? ";

	private static final String DELETE_FOLLOWEDS = "DELETE FROM channels_followed_channels where followed_channel_id = ? ";

	@Autowired
	private DBManager dbManager;
	@Autowired
	private IUserDAO userDao;
	@Autowired
	private VideoDAO videoDao;
	@Autowired
	private CommentDAO commentDao;

	private Connection connection = dbManager.getConnection();
	// DB

	@Override
	public void addNewChannelToDB(int userId) throws DataBaseException {
		PreparedStatement st;
		try {
			st = connection.prepareStatement(INSERT_INTO_CHANNELS);
			st.setInt(1, userId);
			st.executeUpdate();
			st.close();

		} catch (SQLException e) {
			throw new DataBaseException("Database problem", e);
		}

	}

	
	@Override
	public Channel getChannelById(int id) throws IllegalInputException, DataBaseException {
		PreparedStatement channelSt;
		try {
			channelSt = connection.prepareStatement(BY_CHANNEL_ID);
			channelSt.setInt(1, id);
			ResultSet channelRS = channelSt.executeQuery();
			List<Channel> list = createChannelsFromRezultSet(channelRS);
			channelSt.close();
			if (list.isEmpty()) {
				throw new IllegalInputException("CHANNEL WITH THIS ID NOT FOUND");
			}
			return list.get(0);

		} catch (SQLException e) {
			throw new DataBaseException("Database problem", e);
		}

	}


	@Override
	public Map<String, Channel> getAllChannels() throws IllegalInputException, DataBaseException {
		Map<String, Channel> channels = new HashMap<String, Channel>();
		PreparedStatement channelSt;
		try {
			channelSt = connection.prepareStatement(ALL_CHANNELS);
			ResultSet channelRS = channelSt.executeQuery();

			for (Channel channel : createChannelsFromRezultSet(channelRS)) {
				channels.put(channel.getUser().getUserName(), channel);
			}
			channelRS.close();
			channelSt.close();

			return Collections.unmodifiableMap(channels);
		} catch (SQLException e) {
			throw new DataBaseException("Database problem", e);
		}

	}

	private List<Channel> createChannelsFromRezultSet(ResultSet channelRS)
			throws IllegalInputException, DataBaseException {
		List<Channel> allChannels = new ArrayList<Channel>();

		try {
			User user;
			while (channelRS.next()) {
				user = userDao.getUserByUserName(channelRS.getString("user_name"));
				Channel channel = new Channel(channelRS.getInt("channel_id"), user);
				allChannels.add(channel);
				return allChannels;
			}
		} catch (SQLException e) {
			throw new DataBaseException("Database problem", e);
		}
		return allChannels;

	}


	@Override
	public Channel getChannelByUserId(int userId) throws IllegalInputException, DataBaseException {
		PreparedStatement channelSt;
		try {
			channelSt = connection.prepareStatement(BY_USER_ID);

			channelSt.setInt(1, userId);
			ResultSet channelRS = channelSt.executeQuery();
			List<Channel> list = createChannelsFromRezultSet(channelRS);
			if (list.isEmpty()) {
				throw new IllegalInputException("CHANNEL WITH THIS USER ID NOT FOUND!");
			}
			return list.get(0);
		} catch (SQLException e) {
			throw new IllegalInputException("DATABASE ERROR!", e);
		}
	}


	@Override
	public int getFolowersCountForChannel(int channelId) throws DataBaseException {
		try {
			PreparedStatement channelSt = connection.prepareStatement(COUNT_OF_FOLLOWERS);
			channelSt.setInt(1, channelId);
			ResultSet channelRS = channelSt.executeQuery();
			int count = 0;
			while (channelRS.next()) {
				count = channelRS.getInt("count");
			}
			return count;
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE ERROR!", e);
		}
	}


	@Override
	public List<Channel> getFollowedChannels(int channelId) throws IllegalInputException, DataBaseException {
		try {
			PreparedStatement channelSt = connection.prepareStatement(FOLLOWED_CHANNELS);
			channelSt.setInt(1, channelId);
			ResultSet channelRS = channelSt.executeQuery();
			List<Channel> list = new ArrayList<>();
			list = createChannelsFromRezultSet(channelRS);
			channelRS.close();
			channelSt.close();
			return list;
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE ERROR!", e);
		}
	}

	@Override
	public void followChannel(int followerChannelId, int followedChannelId) throws IllegalInputException, DataBaseException {
		try {
			if (followerChannelId == followedChannelId) {
				throw new IllegalInputException("YOU CAN NOT FOLLOW OWN CHANNEL!");
			}
			PreparedStatement st = connection.prepareStatement(FOLLOW_CHANNEL);
			st.setInt(1, followerChannelId);
			st.setInt(2, followedChannelId);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			throw new DataBaseException("DATABASE ERROR!", e);
		}

	}

	
	@Override
	public void unfollowChannel(int followerChannelId, int followedChannelId) throws IllegalInputException, DataBaseException {
		if (followerChannelId== followedChannelId) {
			throw new IllegalInputException("INVALID INPUT");
		}
		try{
		PreparedStatement st = connection.prepareStatement(UNFOLLOW_CHANNEL);
		st.setInt(1, followerChannelId);
		st.setInt(2, followedChannelId);
		st.executeUpdate();
		st.close();
		}catch (SQLException e) {
			throw new DataBaseException("DATABASE ERROR!", e);
		}
	}
	
	
	@Override
	public void deleteUserProfile(int userId) throws IllegalInputException, DataBaseException {
		try {
			int channelId = this.getChannelIdByUserId(userId);
			videoDao.deleteUserProfileVideos(channelId);
			commentDao.deleteUserProfileComments(channelId);
			PreparedStatement st = connection.prepareStatement(DELETE_USER_PROFILE);
			st.setInt(1, userId);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			throw new DataBaseException("Database Problem", e);
		}

	}

	private int getChannelIdByUserId(int userId) throws IllegalInputException, DataBaseException {
		try {
			PreparedStatement channelSt = connection.prepareStatement(BY_USER_ID);
			channelSt.setInt(1, userId);
			ResultSet channelRS = channelSt.executeQuery();
			if (channelRS.next()) {
				return channelRS.getInt("channel_id");
			}
			throw new IllegalInputException("CHANNEL WITH THIS USER ID NOT FOUND!");
		} catch (SQLException e) {
			throw new DataBaseException("Database Problem", e);
		}

	}

}
