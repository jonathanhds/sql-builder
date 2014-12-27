package com.github.jonathanhds.sqlbuilder;

import com.github.jonathanhds.sqlbuilder.select.RowMapper;

import java.sql.SQLException;
import java.util.List;

public interface TerminalExpression extends Query, Expression {

	<E> List<E> list(RowMapper<E> rowMapper) throws SQLException;
	
	<E> E single(RowMapper<E> rowMapper) throws SQLException;
}