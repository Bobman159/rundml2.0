package com.bobman159.rundml.sql.factory;

import com.bobman159.rundml.common.model.ISQLStatement;
import com.bobman159.rundml.common.serialize.AbstractStatementSerializer;
import com.bobman159.rundml.common.serialize.SQLStatementSerializerService;
import com.bobman159.rundml.common.serialize.impl.GenericSelectSerializer;
import com.bobman159.rundml.core.model.ISelectStatement;
import com.bobman159.rundml.sql.builders.impl.GenericSelectBuilder;
import com.bobman159.rundml.sql.generic.GenericSelectStatement;
import com.bobman159.rundml.sql.h2.H2SelectBuilder;
import com.bobman159.rundml.sql.h2.H2SelectSerializer;
import com.bobman159.rundml.sql.h2.H2SelectStatement;
import com.bobman159.rundml.sql.mysql.MySQLSelectBuilder;
import com.bobman159.rundml.sql.mysql.MySQLSelectSerializer;
import com.bobman159.rundml.sql.mysql.MySQLSelectStatement;

/**
 * Factory class for the creation of SQL Statement builder classes.
 * 
 *
 */
public class SQLStatementBuilderFactory {
	
	private static SQLStatementBuilderFactory self = null;
	
	/**
	 *
	 * @return instance of this Factory
	 */
	public static SQLStatementBuilderFactory getInstance() {
		if (self == null) {
			self = new SQLStatementBuilderFactory();
		}
		return self;
	}
	
	private SQLStatementBuilderFactory() {
		//Make Sonar Lint happy
	}
	
	/**
	 * Create a basic SELECT statement builder for execution on different DBMS platforms.
	 * @see com.bobman159.rundml.sql.builders.impl.GenericSelectBuilder
	 * @return a basic SELECT statement builder and executor
	 */
	@SuppressWarnings("rawtypes")
	public static GenericSelectBuilder<GenericSelectBuilder> createBaseSelectStatement() {
		return new GenericSelectBuilder<>();
	}
	
	/**
	 * Create the builder for an MySQL DBMS SELECT statement.
	 * @return MySQL SELECT statement builder and executor
	 */
	public static MySQLSelectBuilder createMySQLSelectStatement() {
		return new MySQLSelectBuilder();
	}
	
	/**
	 * Create the builder for an H2 DBMS SELECT statement
	 * @return H2 SELECT statement builder
	 */
	public static H2SelectBuilder createH2SelectStatement() {
		return new H2SelectBuilder();
	}
	
	/**
	 * Create a SELECT statement h2Model
	 * @param Dialect the dbms h2Model to be created GENERIC, H2 etc
	 * @return the select h2Model for the dialect
	 */
	public ISelectStatement createSelectModel(String dialect) {
		
		ISelectStatement model = null;
		if (AbstractStatementSerializer.GENERIC_SELECT.equals(dialect)) {
			model = (ISelectStatement) new GenericSelectStatement();
			SQLStatementSerializerService.getInstance().registerSerializer((ISQLStatement) model, 
																			new GenericSelectSerializer());
		} else if (AbstractStatementSerializer.H2_SELECT.equals(dialect)) {
			model = new H2SelectStatement();
			SQLStatementSerializerService.getInstance().registerSerializer((ISQLStatement) model, 
																			new H2SelectSerializer());
		} else if (AbstractStatementSerializer.MYSQL_SELECT.equals(dialect)) {
			model = new MySQLSelectStatement();
			SQLStatementSerializerService.getInstance().registerSerializer((ISQLStatement) model, 
																			new MySQLSelectSerializer());
		}
		
		return model;
	}


}
