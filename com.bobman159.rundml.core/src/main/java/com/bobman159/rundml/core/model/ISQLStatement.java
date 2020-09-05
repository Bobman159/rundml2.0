package com.bobman159.rundml.core.model;

public interface ISQLStatement {

	public enum Statement {SELECT,INSERT,UPDATE,DELETE}
	
	public Statement getStatement();
	public String toSQL();
}
