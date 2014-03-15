package com.github.sqlbuilder.jonathanhds.support;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.sqlbuilder.jonathanhds.select.RowMapper;

public class CountryRowMapper extends RowMapper<Country> {

	@Override
	public Country convert(ResultSet resultSet, int rowNum) throws SQLException {
		String name = resultSet.getString("country_name");

		return new Country(name);
	}

}