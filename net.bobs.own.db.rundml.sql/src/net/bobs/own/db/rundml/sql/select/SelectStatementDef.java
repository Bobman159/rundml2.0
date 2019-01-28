package net.bobs.own.db.rundml.sql.select;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Executes an SQL Select statement including binding parameter types in an SQL.
 * 
 */

public class SelectStatementDef {
	
	private Logger logger = LogManager.getLogger(SelectStatementDef.class);
	private Connection conn;
	private String sqlStmt;    
	private List<Object> sqlParms;
	
	private SelectStatementDef(Connection conn, String sqlStmt, List<Object> sqlParms) {
		
		this.conn = conn;
		this.sqlStmt = sqlStmt;		
		this.sqlParms = sqlParms;
	}

	public List<String> fetch() {
		
		logger.debug(sqlStmt);
		return null;
	}
	
}
