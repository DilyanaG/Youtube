package com.youtube.controller.upload.service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.user.UserDAO;
import com.youtube.model.pojo.User;

public class LogInService {
	private static final int MIN_USERNAME_SIZE = 2;
	private static final int MAX_USERNAME_SIZE = 30;
	private static final String USERNAME_PATTERN = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";

	private static final int MIN_PASSWORD_SIZE = 6;
	private static final int MAX_PASSWORD_SIZE = 100;

	// @Autowired
	private static UserDAO userDAO = UserDAO.getInstance();

	public User login(String username, String password) throws IllegalInputException, DataBaseException {

		System.out.println("In LogInService.login()");

		checkForUsername(username);
		checkForPassword(password);

		System.out.println("Checks passed");
		
		System.out.println(username);
		System.out.println(password);
		
		int userId = userDAO.loginUser(new User(username, password, ""));
		System.out.println("userId = " + userId);
		User user = userDAO.getUserById(userId);
		return user;
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
