package com.github.sqlbuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SelectQueryTest {

	@Test
	public void shouldReturnEmptyIfNoArgumentIsPassed() throws Exception {
		// Given
		SelectQuery query = new SelectQuery();

		// When
		String actual = query.toString();

		// Then
		String expected = "";
		assertEquals(actual, expected);
	}

	@Test
	public void shouldCreateQueryUsingFrom() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addFrom("users");

		// When
		String actual = query.toString();

		// Then
		String expected = "SELECT * FROM users";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingColumnsAndFrom() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addColumn("name")
											 .addColumn("password")
											 .addFrom("users");

		// When
		String actual = query.toString();

		// Then
		String expected = "SELECT name, password FROM users";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingColumnsAndFroms() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addColumns("name", "product_id",  "password")
											 .addFrom("users")
											 .addFrom("products");

		// When
		String actual = query.toString();

		// Then
		String expected = "SELECT name, product_id, password FROM users, products";
		assertEquals(expected, actual);
	}

	@Test(expected = IllegalQueryException.class)
	public void shouldThrowErrorIfNoFromIsPassed() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addColumns("name");

		// Then
		query.toString();

	}

}