package com.youtube.model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.model.resolvers.IResolver;

@Component
public class DBManager {

	private static final String DB_IP = "127.0.0.1";
	private static final String DB_PORT = "3306";

	private final String DB_NAME;
	private final String DB_USERNAME;
	private final String DB_PASSWORD;
	private final String URL;

	@Autowired
	public DBManager() {
		File file = new File("DB_connection.properties");
		try (BufferedReader in = new BufferedReader(new FileReader(file));) {
			final Properties properties = new Properties();
			properties.load(in);
			DB_NAME = properties.getProperty("DB_NAME");
			DB_USERNAME = properties.getProperty("DB_USERNAME");
			DB_PASSWORD = properties.getProperty("DB_PASSWORD");
			URL = "jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false";

			// load driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Sorry,connection failed! Maybe wrong credentials!");
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() throws DataBaseException {
		try {
			return DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {
			throw new DataBaseException(e);
		}
	}

	// Begins a transaction
	public void startTransaction(Connection connection) throws SQLException {
		connection.prepareStatement("START TRANSACTION;").execute();
	}

	// If the transaction fails it makes a rollback and throws an exception
	public void rollback(Connection connection, SQLException sqlE) throws DataBaseException {
		try {
			connection.prepareStatement("ROLLBACK;").execute();
			connection.close();
		} catch (SQLException e) {
			throw new DataBaseException(e);
		}
		throw new DataBaseException(sqlE);
	}

	// If the transaction succeeds it makes a commit
	public void commit(Connection connection) throws SQLException {
		connection.prepareStatement("COMMIT;").execute();
		connection.close();
	}

	// Executes the given select
	// Receives a connection, a select SQL statement, a resolver for the object
	// and the parameters in the select
	// Returns a list of the objects the resolver gave
	public <T> List<T> executeSelect(Connection connection, String sql, IResolver<T> resolver, Object... args)
			throws SQLException {
		PreparedStatement prst = connection.prepareStatement(sql);
		setParameters(prst, args);
		final ResultSet rs = prst.executeQuery();
		final List<T> result = new ArrayList<>();

		while (rs.next()) {
			final T object = resolver.resolve(rs);
			result.add(object);
		}

		return result;
	}

	// Similar to executeSelect
	// Returns a single object that the resolver gave
	public <T> T executeSingleSelect(Connection connection, String sql, IResolver<T> resolver, Object... args)
			throws SQLException {
		PreparedStatement prst = connection.prepareStatement(sql);
		setParameters(prst, args);
		final ResultSet rs = prst.executeQuery();

		if (!rs.next()) {
			throw new SQLException("Found nothing");
		} else {
			final T object = resolver.resolve(rs);
			if (rs.next()) {
				throw new SQLException("Found more than one result");
			}
			return object;
		}
	}

	// Executes INSERT, UPDATE, DELETE (the whole DML)
	// Receives a connection, a select SQL statement, the parameters in the SQL
	// Returns the number of changed rows in the DB
	public int execute(Connection connection, String sql, Object... args) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(sql);
		setParameters(prst, args);
		return prst.executeUpdate();
	}

	private PreparedStatement setParameters(PreparedStatement prst, Object[] args) throws SQLException {
		for (int parameterIndex = 0; parameterIndex < args.length; parameterIndex++) {
			prst.setObject(parameterIndex, args[parameterIndex]);
		}
		return prst;
	}

}