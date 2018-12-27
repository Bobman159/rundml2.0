package net.bobs.own.db.rundml.sql.builders;

/*	Extend this class for all SQL statement builders 
 * 
 *	I got this working with help from the following Stack Overflow 
 * 	https://stackoverflow.com/questions/21086417/builder-pattern-and-inheritance
*/
public abstract class AbstractSQLStatementFactory<SS extends AbstractSQLStatementFactory<SS>> {

	//TODO: Consider using a FIFO queue instead of String Builder
	//The Builder allows methods in this class to be called in any order so I can't 
	//just build the sql statement in the order the methods are called....
	private StringBuilder builder;
	
	@SuppressWarnings("unchecked")
	public SS select() {
		builder.append("select");
		return (SS) this;
	}
	
	@SuppressWarnings("unchecked")
	public SS insert() {
		builder.append("insert");
		return (SS) this;
	}
	
	@SuppressWarnings("unchecked")
	public SS from(String schema, String table) {
		builder.append("\n").append(schema).append(".").append(table);
		return (SS) this;
	}
	
	@SuppressWarnings("unchecked")
	public SS from(String table) {
		builder.append("\n").append(table);
		return (SS) this;
	}
}	