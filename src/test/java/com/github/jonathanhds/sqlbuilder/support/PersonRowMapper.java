package com.github.jonathanhds.sqlbuilder.support;

import com.github.jonathanhds.sqlbuilder.select.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PersonRowMapper extends RowMapper<Person> {

	@Override
	public Person convert(ResultSet resultSet, int rowNum) throws SQLException {
		String name = resultSet.getString("name");
		Date birthDay = resultSet.getDate("birthday");
		Country country = contains(resultSet, "country_name") ? new CountryRowMapper().convert(resultSet, rowNum) : null;

		return new Person(name, birthDay, country);
	}

}