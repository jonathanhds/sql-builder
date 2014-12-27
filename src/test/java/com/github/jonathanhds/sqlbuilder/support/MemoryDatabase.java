package com.github.jonathanhds.sqlbuilder.support;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MemoryDatabase {

	static {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			throw new Error(e);
		}
	}

	public Connection getConnection() throws SQLException, FileNotFoundException, IOException {
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:querybuilder;sql.syntax_ora=true", "SA", "");

		ScriptRunner runner = new ScriptRunner(connection, true, true);
		runner.runScript(new FileReader("src/test/resources/schema.sql"));
		runner.runScript(new FileReader("src/test/resources/seed.sql"));

		return connection;
	}

}