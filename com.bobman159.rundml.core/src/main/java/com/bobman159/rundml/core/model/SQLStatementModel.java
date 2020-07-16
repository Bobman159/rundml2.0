package com.bobman159.rundml.core.model;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

import com.bobman159.rundml.core.expressions.ExpressionList;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.ISQLClause;
import com.bobman159.rundml.core.sql.impl.SQLClauses.SQLClause;
import com.bobman159.rundml.core.sql.types.ISQLType;

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
	
	private LinkedList<SQLModelNode> model;
	
	/**
	 * Define a new model for an SQL statement.
	 */
	public SQLStatementModel() {
		model = new LinkedList<>();
	}

	/**
	 * Add an SQL clause for the current model 
	 * @param clause the clause being added
	 */
	public void addClause(ISQLClause clause) {
		addNode(clause);
	}
	
	/**
	 * Add an SQL clause and it's value for the current model.
	 * This method may be called multiple times to add values 
	 * for the same SQL clause.
	 * 
	 * @param clause the clause being added
	 * @param value the value for the current clause being added
	 */
	public void addClause(ISQLClause clause,Object value) {
		addNode(clause,value);
	}
	
	/**
	 * Add an SQL expression represented by an <code>ISQLType</code> object
	 * to a list of other expressions that are defined in the model.  Only 
	 * <code>SQLClause</code> enumerations defined for SQL expressions that are 
	 * comma separated list such as SELECT_EXPR or GROUP_BY should use this method. 
	 * 
	 * @param clause <code>SQLClause</code> enumeration
	 * @param expr an SQL expression 
	 */
	public void addExpressionList(ISQLClause clause, ISQLType expr) {
		
		ExpressionList exprList;
		if (!containsSQLClause(clause)) { 
			exprList = new ExpressionList();
			exprList.addExpression(expr);
			addNode(clause,exprList);
		} else {
			SQLModelNode node = getSQLClause(SQLClause.SELECTEXPR);
			if (node != null) {
				Object objNode = node.getArg();
				if (objNode instanceof ExpressionList) {
					ExpressionList list = (ExpressionList) objNode;
					list.addExpression(expr);
					int index = model.indexOf(node);
					model.set(index, node);
				}
			}
		}
	}
	
	/**
	 * Add an SQL expression represented by a list  of <code>ISQLType</code> 
	 * objects to the model.  Only <code>SQLClause</code> enumerations defined 
	 * for SQL expressions that are comma separated list such as SELECT_EXPR 
	 * or GROUP_BY should use this method. 
	 * 
	 * @param clause <code>SQLClause</code> enumeration
	 * @param expressions SQL expression list 
	 */
	public void addExpressionList(ISQLClause clause,ISQLType... expressions) {

		ExpressionList exprsList = new ExpressionList();
		exprsList.addExpressions(expressions);
		addNode(clause,exprsList);
	}
	
	/**
	 * Return a non-modifiable <code>Stream</code> of the SQL clauses 
	 * for the current model.
	 * 
	 * @return the <code>Stream</code> of <code>SQLClause</code> objects
	 */
	public Stream<SQLModelNode> sqlClauses() {
		return model.stream();
	}
	
	/**
	 * Check the model for an occurrence of the specified clause.
	 * @param srchClause the clause to search the model for
	 * @return true if an entry was found, false otherwise.
	 */
	private boolean containsSQLClause(ISQLClause srchClause) {
		
		boolean clauseFound = false;

		clauseFound = model.stream()
			 .anyMatch(modelNode -> modelNode.getEnum().equals(srchClause));
		
		return clauseFound;
	}
	
	/* Return the SQLClause found first in the model 
	 * If no entry is found, then null is returned.
	 * https://stackoverflow.com/questions/22940416/fetch-first-element-which-matches-criteria
	 */
	private SQLModelNode getSQLClause(ISQLClause sqlClause) {
				
		Optional<SQLModelNode> srchNode = model.stream()
					.filter(modelNode -> modelNode.getEnum().equals(sqlClause))
					.findFirst();
		
		if (srchNode.isPresent()) {
			return srchNode.get();
		} else {
			return null;
		}
		
	}
	
	/* Adds a SQL clause to the model */
	private void addNode(ISQLClause clause) {
		SQLModelNode node = new SQLModelNode(clause);
		model.add(node);
	}
	
	/* Adds a SQL clause and it's arguments to the model */
	private void addNode(ISQLClause clause,Object arg) {
		SQLModelNode node = new SQLModelNode(clause,arg);
		model.add(node);
	}

	public void addExpressionList(SQLClause orderby, IOrderByEntry[] orderByExprs) {
		// TODO Auto-generated method stub
		
	}
	
}
