package com.github.sqlbuilder.jonathanhds.dml;

import java.sql.SQLException;
import java.util.List;

interface TerminalExpression extends Query, Expression {

	<E> List<E> list(RowMapper<E> rowMapper) throws SQLException;
	
	<E> E single(RowMapper<E> rowMapper) throws SQLException;
}