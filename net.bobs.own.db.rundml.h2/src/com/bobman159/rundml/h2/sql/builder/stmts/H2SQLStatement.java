package com.bobman159.rundml.h2.sql.builder.stmts;

import java.sql.Connection;

import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;

public class H2SQLStatement {
	
	public static H2SelectStatement selectStatement(Connection conn) {
		return new H2SelectStatement(conn);
		
	}
	
	public static H2SelectStatement selectStatement(DefaultConnectionProvider provider) {
		return new H2SelectStatement(provider);
	}

}
