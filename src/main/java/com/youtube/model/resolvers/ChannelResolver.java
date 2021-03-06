package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.User;

//Makes a Channel object with the parameters taken from the ResultSet
public class ChannelResolver implements IResolver<Channel> {

	private final IResolver<User> userResolver = new UserResolver();
	
	@Override
	public Channel resolve(final ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);

		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value

		final Integer channelId = selectedColumns.contains("channel_id") ? rs.getInt("channel_id") : null;
		final User user = userResolver.resolve(rs);
		
		Channel channel = new Channel(channelId, user);
		return channel;
	}

}
