package com.youtube.model.dao.channel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.db.DBManager;
import com.youtube.model.dto.channel.ProfileViewDTO;
import com.youtube.model.dto.playlist.PlaylistTopViewDTO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.resolvers.ChannelResolver;

@Component
public class ChannelDAO implements IChannelDAO {

	// selects
	private static final String ALL_CHANNELS = "SELECT ch.*,u.* FROM channels AS ch JOIN users u ON ch.user_id=u.user_id WHERE u.user_name LIKE ? AND ch.isDeleted = 0;";

	private static final String BY_CHANNEL_ID = "SELECT ch.*, u.* FROM channels AS ch JOIN users AS u ON u.user_id=ch.user_id WHERE ch.channel_id = ? AND ch.isDeleted = 0;";

	private static final String CHANNEL_BY_USER_ID = "SELECT ch.*, u.* FROM channels AS ch JOIN users AS u ON u.user_id=ch.user_id WHERE ch.user_id = ? AND ch.isDeleted = 0;";;

	private static final String COUNT_OF_FOLLOWERS = "SELECT COUNT(follower_channel_id) as count FROM channels_followed_channels AS cfc"
			+ " JOIN channels AS fc ON fc.channel_id = cfc.follower_chanel_id WHERE cfc.followed_channel_id = ? AND fc.isDeleted = 0;";

	private static final String FOLLOWED_CHANNELS = " SELECT u.*, ch.* FROM users u "
			+ " JOIN channels ch ON ch.user_id = u.user_id"
			+ " JOIN channels_followed_channels cfc ON ch.channel_id = cfc.followed_channel_id "
			+ " WHERE cfc.follower_channel_id = ? AND ch.isDeleted = 0 ;";

	private static final String FOLLOWED_CHANNELS_IDS = "SELECT follower_channel_id as id FROM channels_followed_channels WHERE followed_channel_id = ?;";

	// inserts
	private static final String FOLLOW_CHANNEL = "INSERT INTO channels_followed_channels"
			+ " (follower_channel_id, followed_channel_id) VALUES (?,?);";

	// delete
private static final String UNFOLLOW_CHANNEL = "DELETE FROM channels_followed_channels where follower_channel_id = ? and followed_channel_id = ? ;";



	
	@Autowired
	private DBManager dbManager;
	
	@Override
	public Channel getChannelById(int id) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			Channel channel = dbManager.executeSingleSelect(connection, BY_CHANNEL_ID, new ChannelResolver(), id);
			dbManager.commit(connection);
			return channel;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public List<ProfileViewDTO> getAllChannels(String searchWord) throws IllegalInputException, DataBaseException, SQLException {
		final Connection connection = dbManager.getConnection();

		List<Channel> channels = new ArrayList<Channel>();
        searchWord="%"+searchWord+"%";
		channels = dbManager.executeSelect(connection, ALL_CHANNELS, new ChannelResolver(),searchWord);
		List<ProfileViewDTO> channelViews= new ArrayList<>();
		for(Channel c:channels){
			channelViews.add(new ProfileViewDTO(c));
		}
		return Collections.unmodifiableList(channelViews);
	}

	@Override
	public Channel getChannelByUserId(int userId) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			ChannelResolver channelResolver = new ChannelResolver();
			Channel channel = dbManager.executeSingleSelect(connection, CHANNEL_BY_USER_ID, channelResolver, userId);
			dbManager.commit(connection);
			return channel;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public int getFolowersCountForChannel(int channelId) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			final int followersCount = dbManager.executeSingleSelect(connection, COUNT_OF_FOLLOWERS,
					(rs) -> rs.getInt("count"), channelId);
			dbManager.commit(connection);
			return followersCount;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return -1;
		}
	}

	@Override
	public List<Channel> getFollowedChannels(int channelId) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			ChannelResolver channelResolver = new ChannelResolver();
			List<Channel> channels = dbManager.executeSelect(connection, FOLLOWED_CHANNELS, channelResolver, channelId);
			dbManager.commit(connection);
			return channels;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public List<Integer> getFollowedChannelIds(int channelId) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<Integer> channels = dbManager.executeSelect(connection, FOLLOWED_CHANNELS_IDS, (rs) -> rs.getInt("id"), channelId);
			dbManager.commit(connection);
			return channels;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}
	
	@Override
	public void followChannel(int followerChannelId, int followedChannelId)
			throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();
		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, FOLLOW_CHANNEL, followerChannelId, followedChannelId);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}

	@Override
	public void unfollowChannel(int followerChannelId, int followedChannelId)
			throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, UNFOLLOW_CHANNEL, followerChannelId, followedChannelId);
			dbManager.commit(connection);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
		}
	}
}
