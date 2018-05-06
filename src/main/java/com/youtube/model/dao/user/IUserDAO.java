package com.youtube.model.dao.user;

import java.util.Map;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.pojo.User;

public interface IUserDAO {

	User getUserById(int userId) throws DataBaseException, IllegalInputException;

	int addNewUserToDB(User user) throws DataBaseException, IllegalInputException;

	Map<String, User> getAllUsers() throws IllegalInputException, DataBaseException;

	User getUserByUserName(String username) throws IllegalInputException, DataBaseException;

	User getUserByEmail(String email) throws IllegalInputException, DataBaseException;

	boolean updatePassword(int user_id, String oldPassword, String newPassword) throws DataBaseException, IllegalInputException;

	boolean updateProfilePicture(String pictureURL, int userId) throws IllegalInputException, DataBaseException;

	boolean deleteProfilePicture(int userId) throws IllegalInputException, DataBaseException;

	boolean deleteUser(int userId) throws IllegalInputException, DataBaseException;
  
	int loginUser(User user)throws IllegalInputException, DataBaseException;

}