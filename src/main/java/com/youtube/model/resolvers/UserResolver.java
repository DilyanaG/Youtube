package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.youtube.model.pojo.User;

// Makes a User object with the parameters taken from the ResultSet
public class UserResolver implements IResolver<User> {

	@Override
	public User resolve(final ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);
	
		
		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value
		// The "u." is the alias for the "users" table in the DB
			
		final Integer userId = selectedColumns.contains("u.user_id") ? rs.getInt("u.user_id") : null;
		final String userName = selectedColumns.contains("u.user_name") ? rs.getString("u.user_name") : null;
		final String password = selectedColumns.contains("u.password") ? rs.getString("u.password") : null;
		final String email = selectedColumns.contains("u.email") ? rs.getString("u.email") : null;
		final String photoURL = selectedColumns.contains("u.photoURL") ? rs.getString("u.photoURL") : null;
		final Integer isDeleted = selectedColumns.contains("u.isDeleted") ? rs.getInt("u.isDeleted") : null;
		
		User user = new User(userId, userName, password, email, photoURL, isDeleted);
		return user;
	}

}
