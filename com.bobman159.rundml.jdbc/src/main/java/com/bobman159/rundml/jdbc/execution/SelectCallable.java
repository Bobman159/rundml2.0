package com.bobman159.rundml.jdbc.execution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.model.SQLStatementSerializer;
import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.jdbc.select.ITableRow;

/**
 * Executes an SQL SELECT statement using JDBC and the callable interface which
 * allows for returning a re.  Because a Runnable is used,
 * the SELECT is executed in a separate thread. 
 *
 */
/*
 * ASSUMES:
 * 	*	No special handling for large SELECT results is needed.  Hundreds of
 * 		thousands or millions of result rows will take a VERY long time to 
 * 		execute.
 */

class SelectCallable implements Callable<List<ITableRow>> {
	
	private Connection conn;
	private SQLStatementModel model;
	private List<ITableRow> results;
	private ResultSetMapper mapper;

	private static Logger logger = LogManager.getLogger(SelectCallable.class.getName());
	
	/**
	 * Define the Select Runnable to execute the SELECT statement
	 * @param provider connection pool provider for JDBC connections
	 * @param model model for an SQL SELECT statement to be executed
	 * @param tbDef table defintion for mapping results
	 */
	public SelectCallable(Connection conn,SQLStatementModel model,
						  TableDefinition tbDef) {
		this.conn = conn;
		this.model = model;
		mapper = new ResultSetMapper(tbDef);
		results = new ArrayList<>();
	}

	/**
	 * @see java.util.concurrent.Callable#call() 
	 */
	@Override
	public List<ITableRow> call() throws SQLException {
		logger.info("Execute SELECT statement");
		

		String stmtText = SQLStatementSerializer.serializeSelect(model);
		try (PreparedStatement ps = conn.prepareStatement(stmtText)) {
			//TODO: Figure out how to bind parameters....
			try (ResultSet rs = ps.executeQuery()) {
				mapQueryResults(rs);
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage(), ex);
		}	

		return results;
	}

	private void mapQueryResults(ResultSet rs) {

		try {
		while (rs.next()) {
			Object mappedRow = mapper.mapResultRow(rs);
			results.add((ITableRow) mappedRow);
		}
		} catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
		}

	}
}
