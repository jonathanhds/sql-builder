package com.github.sqlbuilder.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.github.sqlbuilder.select.RowMapper;

public class PersonRowMapper extends RowMapper<Person> {

	@Override
	public Person convert(ResultSet resultSet, int rowNum) throws SQLException {
		String name = resultSet.getString("name");
		Date birthDay = resultSet.getDate("birthday");
		Country country = contains(resultSet, "country_name") ? new CountryRowMapper().convert(resultSet, rowNum) : null;

		return new Person(name, birthDay, country);
	}

}