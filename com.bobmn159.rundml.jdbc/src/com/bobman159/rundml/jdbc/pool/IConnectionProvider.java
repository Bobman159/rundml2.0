package com.bobman159.rundml.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionProvider {

	public Connection getConnection() throws SQLException;
	public void releaseConnection(Connection conn) throws SQLException;
	public void closePool();
	public void setPoolConnectionTrace(boolean trace);

}
