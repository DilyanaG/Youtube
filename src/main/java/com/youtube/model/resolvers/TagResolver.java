package com.youtube.model.resolvers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.youtube.model.pojo.Tag;

public class TagResolver implements IResolver<Tag> {

	@Override
	public Tag resolve(final ResultSet rs) throws SQLException {
		final List<String> selectedColumns = getColumnNames(rs);

		// Checks if the parameter is in the ResultSet and takes it
		// If the parameter is not in the ResultSet it sets it a null value
		// The "t." is the alias for the "videos_has_tags" table in the DB

		final Integer tagId = selectedColumns.contains("t.tag_id") ? rs.getInt("t.tag_id") : null;
		final String content = selectedColumns.contains("t.content") ? rs.getString("t.content") : null;

		Tag tag = new Tag(tagId, content);
		return tag;
	}

}
