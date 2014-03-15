package com.github.sqlbuilder.jonathanhds.insert;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.sqlbuilder.jonathanhds.IllegalQueryException;


public class InsertQueryTest {

	@Test
	public void shouldCreateQueryPassingColumnsAndValues() throws Exception {
		// Given
		InsertQuery query = new InsertQuery("persons").columns("id", "name", "age")
													  .values(1, "foo", 30);

		// When
		String actual = query.toString();

		// Then
		String expected = "INSERT INTO persons (id, name, age) VALUES (1, 'foo', 30)";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryPassingColumnsAndManyValues() throws Exception {
		// Given
		InsertQuery query = new InsertQuery("persons").columns("id", "name", "age")
													  .values(1, "foo", 30)
													  .values(2, "bar", 23)
													  .values(3, "hello", 54)
													  .values(4, "world", 19);

		// When
		String actual = query.toString();

		// Then
		String expected = "INSERT INTO persons (id, name, age) VALUES (1, 'foo', 30), (2, 'bar', 23), (3, 'hello', 54), (4, 'world', 19)";
		assertEquals(expected, actual);
	}

	@Test(expected = IllegalQueryException.class)
	public void shouldThrowErrorIfNoColumnsIsInformed() throws Exception {
		// Given
		InsertQuery query = new InsertQuery("persons").columns("id", "name", "age");

		// When
		query.toString();
	}

	@Test(expected = IllegalQueryException.class)
	public void shouldThrowErrorIfNoValuesIfPassed() throws Exception {
		// Given
		InsertQuery query = new InsertQuery("persons").values(1, "foo", 30);

		// When
		query.toString();
	}

	@Test(expected = IllegalQueryException.class)
	public void shouldThrowErrorIfColumnsSizeIsDifferentOfValuesSize() throws Exception {
		// Given
		InsertQuery query = new InsertQuery("persons").columns("id", "name", "age")
													  .values(1, "bar", 20)
													  .values(2, "foo", 30, "bar")
													  .values(3, "hello", 45);

		// When
		query.toString();
	}


}
