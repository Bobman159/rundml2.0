package com.bobman159.rundml.compat.model;

import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Model class for representing SQL clauses for SELECT, INSERT, UPDATE and
 * DELETE statements.  This class is used to store the information needed to 
 * build those statements.
 * 
 * The model adds SQL clauses are added in FIFO (First In First Out) order.
 * The first SQL clause added to the model should identify the type of SQL 
 * statement (SELECT, INSERT, UPDATE or DELETE).
 *
 */
public class SQLStatementModel {
	
	private LinkedList<SQLClause> model;
	
	/**
	 * Define a new model for an SQL statement.
	 */
	public SQLStatementModel() {
		model = new LinkedList<SQLClause>();
	}

	/**
	 * Add an SQL clause for the current model 
	 * @param clause - the clause being added
	 */
	public void addClause(ISQLEnum clause) {
		SQLClause newClause = new SQLClause(clause);
		model.add(newClause);
	}
	
	/**
	 * Add an SQL clause and it's value for the current model.
	 * This method may be called multiple times to add values 
	 * for the same SQL clause.
	 * 
	 * @param clause - the clause being added
	 * @param value - the value for the current clause being added
	 */
	public void addClause(ISQLEnum clause,Object value) {	
		SQLClause newClause = new SQLClause(clause,value);
		model.add(newClause);
	}
	
	/**
	 * Return a non-modifiable <code>Stream</code> of the SQL clauses 
	 * for the current model.
	 * 
	 * @return - the <code>Stream</code> of <code>SQLClause</code> objects
	 */
	public Stream<SQLClause> sqlClauses() {
		return model.stream();
	}
	
	/**
	 * Return the first entry in the list of SQL clauses for the model.
	 * The first entry identifies the type of SQL statement being built.
	 * (SELECT, INSERT, UPDATE or DELETE)
	 * 
	 * @return - <code>ISQLEnum</code> identifier
	 */
	public ISQLEnum getStatementType() {
		return (ISQLEnum) model.get(0);
	}
	
}
