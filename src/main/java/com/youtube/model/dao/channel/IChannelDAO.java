package com.youtube.model.dao.channel;

import java.util.List;
import java.util.Map;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.pojo.Channel;

public interface IChannelDAO {


	Channel getChannelById(int id) throws IllegalInputException, DataBaseException;

	Map<String, Channel> getAllChannels() throws IllegalInputException, DataBaseException;

	Channel getChannelByUserId(int userId) throws IllegalInputException, DataBaseException;

	int getFolowersCountForChannel(int channelId) throws DataBaseException;

	List<Channel> getFollowedChannels(int channelId) throws IllegalInputException, DataBaseException;

	List<Integer> getFollowedChannelIds(int channelId) throws DataBaseException;

	void followChannel(int followerChannelId, int followedChannelId) throws IllegalInputException, DataBaseException;

	void unfollowChannel(int followerChannelId, int followedChannelId) throws IllegalInputException, DataBaseException;

}