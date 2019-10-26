package com.bobman159.rundml.core.sql;

import java.sql.Connection;
import java.util.List;

import com.bobman159.rundml.jdbc.pool.IConnectionProvider;

/**
 * Define the basic implementation for an SQL SELECT statement to use when using RunDML.
 *
 */
public interface ISQLSelect extends ISQLStatement {
	
	public List<ITableRow> execute(Connection conn);
	public List<ITableRow> execute(IConnectionProvider provider);

}
