package com.bobman159.rundml.compat.model;

class SQLClause implements ISQLClause {
	
	private ISQLEnum 	enumClause;
//	private ArrayList<Object>	args;
	private Object arg;

	public SQLClause(ISQLEnum enumeration) {
		enumClause = enumeration;
//		args = new ArrayList<Object>();
		arg = null;
	}
	
	public SQLClause(ISQLEnum enumeration,Object arg) {
		enumClause = enumeration;
//		args = new ArrayList<Object>();
		this.arg = arg;
	}
	
	@Override
	public ISQLEnum getEnum() {
		return enumClause;
	}
	
//	@Override
//	public ArrayList<Object> getArgs() {
//		return args;
//	}
	
	@Override
	public Object getArg() {
		return arg;
	}
}
