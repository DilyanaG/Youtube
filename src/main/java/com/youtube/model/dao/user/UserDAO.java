package com.youtube.model.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.youtube.model.dao.DBManager;
import com.youtube.model.dao.channel.IChannelDAO;
import com.youtube.model.pojo.User;

@Component
public class UserDAO implements IUserDAO {
	// DB
	// selects
	private static final String BY_ID = "SELECT user_id,user_name,email,password,photoUrl FROM users WHERE user_id = ? AND isDeleted = 0;";

	private static final String BY_USERNAME ="SELECT user_id,user_name,email,password,photoUrl FROM users WHERE user_name = ? AND isDeleted = 0;"; 
	
	private static final String BY_USERNAME_AND_PASSWORD = "SELECT user_id FROM users "
			+ "WHERE user_name = ? AND user_password = sha1(?)  AND isDeleted = 0;";

	private static final String SELECT_ALL_USERS = "SELECT user_id,user_name,email,password,photoUrl FROM users WHERE isDeleted = 0;;";
	// insert
	private static final String INSERT_INTO_USERS = "INSERT INTO users (user_name, password, email,photoUrl) VALUES (?,sha1(?),?,?);";

	// update
	private static final String UPDATE_USER_PASSWORD = "UPDATE users SET password = sha1(?) WHERE user_id = ?;";

	private static final String UPDATE_PROFILE_PICTURE = "UPDATE users SET photoUrl = ? WHERE user_id = ?;";
	// delete
	private static final String DELETE_USER = "UPDATE users SET isDeleted = 1 WHERE user_id = ?;";

	private static final String DELETE_PROFILE_PICTURE = " UPDETE users SET photoUrl = null WHERE user_id = ?;";

	

	@Autowired
	private static DBManager dbManager;

	@Autowired
	private static IChannelDAO channelDao;

	// connection to DB
	private Connection connection =  dbManager.getConnection();
 
	@Override
	public User getUserById(int userId) throws DataBaseException, IllegalInputException {
		try {
			PreparedStatement userST = connection.prepareStatement(BY_ID);
			userST.setInt(1, userId);
			ResultSet usersRS = userST.executeQuery();
			List<User> user = new ArrayList<>();

			user = getUsersFromRezultSet(usersRS);

			if (user.isEmpty()) {
				throw new IllegalInputException("USER WITH THIS ID NOT FOUND!");
			}
			return user.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("DATABASE ERROR!", e);
		}
	}

	@Override
	public int addNewUserToDB(User user) throws DataBaseException, IllegalInputException {
		try {
			PreparedStatement st = connection.prepareStatement(INSERT_INTO_USERS,
					PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, user.getUserName());
			st.setString(2, user.getPassword());
			st.setString(3, user.getEmail());
			st.setString(4, user.getPhotoURL());
			st.executeUpdate();
			ResultSet usersRS = st.getGeneratedKeys();
			if (usersRS.next()) {
				int id = usersRS.getInt("user_id");
				channelDao.addNewChannelToDB(id);
				return id;
			} else {
				throw new IllegalInputException("Invalid Registration!");
			}
		} catch (SQLException e) {
			throw new DataBaseException("Database Error", e);
		}
	}

	@Override
	public Map<String, User> getAllUsers() throws IllegalInputException, DataBaseException {
		Map<String, User> users = new HashMap<String, User>();
		PreparedStatement userST;
		try {
			userST = connection.prepareStatement(SELECT_ALL_USERS);
			ResultSet usersRS = userST.executeQuery();
			// get all users
			List<User> list = getUsersFromRezultSet(usersRS);
			for (User user : list) {
				users.put(user.getUserName(), user);
			}
			usersRS.close();
			// System.out.println("Users loaded successfully");
			return Collections.unmodifiableMap(users);
		} catch (SQLException e) {
			throw new DataBaseException("Database Error", e);
		}

	}

	private List<User> getUsersFromRezultSet(ResultSet usersRS) throws IllegalInputException, DataBaseException {
		List<User> users = new ArrayList<>();

		try {
			while (usersRS.next()) {
				String username;
				username = usersRS.getString("user_name");
				if (username == null) {
					throw new IllegalInputException("USER WITH THIS USER NAME NOT FOUND!");
				}
				User user = new User(usersRS.getInt("user_id"), username, usersRS.getString("password"),
						usersRS.getString("email"), usersRS.getString("photo"));
				users.add(user);

			}

			return users;
		} catch (SQLException e) {
			throw new DataBaseException("Database Error", e);
		}

	}

	@Override
	public User getUserByUserName(String username) throws IllegalInputException, DataBaseException {

		PreparedStatement userST;
		try {
			userST = connection.prepareStatement(BY_USERNAME);
			userST.setString(1, username);
			ResultSet usersRS = userST.executeQuery();
			List<User> user = new ArrayList<>();
			user = getUsersFromRezultSet(usersRS);
			if (user.isEmpty()) {
				throw new IllegalInputException("USER WITH THIS USERNAME NOT FOUND!");
			}
			return user.get(0);

		} catch (SQLException e) {
			throw new DataBaseException("Database Error", e);
		}

	}

	@Override
	public boolean updatePassword(int user_id, String newPassword) throws DataBaseException, IllegalInputException {
		PreparedStatement st;
		try {
			st = connection.prepareStatement(UPDATE_USER_PASSWORD);
			st.setString(1, newPassword);
			st.setInt(2, user_id);
			int count = st.executeUpdate();
			if (count == 0) {
				throw new IllegalInputException("USER NOT FOUND !");
			}
			return true;
		} catch (SQLException e) {
			throw new DataBaseException("Database Error", e);
		}

	}

	@Override
	public boolean updateProfilePicture(String pictureURL, int userId) throws IllegalInputException, DataBaseException {
		PreparedStatement st;
		try {
			st = connection.prepareStatement(UPDATE_PROFILE_PICTURE);
			st.setString(1, pictureURL);
			st.setInt(2, userId);
			int count = st.executeUpdate();
			if (count == 0) {
				throw new IllegalInputException("USER NOT FOUND !");
			}
			return true;
		} catch (SQLException e) {
			throw new DataBaseException("Database Error", e);
		}
	}

	@Override
	public boolean deleteProfilePicture(int userId) throws IllegalInputException, DataBaseException {

		PreparedStatement st;
		try {
			st = connection.prepareStatement(DELETE_PROFILE_PICTURE);
			st.setInt(1, userId);
			int count = st.executeUpdate();
			if (count == 0) {
				throw new IllegalInputException("USER NOT FOUND !");
			}
			return true;
		} catch (SQLException e) {
			throw new DataBaseException("Database Error", e);
		}
	}

	@Override
	public boolean deleteUser(int userId) throws IllegalInputException, DataBaseException {

		try {
			connection.setAutoCommit(false);
			channelDao.deleteUserProfile(userId);
			PreparedStatement st = connection.prepareStatement(DELETE_USER);
			st.setInt(1, userId);
			st.executeUpdate();
			connection.commit();
			return true;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DataBaseException("DATABASE ERROR!", e);
			}

		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DataBaseException("DATABASE ERROR!", e);
			}
		}
		return false;

	}

	@Override
	public int loginUser(User user) throws IllegalInputException, DataBaseException {
		try {
			PreparedStatement st = connection.prepareStatement(BY_USERNAME_AND_PASSWORD);
			st.setInt(1, user.getUserId());
			st.setString(2, user.getPassword());
			ResultSet resultSet = st.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("user_id");
			} else {
				throw new IllegalInputException("INVALID USERNAME OR PASSWORD");
			}

		} catch (SQLException e) {
			throw new DataBaseException("DATABASE ERROR!", e);
		}
	}

}
