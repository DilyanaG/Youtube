package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IResolver<T> {

	T resolve(final ResultSet rs) throws SQLException;

	default List<String> getColumnNames(final ResultSet rs)throws SQLException {
		System.out.println(1);
		final int columnCount = rs.getMetaData().getColumnCount();
		System.out.println(2);
		final List<String> selectedColumns = new ArrayList<>(columnCount);
		System.out.println(3);
		
		// Makes a List with the parameters from the ResultSet
		for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
			System.out.println(4);
			final String name = rs.getMetaData().getColumnName(columnIndex);
			System.out.println(5);
			selectedColumns.add(name);
		}
		
		return selectedColumns;
	}
}
