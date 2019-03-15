package com.bobman159.rundml.compat.builder.select;

import com.bobman159.rundml.compat.builder.select.SelectClauses.SelectClause;
import com.bobman159.rundml.core.util.RunDMLUtils;

//It's my understanding that "<T extends AnsiSelect>"  tells Java
//to allow anything that subtypes AnsiSelect as a Generic Type
public abstract class SelectListStep<T,S extends SelectListStep<T,S>> 
	extends SelectConditions<T,S>
{
	
	@SuppressWarnings("unchecked")
	public S distinct() {
		model.addClause(SelectClause.DISTINCT);
		System.out.println("AnsiSelect distinct");
		return (S) this;
	}
	
	@SuppressWarnings("unchecked")
	public S all() {
		model.addClause(SelectClause.ALL);
		System.out.println("AnsiSelect all");
		return (S) this;
	}
	
	@SuppressWarnings("unchecked")
	public S selectExpression() {
		System.out.println("SelectList selectExpression");
		return (S) this;
	}
	
	@SuppressWarnings("unchecked")
	public S from(String schema, String tbName) {
		model.addClause(SelectClause.FROM,RunDMLUtils.qualifiedTbName(schema, tbName));
		System.out.println("CompatSelect from");
		return (S) this;
	}

}
