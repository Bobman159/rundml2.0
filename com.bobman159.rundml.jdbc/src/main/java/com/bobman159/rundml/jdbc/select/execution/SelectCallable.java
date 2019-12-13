package com.bobman159.rundml.jdbc.select.execution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.exceptions.RunDMLExceptionListeners;
import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.model.SQLStatementSerializer;

/**
 * Executes an SQL SELECT statement using JDBC and the callable interface which
 * allows for returning a re.  Because a Runnable is used,
 * the SELECT is executed in a separate thread. 
 *
 */
/*
 * ASSUMES:
 * 	*	No special handling for large SELECT results is needed/done.  Hundreds of
 * 		thousands or millions of result rows may take a VERY long time to 
 * 		execute.
 */

class SelectCallable implements Callable<List<Object>> {
	
	private Connection conn;
	private SQLStatementModel model;
	private List<Object> results;
	private ResultSetMapper mapper;
	
	private static Logger logger = LogManager.getLogger(SelectCallable.class.getName());
	
	/**
	 * Define the Select Runnable to execute the SELECT statement
	 * @param provider connection pool provider for JDBC connections
	 * @param model model for an SQL SELECT statement to be executed
	 */
	public SelectCallable(Connection conn,SQLStatementModel model,Class<?> rowClass) {
		this.conn = conn;
		this.model = model;
		mapper = new ResultSetMapper(rowClass);
		results = new ArrayList<>();
	}

	/**
	 * @see java.util.concurrent.Callable#call() 
	 */
	@Override
	public List<Object> call() throws SQLException,RunDMLException {
		
		logger.info("Execute SELECT statement");
		String stmtText = SQLStatementSerializer.serializeSelect(model);
		
		try (PreparedStatement ps = conn.prepareStatement(stmtText)) {
			//TODO: Figure out how to bind parameters....
			createQueryResults(ps);
		} catch (RunDMLException rdex) {
			RunDMLExceptionListeners.getInstance().notifyListeners(rdex);			
			//At this point, I know createQueryResults got an exception, so throw
			//the RunDMLException so that the Executor knows the thread failed.
			throw rdex;		
		} finally {
			conn.close();
		}

		return results;
	}

	/*
	 * Executes the SQL query and maps the results from the query into the table row class
	 */
	private void createQueryResults(PreparedStatement ps) throws RunDMLException {

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Object mappedRow = mapper.mapResultRow(rs);
				results.add((Object) mappedRow);
			}
		} catch (SQLException sqlex) {			
			RunDMLException.createRunDMLException(sqlex, RunDMLException.SQL_ERROR, null);
		} finally {
			try {
				conn.close();
			} catch (SQLException sqlex) {
				RunDMLException.createRunDMLException(sqlex, RunDMLException.SQL_ERROR, null);
			}
		}

	}

}
