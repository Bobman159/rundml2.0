package com.bobman159.rundml.sql.mysql;

import com.bobman159.rundml.core.model.ISelectStatement;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * MySQL database SELECT statement definition
 *
 */
public interface IMySQLSelectStatement extends ISelectStatement {

	/**
	 * Specifies the OFFSET clause for a LIMIT on a MYSQL select
	 * Refer to MySQL SELECT documentation full documentation
	 * @param offSet the numbered offset of the row to be returned
	 */
	public void addOffset(int offSet);
	
	/**
	 * Specifies the OFFSET clause for a LIMIT on a MYSQL select
	 * Refer to MySQL SELECT documentation full documentation
	 * @param offSet the offset of the row to be returned
	 */
	public void addOffset(ISQLType offSet);

	/**
	 * 
	 * @return the OFFSET clause value
	 */
	public ISQLType getOffset();
	
	/**
	 * Specifies the LIMIT clause on a MYSQL select
	 * Refer to MySQL SELECT documentation full documentation
	 * @param limit the numbered offset of the row to be returned
	 */
	public void addLimit(int limit);
	
	/**
	 * Specifies the LIMIT clause for a MySQL SELECT
	 * @param limitTerm maximum number of rows to return
	 */
	public void addLimit(ISQLType limitTerm);

	/**
	 *
	 * @return the LIMIT clause value for a MySQL SELECT 
	 */
	public ISQLType getLimit();
	
	/**
	 * Specifies a SQL_SMALL_RESULT clause for a MySQL SELECT.  
	 * Refer to MySQL documentation for more information
	 */
	public void setSmallResult();

	/**
	 * 
	 * @return true if SQL_SMALL_RESULT is specified,false otherwise
	 */
	public boolean hasSmallResult();
	
	/**
	 * Specifies a SQL_BIG_RESULT clause for a MySQL SELECT.  
	 * Refer to MySQL documentation for more information
	 */
	public void setBigResult();
	
	/**
	 * 
	 * @return true if SQL_BIG_RESULT is specified, false otherwise
	 */
	public boolean hasBigResult();

	/**
	 * Specifies a SQL_BUFFER_RESULT clause for a MySQL SELECT.  
	 * Refer to MySQL documentation for more information
	 */
	public void setBufferResult();	
	
	/**
	 * 
	 * @return true if SQL_BUFFER_RESULT was specified, false otherwise
	 */
	public boolean hasBufferResult();

}
