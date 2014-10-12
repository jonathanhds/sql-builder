package com.github.sqlbuilder.jonathanhds.update;

import com.github.sqlbuilder.jonathanhds.dml.Update;
import static org.junit.Assert.*;

import org.junit.Test;

import com.github.sqlbuilder.jonathanhds.IllegalQueryException;
import com.github.sqlbuilder.jonathanhds.dml.Database;
import com.github.sqlbuilder.jonathanhds.dml.QueryBuilder;
import com.github.sqlbuilder.jonathanhds.support.MemoryDatabase;
import java.sql.Connection;
import org.junit.After;
import org.junit.Before;

public class UpdateQueryTest {
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

	@Test(expected = IllegalQueryException.class)
	public void shouldThrowErroWhenSetIsNotPassed() throws Exception {
		// Given
        Update query = new QueryBuilder(Database.HSQLDB, connection).update("person p");

		// When
		query.toSqlString();
	}

	@Test
	public void shouldCreateQueryForTableUsingSets() throws Exception {
		// Given
        Update query = new QueryBuilder(Database.HSQLDB, connection)
                .update("person p")
                .set("p.name", "foo");

		// When
		String actual = query.toSqlString();

		// Then
        String expected = new StringBuilder("UPDATE person p SET")
                .append(newLine)
                .append("p.name = 'foo'")
                .toString();
		assertEquals(expected, actual);
	}

	@Test
	public void shouldCreateQueryUsingWhere() throws Exception {
		// Given
        Update query = new QueryBuilder(Database.HSQLDB, connection)
                .update("employee e")
                .set("e.salary", "50000")
                .where("e.age > 40")
                .where("e.genre = 'female'")
                ;

		// When
		String actual = query.toSqlString();

		// Then
        String expected = new StringBuilder("UPDATE employee e SET")
                .append(newLine)
                .append("e.salary = '50000'")
                .append(newLine)
                .append("WHERE ").append("e.age > 40")
                .append(newLine)
                .append("AND e.genre = 'female'")
                .toString();
        
		assertEquals(expected, actual);
	}

}