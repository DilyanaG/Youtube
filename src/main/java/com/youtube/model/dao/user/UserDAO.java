package com.youtube.model.dao.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.db.DBManager;
import com.youtube.model.pojo.User;
import com.youtube.model.resolvers.UserResolver;

@Component
public class UserDAO implements IUserDAO {

	// selects
	private static final String BY_ID = "SELECT u.* FROM users AS u WHERE u.user_id = ? AND u.isDeleted = 0;";

	private static final String BY_USERNAME = "SELECT u.* FROM users AS u WHERE u.user_name = ? AND u.isDeleted = 0;";

	private static final String BY_EMAIL = "SELECT u.* FROM users AS u WHERE u.email = ? AND u.isDeleted = 0;";

	private static final String BY_USERNAME_AND_PASSWORD = "SELECT u.user_id FROM users AS u WHERE u.user_name = ? AND u.password = sha1(?) AND isDeleted = 0;";

	private static final String SELECT_ALL_USERS = "SELECT u.* FROM users AS u WHERE u.isDeleted = 0;";

	// insert
	private static final String INSERT_INTO_USERS = "INSERT INTO users (user_name, password, email, profile_picture_url) VALUES (?,sha1(?),?,?);";

	private static final String INSERT_INTO_CHANNELS = "INSERT INTO channels (user_id) VALUES (?);";
	
	
	// update
	private static final String UPDATE_USER_PASSWORD = "UPDATE users SET password = sha1(?) WHERE user_id = ? AND password = sha1(?);";

	private static final String UPDATE_PROFILE_PICTURE = "UPDATE users SET profile_picture_url = ? WHERE user_id = ?;";

	private static final String DELETE_PROFILE_PICTURE = "UPDETE users SET profile_picture_url = null WHERE user_id = ?;";

	// delete

	private static final String DELETE_USER = "UPDATE users SET isDeleted = 1 WHERE user_id = ?;";

	private static final String DELETE_CHANNEL = "UPDATE channels SET isDeleted = 1 WHERE channels.user_id = ?;";
	
	private static final String DELETE_CHANNEL_VIDEOS = "UPDATE videos SET isDeleted = 1 WHERE videos.channel_id = (SELECT channel_id FROM channels WHERE channels.user_id = ?);";

	

	@Autowired
	private DBManager dbManager; 
	
	@Override
	public User getUserById(int userId) throws DataBaseException, IllegalInputException {
		final Connection connection = dbManager.getConnection();
		try {
			dbManager.startTransaction(connection);
			User user = dbManager.executeSingleSelect(connection, BY_ID, new UserResolver(), userId);
			dbManager.commit(connection);
			return user;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public int addNewUserToDB(User user) throws DataBaseException, IllegalInputException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			int inserted = dbManager.execute(connection, INSERT_INTO_USERS, user.getUserName(), user.getPassword(),
					user.getEmail(), user.getPhotoURL());
			int userId = dbManager.executeSingleSelect(connection, BY_USERNAME_AND_PASSWORD, new UserResolver(), user.getUserName(), user.getPassword()).getUserId();
			dbManager.execute(connection, INSERT_INTO_CHANNELS, userId);	
			dbManager.commit(connection);
			return inserted;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return 0;
		}
	}

	@Override
	public Map<String, User> getAllUsers() throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			List<User> users = dbManager.executeSelect(connection, SELECT_ALL_USERS, new UserResolver());
			dbManager.commit(connection);
			return groupUsersByName(users);
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	private Map<String, User> groupUsersByName(List<User> users) {
		final Map<String, User> allUsers = new HashMap<>();
		for (User user : users) {
			allUsers.put(user.getUserName(), user);
		}
		return Collections.unmodifiableMap(allUsers);
	}

	@Override
	public User getUserByUserName(String username) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			User user = dbManager.executeSingleSelect(connection, BY_USERNAME, new UserResolver(), username);
			dbManager.commit(connection);
			return user;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}
	
	@Override
	public User getUserByEmail(String email) throws DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			User user = dbManager.executeSingleSelect(connection, BY_EMAIL, new UserResolver(), email);
			dbManager.commit(connection);
			return user;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return null;
		}
	}

	@Override
	public boolean updatePassword(int user_id, String oldPassword, String newPassword) throws DataBaseException, IllegalInputException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			int updated = dbManager.execute(connection, UPDATE_USER_PASSWORD, newPassword, user_id, oldPassword);

			if (updated == 0) {
				throw new IllegalInputException("USER NOT FOUND !");
			}
			
			dbManager.commit(connection);
			return true;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return false;
		}
	}

	@Override
	public boolean updateProfilePicture(String pictureURL, int userId) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			int updated = dbManager.execute(connection, UPDATE_PROFILE_PICTURE, pictureURL, userId);

			if (updated == 0) {
				throw new IllegalInputException("USER NOT FOUND !");
			}
			
			dbManager.commit(connection);
			return true;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return false;
		}
	}

	@Override
	public boolean deleteProfilePicture(int userId) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			int updated = dbManager.execute(connection, DELETE_PROFILE_PICTURE, userId);

			if (updated == 0) {
				throw new IllegalInputException("USER NOT FOUND !");
			}
			
			dbManager.commit(connection);
			return true;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return false;
		}
	}

	@Override
	public boolean deleteUser(int userId) throws IllegalInputException, DataBaseException {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			dbManager.execute(connection, DELETE_USER, userId);
			dbManager.execute(connection, DELETE_CHANNEL, userId);
			dbManager.execute(connection, DELETE_CHANNEL_VIDEOS, userId);
			dbManager.commit(connection);
			return true;
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return false;
		}
	}

	@Override
	public int loginUser(User user) throws DataBaseException  {
		final Connection connection = dbManager.getConnection();

		try {
			dbManager.startTransaction(connection);
			User newUser = dbManager.executeSingleSelect(connection, BY_USERNAME_AND_PASSWORD, new UserResolver(),
					user.getUserName(), user.getPassword());
			dbManager.commit(connection);
			return newUser.getUserId();
		} catch (SQLException s) {
			dbManager.rollback(connection, s);
			return 0;
		}
	}
}
