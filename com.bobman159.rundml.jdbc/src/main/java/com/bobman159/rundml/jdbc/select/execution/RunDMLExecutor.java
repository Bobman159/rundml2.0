package com.bobman159.rundml.jdbc.select.execution;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exceptions.RunDMLExceptionListeners;
import com.bobman159.rundml.core.model.ISelectStatement;



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
	private ExecutorService service = null;
	private static RunDMLExecutor self;
	private static Logger logger = LogManager.getLogger(RunDMLExecutor.class.getName());

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
	public List<Object> executeSelect(Connection conn, ISelectStatement model,
												 Class<?> tableRow) {
		
		Future<List<Object>> futureTask;
		List<Object> results = new ArrayList<>();
		SelectCallable exec = new SelectCallable(conn,model,tableRow);
		if (service == null || service.isShutdown()) {
			initService();			
		}
		
		futureTask = service.submit(exec);		
		
		/*
		 * For now this seems to work well in that it executes the SELECT
		 * in another thread. There may be issues with performance using this mechanism
		 * but since I don't anticipate retrieving large numbers of rows, I will
		 * leave it this way for now.  If performance issues DO crop up then
		 * this method of invocation may need to be revised.
		 */
		
		try {
			results = futureTask.get();
		} catch (CancellationException | ExecutionException | InterruptedException ex) {
			RunDMLExceptionListeners.getInstance().notifyListeners(ex);
			shutdown();
		}
		
		return results;

	}

	/**
	 * @see java.util.concurrent.ExecutorService#shutdown()
	 */
	public void shutdown() {
		service.shutdown();
		try {
			service.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
		}
		service = null;
	}
	
	private RunDMLExecutor() {
		initService();
	}
	
	private void initService() {
		service = Executors.newSingleThreadExecutor();
	}
	
}
