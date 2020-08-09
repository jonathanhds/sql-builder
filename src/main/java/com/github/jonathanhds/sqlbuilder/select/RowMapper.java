package com.github.jonathanhds.sqlbuilder.select;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class RowMapper<E> {

	public abstract E convert(ResultSet resultSet, int rowNum)
		throws SQLException;

	protected boolean contains(ResultSet resultSet, String columnName)
		throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			String metaDataColumnName = metaData.getColumnLabel(i);
			if (columnName.equalsIgnoreCase(metaDataColumnName)) {
				return true;
			}
		}
		return false;
	}
}
