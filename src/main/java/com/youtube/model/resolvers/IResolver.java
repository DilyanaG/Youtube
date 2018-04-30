package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IResolver<T> {

	T resolve(final ResultSet rs) throws SQLException;

	default List<String> getColumnNames(final ResultSet rs)throws SQLException {
		final int columnCount = rs.getMetaData().getColumnCount();
		final List<String> selectedColumns = new ArrayList<>(columnCount);
		
		// Makes a List with the parameters from the ResultSet
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			final String name = rs.getMetaData().getColumnName(columnIndex);
			selectedColumns.add(name);
		}
		
		return selectedColumns;
	}
}
