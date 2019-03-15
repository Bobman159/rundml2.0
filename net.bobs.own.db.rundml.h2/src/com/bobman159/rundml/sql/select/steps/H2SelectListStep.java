package com.bobman159.rundml.sql.select.steps;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.tabledef.TableDefinition;

	/**
	 * First Builder Step the Select.
	 * Next Step available: FromStep 
	 * 
	 */
public interface H2SelectListStep {

	H2SelectListStep selectStar();
	H2SelectListStep select();
	H2SelectFromStep select(TableDefinition tbDef);
	H2SelectListStep all();
	H2SelectListStep distinct();
	H2SelectListStep top(IExpression topExpr);
	H2SelectListStep selectExpression(IExpression expr);
    H2SelectFromStep from(String schema, String tbName);

}
