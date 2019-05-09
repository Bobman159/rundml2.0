package com.bobman159.rundml.jdbc.execution;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.jdbc.select.ITableRow;

/**
 * RunDML's execution service for executing SQL statements using JDBC.
 * Tasks executed in JDBC are executed using a <code>Callable</code> 
 * interface.  Methods in the executor return a <code>Future</code>
 * interface which allows the caller to wait until the task has completed.
 * 
 *
 */
public class RunDMLExecutor  {

	/*
	 * If multiple threads are ever needed, 
	 * Executors.newSingleThreadExecutor(ThreadFactory factory) 
	 * could be used.  For now this should work.
	 * 
	 */
	private ExecutorService service = Executors.newSingleThreadExecutor();
	private static RunDMLExecutor self;

	/**
	 * Obtain an instance of this executor.
	 * @return the executor instance
	 */
	public static RunDMLExecutor getInstance() {
		if (self == null) {
			self = new RunDMLExecutor();
		}
		return self;
	}
	
	/**
	 * Execute an SQL SELECT statement using JDBC.
	 * @param conn JDBC connection for the database to execute on
	 * @param model the SQL SELECT definition
	 * @param tbDef the table definition for mapping
	 * 
	 * @return a <code>List</code> of SELECT rows
	 */
	@SuppressWarnings("unchecked")
	public Future<List<ITableRow>> executeSelect(Connection conn,
												 SQLStatementModel model,
												 TableDefinition tbDef) {
		
		Future<List<ITableRow>> futureTask;
		
		SelectCallable exec = new SelectCallable(conn,model,tbDef);
		futureTask = service.submit(exec);
		return futureTask;
	}
	
}
