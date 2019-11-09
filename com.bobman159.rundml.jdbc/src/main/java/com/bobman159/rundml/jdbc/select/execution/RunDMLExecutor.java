package com.bobman159.rundml.jdbc.select.execution;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.bobman159.rundml.core.model.SQLStatementModel;

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
	 * @param tableRow the SQL SELECT result row class
	 * 
	 * @return a <code>List</code> of SELECT rows
	 */
	@SuppressWarnings("unchecked")
	public Future<List<Object>> executeSelect(Connection conn,
												 SQLStatementModel model,
												 Class<?> tableRow) {
		
		Future<List<Object>> futureTask;
		
		SelectCallable exec = new SelectCallable(conn,model,tableRow);
		futureTask = service.submit(exec);
		return futureTask;
	}
	
}
