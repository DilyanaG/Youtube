package com.youtube.model.dao.channel;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dto.channel.ProfileViewDTO;
import com.youtube.model.dto.playlist.PlaylistTopViewDTO;
import com.youtube.model.pojo.Channel;

public interface IChannelDAO {


	Channel getChannelById(int id) throws IllegalInputException, DataBaseException;

	Channel getChannelByUserId(int userId) throws IllegalInputException, DataBaseException;

	int getFolowersCountForChannel(int channelId) throws DataBaseException;

	List<Channel> getFollowedChannels(int channelId) throws IllegalInputException, DataBaseException;

	List<Integer> getFollowedChannelIds(int channelId) throws DataBaseException;

	void followChannel(int followerChannelId, int followedChannelId) throws IllegalInputException, DataBaseException;

	void unfollowChannel(int followerChannelId, int followedChannelId) throws IllegalInputException, DataBaseException;

	List<ProfileViewDTO> getAllChannels(String searchWord) throws IllegalInputException, DataBaseException, SQLException;

}