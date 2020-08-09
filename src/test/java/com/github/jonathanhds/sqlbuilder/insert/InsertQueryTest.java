package com.github.jonathanhds.sqlbuilder.insert;

import static org.junit.Assert.assertEquals;

import com.github.jonathanhds.sqlbuilder.Database;
import com.github.jonathanhds.sqlbuilder.IllegalQueryException;
import com.github.jonathanhds.sqlbuilder.builder.QueryBuilder;
import com.github.jonathanhds.sqlbuilder.support.MemoryDatabase;
import java.sql.Connection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InsertQueryTest {
	private Connection connection;
	private static final String NEW_LINE = System.getProperty("line.separator");

	@Before
	public void startConnection() throws Exception {
		connection = new MemoryDatabase().getConnection();
		connection
			.createStatement()
			.executeQuery("SET DATABASE SQL SYNTAX ORA FALSE");
	}

	@After
	public void closeConnection() throws Exception {
		connection.createStatement().execute("SHUTDOWN");

		if (!connection.isClosed()) {
			connection.close();
		}
	}

	@Test
	public void shouldCreateQueryPassingColumnsAndValues() throws Exception {
		// Given
		Insert query = new QueryBuilder(Database.HSQLDB, connection)
			.insert("person")
			.columns("id", "name", "age")
			.values(1, "foo", 30);

		// When
		String actual = query.toString();

		// Then
		String expected = new StringBuilder("INSERT INTO person")
			.append(NEW_LINE)
			.append(" ( ")
			.append("id, name, age")
			.append(" )")
			.append(NEW_LINE)
			.append("VALUES (")
			.append("1, 'foo', 30")
			.append(")")
			.toString();

		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryPassingColumnsAndManyValues() throws Exception {
		// Given
		Insert query = new QueryBuilder(Database.HSQLDB, connection)
			.insert("person")
			.columns("id", "name", "age")
			.values(1, "foo", 30)
			.values(2, "bar", 23)
			.values(3, "hello", 54)
			.values(4, "world", 19);

		// When
		String actual = query.toString();

		// Then
		String expected = new StringBuilder("INSERT INTO person")
			.append(NEW_LINE)
			.append(" ( ")
			.append("id, name, age")
			.append(" )")
			.append(NEW_LINE)
			.append(
				"VALUES (1, 'foo', 30), (2, 'bar', 23), (3, 'hello', 54), (4, 'world', 19)"
			)
			.toString();

		assertEquals(expected, actual);
	}

	@Test(expected = IllegalQueryException.class)
	public void shouldThrowErrorIfNoColumnsIsInformed() throws Exception {
		// Given
		Insert query = new QueryBuilder(Database.HSQLDB, connection)
			.insert("person")
			.columns("id", "name", "age");

		// When
		query.toString();
	}

	@Test(expected = IllegalQueryException.class)
	public void shouldThrowErrorIfNoValuesIfPassed() throws Exception {
		// Given
		Insert query = new QueryBuilder(Database.HSQLDB, connection)
			.insert()
			.table("person")
			.values(1, "foo", 30);

		// When
		query.toString();
	}

	@Test(expected = IllegalQueryException.class)
	public void shouldThrowErrorIfColumnsSizeIsDifferentOfValuesSize()
		throws Exception {
		// Given
		Insert query = new QueryBuilder(Database.HSQLDB, connection)
			.insert("person")
			.columns("id", "name", "age")
			.values(1, "bar", 20)
			.values(2, "foo", 30, "bar")
			.values(3, "hello", 45);

		// When
		query.toString();
	}
}
