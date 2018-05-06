package com.youtube.controller.upload.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.user.UserDAO;
import com.youtube.model.pojo.User;

@Service
public class LogInService {
	private static final int MIN_USERNAME_SIZE = 2;
	private static final int MAX_USERNAME_SIZE = 30;
	private static final String USERNAME_PATTERN = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";

	private static final int MIN_PASSWORD_SIZE = 6;
	private static final int MAX_PASSWORD_SIZE = 100;

	@Autowired
	private UserDAO userDAO;

	public User login(String username, String password) throws IllegalInputException, DataBaseException {

		checkForUsername(username);
		checkForPassword(password);

		int userId = userDAO.loginUser(new User(username, password, ""));
		User user = userDAO.getUserById(userId);
		return user;
	}
	
	public User changePassword(String username, String oldPassword, String newPassword) throws IllegalInputException, DataBaseException {

		checkForPassword(oldPassword);
		checkForPassword(newPassword);

		User user = userDAO.getUserByUserName(username);
		userDAO.updatePassword(user.getUserId(), oldPassword, newPassword);
		return userDAO.getUserById(user.getUserId());
	}

	private boolean checkForUsername(String username) throws IllegalInputException {
		if (username == null || username.length() < MIN_USERNAME_SIZE || username.length() > MAX_USERNAME_SIZE
				|| !checkForPattern(username, USERNAME_PATTERN)) {
			throw new IllegalInputException("INCORRECT USERNAME!");
		}
		return true;
	}

	private boolean checkForPassword(String password) throws IllegalInputException {
		if (password == null || password.length() < MIN_PASSWORD_SIZE || password.length() > MAX_PASSWORD_SIZE) {
			throw new IllegalInputException("INCORRECT PASSWORD!");
		}
		return true;
	}

	private boolean checkForPattern(String givenMatcher, String givenPattern) {
		try {
			Pattern pattern = Pattern.compile(givenPattern);
			Matcher matcher = pattern.matcher(givenMatcher);
			return matcher.matches();
		} catch (RuntimeException e) {
			return false;
		}
	}
}
