package com.github.sqlbuilder.jonathanhds.dml;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountRowMapper extends RowMapper<Integer> {

	@Override
	public Integer convert(ResultSet resultSet, int rowNum) throws SQLException {
		return resultSet.getInt(1);
	}

}
