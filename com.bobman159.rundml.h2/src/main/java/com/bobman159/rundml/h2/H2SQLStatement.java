package com.bobman159.rundml.h2;

import java.sql.Connection;

import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.h2.select.builder.H2SelectStatement;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;

/**
 * Factory class to create H2 DBMS SQL statement builders.
 *
 */
public class H2SQLStatement {

	private H2SQLStatement() {
		//make sonar lint happy
	}
	
	/**
	 * Create the builder for an H2 DBMS SELECT statement using an existing 
	 * <code>Connection</code> object
	 * @param conn a JDBC connection
	 * @param mapping a mapping definition for the statement
	 * @return H2 SELECT statement builder
	 */
	public static H2SelectStatement selectStatement(Connection conn, TableDefinition mapping) {
		return new H2SelectStatement(conn,mapping);
		
	}
	
	/**
	 * Create the builder for an H2 DBMS SELECT statement using an existing 
	 * <code>DefaultConnectionProvider</code> for a connection pool.
	 * @param provider a JDBC connection pool
	 * @param mapping
	 * @return H2 SELECT statement builder
	 */
	public static H2SelectStatement selectStatement(DefaultConnectionProvider provider,
													TableDefinition mapping) {
		return new H2SelectStatement(provider,mapping);
	}

}
