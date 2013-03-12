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
		SelectQuery query = new SelectQuery().addColumn("name", "product_id",  "password")
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
		SelectQuery query = new SelectQuery().addColumn("name");

		// Then
		query.toString();

	}

	@Test
	public void shouldCreateQueryUsingWhere() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addColumn("address")
											 .addFrom("customers")
											 .addWhere("age > 40");

		// Then
		String actual = query.toString();

		// Then
		String expected = "SELECT address FROM customers WHERE age > 40";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingANDOperator() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addColumn("name")
											 .addFrom("employess")
											 .addWhere("birthday = '01/01/1900'")
											 .and("sector = 'SALES'");

		// Then
		String actual = query.toString();

		// Then
		String expected = "SELECT name FROM employess WHERE birthday = '01/01/1900' AND sector = 'SALES'";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingOROperator() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addColumn("name")
											 .addFrom("cities")
											 .addWhere("state = 'TEXAS'")
											 .or("state = 'CALIFORNIA'");

		// Then
		String actual = query.toString();

		// Then
		String expected = "SELECT name FROM cities WHERE state = 'TEXAS' OR state = 'CALIFORNIA'";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingJoin() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addColumn("p.name, a.name")
											 .addFrom("persons p")
											 .join("address a", "a.id = p.address_id");

		// Then
		String actual = query.toString();

		// Then
		String expected = "SELECT p.name, a.name FROM persons p JOIN address a ON a.id = p.address_id";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingInnerJoin() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addColumn("p.name, a.name")
											 .addFrom("persons p")
											 .innerJoin("address a", "a.id = p.address_id");

		// Then
		String actual = query.toString();

		// Then
		String expected = "SELECT p.name, a.name FROM persons p INNER JOIN address a ON a.id = p.address_id";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingopoOutterJoin() throws Exception {
		// Given
		SelectQuery query = new SelectQuery().addColumn("p.name, a.name")
											 .addFrom("persons p")
											 .outterJoin("address a", "a.id = p.address_id");

		// Then
		String actual = query.toString();

		// Then
		String expected = "SELECT p.name, a.name FROM persons p OUTTER JOIN address a ON a.id = p.address_id";
		assertEquals(expected, actual);
	}

}