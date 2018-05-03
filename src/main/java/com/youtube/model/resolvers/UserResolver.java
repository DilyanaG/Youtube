package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.youtube.model.pojo.User;

import ch.qos.logback.core.net.SyslogOutputStream;

// Makes a User object with the parameters taken from the ResultSet
public class UserResolver implements IResolver<User> {

	@Override
	public User resolve(final ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);
	
		
		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value
		// The "u." is the alias for the "users" table in the DB
			
		final Integer userId = selectedColumns.contains("user_id") ? rs.getInt("user_id") : null;
		final String userName = selectedColumns.contains("user_name") ? rs.getString("user_name") : null;
		final String password = selectedColumns.contains("password") ? rs.getString("password") : null;
		final String email = selectedColumns.contains("email") ? rs.getString("email") : null;
		final String photoURL = selectedColumns.contains("photoURL") ? rs.getString("photoURL") : null;
		System.out.println(userId);
		System.out.println(userName);
		System.out.println(email);
		System.out.println(photoURL);
		
		User user = new User(userId, userName, password, email, photoURL);
		return user;
	}

}
