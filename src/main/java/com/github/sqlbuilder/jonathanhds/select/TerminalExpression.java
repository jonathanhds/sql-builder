package com.github.sqlbuilder.jonathanhds.select;

import java.sql.SQLException;
import java.util.List;

interface TerminalExpression extends Expression {

	<E> List<E> list(RowMapper<E> rowMapper) throws SQLException;
	
	<E> E single(RowMapper<E> rowMapper) throws SQLException;

}