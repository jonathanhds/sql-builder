package com.github.jonathanhds.sqlbuilder.delete;

import com.github.jonathanhds.sqlbuilder.Database;
import com.github.jonathanhds.sqlbuilder.QueryBuilder;
import com.github.jonathanhds.sqlbuilder.support.MemoryDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;


public class DeleteQueryTest {

    private static final String newLine = System.getProperty("line.separator");

	private Connection connection;

	@Before
	public void startConnection() throws Exception {
		connection = new MemoryDatabase().getConnection();
		connection.createStatement().executeQuery("SET DATABASE SQL SYNTAX ORA FALSE");
	}

	@After
	public void closeConnection() throws Exception {
		connection.createStatement().execute("SHUTDOWN");
		
		if (!connection.isClosed()) {
			connection.close();
		}
	}

	@Test
	public void shouldCreateQuery() throws Exception {
		// Given
        Delete query = new QueryBuilder(Database.HSQLDB, connection)
                .delete("person p");

		// When
		String actual = query.toSqlString();

		// Then
		String expected = "DELETE FROM person p";
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingWhere() throws Exception {
		// Given
        Delete query = new QueryBuilder(Database.HSQLDB, connection)
                .delete("account a")
                .where("a.id > 666")
                .and("a.creation_date > '2013-01-01'")
                ;

		// When
		String actual = query.toSqlString();

		// Then
        String expected = new StringBuilder("DELETE FROM account a")
                .append(newLine)
                .append("WHERE a.id > 666")
                .append(newLine)
                .append("AND a.creation_date > '2013-01-01'")
                .toString();
		assertEquals(expected, actual);
	}
}