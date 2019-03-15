package com.bobman159.rundml.compat.model;

import java.util.stream.Stream;

import com.bobman159.rundml.compat.builder.select.SelectClauses;
import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.IPredicate;

public class SQLStatementSerializer {

	private SQLStatementModel model;
	private StringBuilder builder;
	
	
	public SQLStatementSerializer(SQLStatementModel model) {
		this.model = model;
		builder = new StringBuilder();
	}
	
	public String serialize() {
		
		ISQLEnum stmtType = model.getStatementType();
		if (stmtType instanceof SelectClauses) {
			serializeSelect();
		}
	
		return builder.toString();
	}
	
	private void serializeSelect() { 
	
		Stream<SQLClause> modelStream = model.sqlClauses();
//		Optional<String> clause = modelStream.peek((SelectClause.SELECT) -> {
//			System.out.println();
//		});
		
		
		modelStream.forEach(SQLClause  -> {
			ISQLEnum enumClause =  SQLClause.getEnum();
			builder.append(enumClause.serializeClause());
			
			Object arg = SQLClause.getArg();
			if (arg instanceof IExpression) {
				IExpression expr = (IExpression) arg;
				builder.append(expr.serialize());
			} else if (arg instanceof IPredicate) {
				IPredicate pred = (IPredicate) arg;
				builder.append(pred.serialize());
			} else {
				if (arg != null) {
					builder.append(" ");
					builder.append(arg.toString());
				}
			}
		});
	}
	
}
