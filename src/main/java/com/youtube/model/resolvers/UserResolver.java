package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.youtube.model.pojo.User;

// Makes a User object with the parameters taken from the ResultSet
public class UserResolver implements IResolver<User> {

	@Override
	public User resolve(final ResultSet rs) throws SQLException {
		System.out.println(rs.getMetaData().getColumnCount());
		final List<String> selectedColumns = getColumnNames(rs);
	
		final Integer userId = selectedColumns.contains("user_id") ? rs.getInt("user_id") : null;
		final String userName = selectedColumns.contains("user_name") ? rs.getString("user_name") : null;
		final String password = selectedColumns.contains("password") ? rs.getString("password") : null;
		final String email = selectedColumns.contains("email") ? rs.getString("email") : null;
		final String photoURL = selectedColumns.contains("photoURL") ? rs.getString("photoURL") : null;
		
		System.out.println(userId);
		System.out.println(userName);
		System.out.println(password);
		System.out.println(email);
		System.out.println(photoURL);
		
		
		User user = new User(userId, userName, password, email, photoURL);
		return user;
	}

}
