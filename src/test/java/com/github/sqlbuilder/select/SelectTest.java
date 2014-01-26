package com.github.sqlbuilder.select;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.sqlbuilder.support.Country;
import com.github.sqlbuilder.support.MemoryDatabase;
import com.github.sqlbuilder.support.Person;
import com.github.sqlbuilder.support.PersonRowMapper;

public class SelectTest {

	private Connection connection;

	@Before
	public void startConnection() throws Exception {
		connection = new MemoryDatabase().getConnection();
	}

	@After
	public void closeConnection() throws Exception {
		connection.createStatement().execute("SHUTDOWN");
		
		if (!connection.isClosed()) {
			connection.close();
		}
	}

	@Test
	public void selectAllFromTable() throws Exception {
		List<Person> persons = new QueryBuilder(connection).select()
														   .all()
														   .from()
														   .table("PERSON p")
														   .list(new PersonRowMapper());

		assertThat(persons, hasSize(2));
		assertThat(persons, containsInAnyOrder(
			new Person("Jonathan", toDate(1988, 11, 8), null),
			new Person("Steve Jobs", toDate(1955, 2, 24), null)
		));
	}

	@Test
	public void selectAllFromTableWhereConditions() throws Exception {
		List<Person> persons = new QueryBuilder(connection).select()
														   .all()
														   .from()
														   .table("PERSON p")
														   .where()
														   .and("p.name = 'Jonathan'")
														   .list(new PersonRowMapper());

		assertThat(persons, hasSize(1));
		assertThat(persons, containsInAnyOrder(
			new Person("Jonathan", toDate(1988, 11, 8), null)
		));
	}
	
	@Test
	public void selectAllFromTableWhereConditionWithParameter() throws Exception {
		List<Person> persons = new QueryBuilder(connection).select()
														   .all()
														   .from()
														   .table("PERSON p")
														   .where()
														   .and("p.name = ?", "Steve Jobs")
														   .and("p.birthday = ?", getNullValue())
														   .list(new PersonRowMapper());

		assertThat(persons, hasSize(1));
		assertThat(persons, containsInAnyOrder(
			new Person("Steve Jobs", toDate(1955, 2, 24), null)
		));
	}

	@Test
	public void selectAllFromTableWhereBetween() throws Exception {
		List<Person> persons = new QueryBuilder(connection).select()
														   .all()
														   .from()
														   .table("PERSON p")
														   .innerJoin("COUNTRY c ON c.id = p.country_id")
														   .where()
														   .andBetween("p.birthday", toDate(1988, 1, 1), getNullValue())
														   .list(new PersonRowMapper());

		assertThat(persons, hasSize(1));
		assertThat(persons, containsInAnyOrder(
			new Person("Jonathan", toDate(1988, 11, 8), brazil())
		));
	}
	
	@Test
	public void selectColumnsFromTableJoinAnotherTable() throws Exception {
		List<Person> persons = new QueryBuilder(connection).select()
														   .column("p.name")
														   .column("p.birthday")
														   .column("p.country_id")
														   .column("c.country_name")
														   .from()
														   .table("PERSON p")
														   .innerJoin("COUNTRY c ON c.id = p.country_id")
														   .list(new PersonRowMapper());
		
		assertThat(persons, hasSize(2));
		assertThat(persons, containsInAnyOrder(
			new Person("Jonathan", toDate(1988, 11, 8), brazil()),
			new Person("Steve Jobs", toDate(1955, 2, 24), usa())
		));
	}
	
	@Test
	public void selectAllFromTableOrderBy() throws Exception {
		List<Person> persons = new QueryBuilder(connection).select()
														   .all()
														   .from()
														   .table("PERSON p")
														   .orderBy()
														   .column("p.name", OrderByType.DESC)
														   .list(new PersonRowMapper());

		assertThat(persons, hasSize(2));
		assertThat(persons, contains(
			new Person("Steve Jobs", toDate(1955, 2, 24), null),
			new Person("Jonathan", toDate(1988, 11, 8), null)
		));
	}
	
	@Test
	public void selectAllFromTableSingleResult() throws Exception {
		Person person = new QueryBuilder(connection).select()
													.all()
													.from()
													.table("PERSON p")
													.where()
													.and("p.id = ?", 1)
													.single(new PersonRowMapper());

		assertThat(person, equalTo(new Person("Jonathan", toDate(1988, 11, 8), null)));
	}

	@Test(expected = SQLException.class)
	public void selectAllFromTableSingleResult_throwExceptionIfMoreThanOneResultIsReturned() throws Exception {
		new QueryBuilder(connection).select()
									.all()
									.from()
									.table("PERSON p")
									.single(new PersonRowMapper());
	}

	@Test
	public void selectAllFromTableGroupBy() throws Exception {
		List<Integer> counts = new QueryBuilder(connection).select()
														   .count("*")
														   .from()
														   .table("PERSON p")
														   .groupBy()
														   .column("p.birthday")
														   .list(new CountRowMapper());

		assertThat(counts, hasSize(2));
		assertThat(counts, contains(
			new Integer(1),
			new Integer(1)
		));
	}

	@Test
	public void selectAllFromTableWithPagination() throws Exception {
		List<Person> persons = new QueryBuilder(connection).select()
														   .all()
														   .from()
														   .table("PERSON p")
														   .orderBy()
														   .column("p.name")
														   .limit(0, 1)
														   .list(new PersonRowMapper());

		assertThat(persons, hasSize(1));
		assertThat(persons, containsInAnyOrder(
			new Person("Jonathan", toDate(1988, 11, 8), brazil())
		));
	}

	private Date toDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(DAY_OF_MONTH, day);
		calendar.set(MONTH, month - 1);
		calendar.set(YEAR, year);
		calendar.set(HOUR_OF_DAY, 0);
		calendar.set(MINUTE, 0);
		calendar.set(SECOND, 0);
		calendar.set(MILLISECOND, 0);
		return calendar.getTime();
	}

	private Country brazil() {
		return new Country("Brazil");
	}
	
	private Country usa() {
		return new Country("United States of America");
	}

	private Object getNullValue() {
		return null;
	}
}