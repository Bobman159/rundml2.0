package com.bobman159.rundml.mysql.builder.stmts;

public abstract class MySQLListStep<T,S extends MySQLListStep<T,S>> {
	
	
	public T smallResult() {
		System.out.println("MySQLListStep smallResult method");
		return (T) this;
	}
	
	public T bigResult() {
		System.out.println("MySQLListStep bigResult method");
		return (T) this;
	}
	
	public T bufferResult() {
		System.out.println("MySQLListStep bufferResult");
		return (T) this;
	}

}
