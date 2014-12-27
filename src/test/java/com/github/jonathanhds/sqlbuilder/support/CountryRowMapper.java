package com.github.jonathanhds.sqlbuilder.support;

import com.github.jonathanhds.sqlbuilder.select.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper extends RowMapper<Country> {

	@Override
	public Country convert(ResultSet resultSet, int rowNum) throws SQLException {
		String name = resultSet.getString("country_name");

		return new Country(name);
	}

}