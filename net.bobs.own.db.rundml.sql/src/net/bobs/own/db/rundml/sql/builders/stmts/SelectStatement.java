package net.bobs.own.db.rundml.sql.builders.stmts;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.bobs.own.db.rundml.jdbc.pool.DefaultConnectionProvider;
import net.bobs.own.db.rundml.sql.builders.exprs.ExpressionList;
import net.bobs.own.db.rundml.sql.builders.predicates.Predicate;
import net.bobs.own.db.rundml.sql.builders.predicates.PredicatesList;
import net.bobs.own.db.rundml.sql.expression.types.IExpression;


/**
 * Builds and executes including binding parameter types in an SQL.  The class 
 * uses the step Builder Patter to enforce the correct syntax when building the
 * SELECT statement.  
 * 
 * <p>Example:</p>
 * <ul>
 * <li> SelectStatement.select(conn) <p>
 *                     .selectExpr
 * </ul>
 * 
 * @author Robert Anderson
 *
 */
/*
 * This class is based on an article describing the step builder pattern
 * 
 * http://rdafbn.blogspot.com/2012/07/step-builder-pattern_28.html.
 */
public class SelectStatement implements ISQLStatement
//										IPredicateOperations 
{
	
	private Logger logger = LogManager.getLogger(SelectStatement.class);
	private Connection conn;
//	private List<IExpression> selectExprs;
	private ExpressionList selectExprs;
	private String schema;
	private String tbName;
	private PredicatesList predicates;
	
	/**
	 * First Builder Step the Select.
	 * Next Step available: FromStep 
	 * 
	 */
   public interface SelectExprStep {
            SelectExprStep selectExpr(IExpression expr);
    		FromStep from(String schema, String tbName);
    }
    
    /**
     * Second Builder Step in the Select.
     * Next Step available: PredicateStep OR FetchStep
     *
     */
    public interface FromStep {
//    	IPredicate where(IExpression lhs, Op op,IExpression rhs);
    	FetchStep where(Predicate pred);
//    	public <T> PredicateStep where(T lhsExpr);
    	List<String> fetch();  
    }
    
    /**
     * Third builder step in the Select.
     * Next Step available: FetchStep.
     *
     */
    @SuppressWarnings("hiding")
//	public interface PredicateStep extends IFetchAndOrStep  {
	public interface FetchStep {
//    	PredicateStep and(Number lhs, Op op);
//    	PredicateStep and(IExpression lhs, Op op,IExpression rhs);
//    	PredicateStep or(IExpression lhs, Op op,IExpression rhs);
//    	public <T> PredicateStep and(T lhsExpr);
//    	public <T> PredicateStep or(T lhsExpr);
    	List<String> fetch();
    }
    
    /**
     * Last builder step in the Select.
     *
     */
//    public interface FetchStep {
//    	
//    	List<String> fetch();
//    	
//    }
    
    /**
     * Implement the step builder pattern interfaces to build an 
     * SQL Select statement.
     *
     */
    public static class SelectSteps implements SelectExprStep,
    										   FromStep
//    										   PredicateStep
//    										   IFetchWhereStep
//     										   IFetchAndOrStep    										   
//     										   FetchStep 
{
    	 
    	 private static DefaultConnectionProvider provider;
    	 private static Connection conn;
    	 private static ExpressionList selectExprs;
//    	 private static List<IExpression> selectExprs = new ArrayList<>();
    	 private static String schema;
    	 private static String tableName;
    	 private static PredicatesList  predicates = new PredicatesList();
    	
    	/**
    	 * Execute the Select statement on the defined connection and 
    	 * return the results.
    	 * 
    	 * @return - list of the Select results
    	 */
//		@Override
//		public List<String> fetch() {
//			
//			return new SelectStatement(conn,selectExprs,schema,tableName).fetch();
//			
//		}

		/**
		 * Add the specified expression to the list of expressions to be 
		 * selected by the statement.
		 * 
		 * 
		 */
		@Override
		public SelectExprStep selectExpr(IExpression expr) {

			selectExprs.addExpression(expr);
			return this;
			
		}
		

		/**
		 * Specify the table to select data from
		 * @param - schema - the schema for table name
		 * @param - tbName - the table name 
		 */
		@Override
		public FromStep from(String schema, String tbName) {
			SelectSteps.schema = schema;
			SelectSteps.tableName = tbName;
			return this;
		}


		@Override
		public FetchStep where(Predicate pred) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public List<String> fetch() {
			// TODO Auto-generated method stub
			return null;
		}
		
//		@Override
//		public List<String> fetch() {
//			return new ArrayList<>();
//		}

		/**
		 * Specify a AND condition predicate
		 * @param lhs expression to left of comparison operand
		 * @param op comparison operand 
		 * @param rhs expression to the right of comparison operand
		 */
//		@Override
//		public PredicateStep and(IExpression lhs, Op op, IExpression rhs) {
//			PredicateCondition predCond = PredicateHelper.andPredicateCondition(lhs, op, rhs);
//			predicates.addPredicate(predCond);
//			return this;
//		}

		/**
		 * Specify a OR condition predicate
		 * @param lhs expression to left of comparison operand
		 * @param op comparison operand 
		 * @param rhs expression to the right of comparison operand
		 */
//		@Override
//		public PredicateStep or(IExpression lhs, Op op, IExpression rhs) {
//			PredicateCondition predCond = PredicateHelper.orPredicateCondition(lhs, op, rhs);
//			predicates.addPredicate(predCond);
//			return this;
//		}

		/**
		 * Specify a WHERE condition predicate
		 * @param lhs expression to left of comparison operand
		 * @param op comparison operand 
		 * @param rhs expression to the right of comparison operand
		 */
//		@Override
////		public PredicateStep where(IExpression lhs, Op op, IExpression rhs) {
//    	public <T> PredicateStep where(T lhsExpr) {
//			
////			PredicateCondition predCond = PredicateHelper.wherePredicateCondition(lhs, op, rhs);
////			predicates.addPredicate(predCond);
//			return this;
//		}
		
//		@Override 
//		public <T> IPredicateAndOr and(T lhsExpr) {
//			return (IPredicateAndOr) this;
//		}
//		
//		@Override
//		public <T> PredicateStep or(T lhsExpr) {
//			return this;
//		}
//		
//		@Override
//		public List<String> fetch() {
//			return new ArrayList<>();
//		}
     	
    }
     
    /**
     * Create an SQL Select statement using the connection provider for the
     * SQL statement execution.
     *  
     * @param provider - the connection provider
     * @return - Next allowed step in the SQL statement create
     */
    public static SelectExprStep select(DefaultConnectionProvider provider) {
    	SelectSteps.provider = provider;
     	return new SelectSteps();
    }
    
    /**
     * Create an SQL Select statement using the connection provider for the
     * SQL statement execution.
     * 
     * @param conn - the SQL connection 
     * @return - next allowed step in SQL statement create
     */
    public static SelectExprStep select(Connection conn) {
    	SelectSteps.conn = conn;
    	return new SelectSteps();
    }

    /**
     * Create an SQL Select statement using the provided information
     * @param conn - the SQL connection
     * @param selectExprs - the select expressions
     * @param schema - the table schema name
     * @param tbName - the table name
     */
	private SelectStatement(Connection conn, ExpressionList selectExprs,
							String schema, String tbName) {
		
		this.conn = conn;
		this.selectExprs = selectExprs;
		this.selectExprs = new ExpressionList();
		this.schema = schema;
		this.tbName = tbName;
		
	}

	/**
	 * @see net.bobs.own.db.rundml.sql.expression.types.IExpression#serialize
	 */
	@Override
	public String serialize() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("select ");
		
//		int exprCount = selectExprs.size() - 1;
//		IExpression sqlEx;
//		for (int ix = 0; ix < exprCount; ix++) {
//			sqlEx = selectExprs.get(ix);
//			builder.append(sqlEx.serialize()).append(", ");
//			logger.debug(sqlEx.serialize());
//		}
		selectExprs.forEach(expr -> builder.append(expr.serialize()));
		
//		sqlEx = selectExprs.get((selectExprs.size() - 1));
//		builder.append(sqlEx.serialize());
		
		builder.append(" from ").append(schema).append(".").append(tbName);
		
		return builder.toString();
	}

	/**
	 * Execute the SQL Select statement and return the results.
	 * 
	 * @return - the results from the select.
	 */
	public List<String> fetch() {
		
		String sqlStmt = serialize();
		logger.debug(sqlStmt);
		return new ArrayList<>();
	}
	
}
