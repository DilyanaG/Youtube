package com.youtube.controller.upload.service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.user.UserDAO;
import com.youtube.model.pojo.User;

@Service
public class SignUpService {

	private static final int MIN_USERNAME_SIZE = 2;
	private static final int MAX_USERNAME_SIZE = 30;
	private static final String USERNAME_PATTERN = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";

	private static final int MIN_PASSWORD_SIZE = 6;
	private static final int MAX_PASSWORD_SIZE = 100;

	private static final int MAX_EMAIL_SIZE = 45;
	private static final String EMAIL_PATTERN = "([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})?";

	@Autowired
	private UserDAO userDAO; 

	public boolean register(String username, String password, String email)
			throws IllegalInputException, DataBaseException {

		checkForUsername(username);
		checkForPassword(password);
		checkForEmail(email);

		Map<String, User> allUsers = userDAO.getAllUsers();

		if (allUsers.containsKey(username)) {
			throw new IllegalInputException("User with that username already exists");
		}

		for (User user : allUsers.values()) {
			if (user.getEmail().equals(email)) {
				throw new IllegalInputException("User with that email already exists");
			}
		}

		User user = new User(username, password, email);
		userDAO.addNewUserToDB(user);
		return true;

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

	private boolean checkForEmail(String email) throws IllegalInputException {
		if (email == null || email.length() > MAX_EMAIL_SIZE || !checkForPattern(email, EMAIL_PATTERN)) {
			throw new IllegalInputException("INCORRECT EMAIL!");
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
