package com.github.sqlbuilder.delete;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.sqlbuilder.delete.DeleteQuery;


public class DeleteQueryTest {

	@Test
	public void shouldCreateQuery() throws Exception {
		// Given
		DeleteQuery query = new DeleteQuery("persons p");

		// When
		String actual = query.toString();

		// Then
		String expected = "DELETE FROM persons p";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingWhere() throws Exception {
		// Given
		DeleteQuery query = new DeleteQuery("account a").addWhere("a.id > 666")
														.addWhere("a.creation_date > '2013-01-01'");

		// When
		String actual = query.toString();

		// Then
		String expected = "DELETE FROM account a WHERE a.id > 666 AND a.creation_date > '2013-01-01'";
		assertEquals(expected, actual);
	}

}
