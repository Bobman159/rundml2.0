package net.bobs.own.db.rundml.sql.builders.stmts;

/**
 * Defines the interface used to generate SQL for SQL statements
 * (Select, Insert, Update, Delete)
 * 
 * @author Robert Anderson
 *
 */
interface ISQLStatement {
	
	/**
	 * Generates a SQL statement to be executed.
	 * @return - the SQL statement string.
	 */
	public String serialize();

}
