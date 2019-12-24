package com.bobman159.rundml.core.exprtypes.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.CaseExpression;
import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.exprtypes.Column;

class CaseExpressionTest {

	private static final String COL_DFLTINTEGER = "DfltInteger";
	private static final String COL_NOTNULLCHAR = "NotNullChar";
	private static final String STRING_EXPR = "0123456789";
	private final String STRING_EXPR_1 = "1123456789";
	private final String STRING_EXPR_A = "A123456789";
	private final String STRING_EXPR_B = "B123456789";
	private final String STRING_EXPR_ABC = "Abc";
	private final String STRING_EXPR_DEF = "Def";
	private final Column colDfltInteger = new Column(COL_DFLTINTEGER);
	private final Column colNotNullChar = new Column(COL_NOTNULLCHAR);
	private final int ONE_HUNDRED_THOUSAND = 100000;
	private final int TWO_HUNDRED_THOUSAND = 200000;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testWhen() {
				
		/* Column Expressions - same conditions are repeated for THEN and ELSE as well */
		/* Not sure if parameter markers are fully supported in CASE expressions, but for now at least test 
		 * the syntax is generated correctly.
		 */
		/* CASE ? WHEN ? THEN NOTNULLCHAR || 'ab' */ 
		String caseExprParm = new CaseExpression(Expression.parm(Types.CHAR, "Abc"))
				.when(Expression.parm(Types.CHAR, "Def"))
				.then(colNotNullChar)
				.end().serialize();
		Assertions.assertEquals(" case ?" + " when ? then " + COL_NOTNULLCHAR + " end ",caseExprParm);		

		/*	CASE NOTNULLCHAR || 'ab' WHEN NOTNULLCHAR || 'ab' THEN NOTNULLCHAR END */
		String caseExprConcat = new CaseExpression(colNotNullChar.concat("ab"))
				.when(colNotNullChar.concat(Expression.string("ab")))
				.then(colNotNullChar)
				.end().serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " || 'ab' when " + COL_NOTNULLCHAR + " || 'ab' " +
						        "then " + COL_NOTNULLCHAR + " end ",caseExprConcat);
		
		/* CASE NOTNULLCHAR WHEN NOTNULCHAR THEN 'A123456789' END */
		String caseExprChar = new CaseExpression(colNotNullChar).when(colNotNullChar)
																.then(Expression.string(STRING_EXPR_A))
																.end().serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " when " + COL_NOTNULLCHAR + 
						        " then '" + STRING_EXPR_A + "' end ",caseExprChar);
		
		/* CASE NOTNULLCHAR WHEN '0123456789' || NOTNULLCHAR THEN 'A123456789' END */
		String caseExprLiteral = new CaseExpression(colNotNullChar)
				.when(Expression.string(STRING_EXPR).concat(colNotNullChar))
				.then(Expression.string(STRING_EXPR_A)).end().serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + 
					" when '" + STRING_EXPR + "' || " + COL_NOTNULLCHAR +
					" then '" + STRING_EXPR_A + "' end ",caseExprLiteral);

		/* CASE NOTNULLCHAR || 10 WHEN '112345678910' THEN 'B123456789' END */
		CaseExpression caseExpr = new CaseExpression(colNotNullChar.concat(Expression.string("10")));		
		String caseExprLiteralB = caseExpr.when(Expression.string(STRING_EXPR_1))
										  .then(Expression.string(STRING_EXPR_B)).end()
										  .serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " || '10'"+ " when '" + STRING_EXPR_1 + 
		     "' then '" + STRING_EXPR_B + "' end ",caseExprLiteralB);

		/* CASE DFLTINTEGER WHEN DFLTINTEGER + 100000 THEN 200000 END */
		String caseExprNumb = new CaseExpression(colDfltInteger)
				.when(colDfltInteger.add(ONE_HUNDRED_THOUSAND))
				.then(Expression.number(TWO_HUNDRED_THOUSAND))
				.end().serialize();
		Assertions.assertEquals(" case " + COL_DFLTINTEGER + 
				" when " + COL_DFLTINTEGER + " + " + String.valueOf(ONE_HUNDRED_THOUSAND) +
				" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end ",caseExprNumb);

		/* CASE DFLTINTEGER + 10 WHEN 100000 + DFLTINTEGER THEN 200010 END */
		CaseExpression caseAdd = new CaseExpression(colDfltInteger.add(10));				
		String caseExprAdd = caseAdd.when(Expression.number(100000).add(colDfltInteger))
									.then(Expression.number(200000))
				   					.end().serialize();
		Assertions.assertEquals(" case " + COL_DFLTINTEGER + " + " + 10 + 
								" when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " + " + COL_DFLTINTEGER +
								" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end ",
								caseExprAdd);

		/* String Expressions */
		/* CASE 'Abc' || 'Def' WHEN 'Abc' || 'Def' THEN 'True' END */
		CaseExpression caseString = new CaseExpression(Expression.string(STRING_EXPR_ABC).concat(STRING_EXPR_DEF));
		String caseExprConcat2 = caseString.when(Expression.string(STRING_EXPR_ABC).concat(STRING_EXPR_DEF))
										  .then(Expression.string("True"))
										  .end().serialize();
		Assertions.assertEquals(" case 'Abc' || 'Def'" + 
								" when 'Abc' || 'Def' then 'True' end ", caseExprConcat2);
		
		/* CASE '10' || '10' WHEN '10' || '10' THEN '1010' END */
		CaseExpression caseString10 = new CaseExpression(Expression.string("10").concat("10"));
		String caseExpr10 = caseString10.when(Expression.string("10").concat(Expression.string("10")))
										  .then(Expression.string("1010"))
										  .end().serialize();
		Assertions.assertEquals(" case '10' || '10' when '10' || '10' then '1010' end ", caseExpr10);

		/* Number Expressions */
		/* CASE 10 + 10 WHEN 20 THEN 20 END */
		CaseExpression caseNumber10 = new CaseExpression(Expression.number(10).add(Expression.number(10)));
		String caseNumber10Str = caseNumber10.when(Expression.number(20))
										  .then(Expression.number(20))
										  .end().serialize();
		Assertions.assertEquals(" case 10 + 10 when 20 then 20 end ", caseNumber10Str);
		
		/* CASE 10 * 10 WHEN 10 * 10 THEN 100 END */
		CaseExpression caseMult10 = new CaseExpression(Expression.number(10).multiply(Expression.number(10)));
		String caseMult10Str = caseMult10.when(Expression.number(10).multiply(Expression.number(10)))
										  .then(Expression.number(100))
										  .end().serialize();
		Assertions.assertEquals(" case 10 * 10 when 10 * 10 then 100 end ", caseMult10Str);

	}

	@Test
	void testThen() {
		
		/* Column Expressions */
		/* CASE ? WHEN ? THEN ? */ 
		String caseExprParm = new CaseExpression(Expression.parm(Types.CHAR, "Abc"))
				.when(Expression.parm(Types.CHAR, "Def"))
				.then(Expression.parm(Types.CHAR, "Test"))
				.end().serialize();
		Assertions.assertEquals(" case ?" + " when ? then ? end ",caseExprParm);
		
		/*	CASE NOTNULLCHAR || 'ab' WHEN NOTNULLCHAR || 'ab' THEN NOTNULLCHAR || 'ab' END */
		String caseExprConcat = new CaseExpression(colNotNullChar.concat("ab"))
				.when(colNotNullChar.concat(Expression.string("ab")))
				.then(colNotNullChar.concat("ab"))
				.end().serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " || 'ab' when " + COL_NOTNULLCHAR + " || 'ab' " +
						        "then " + COL_NOTNULLCHAR + " || 'ab' end ",caseExprConcat);
		
		/* CASE NOTNULLCHAR WHEN NOTNULLCHAR THEN NOTNULLCHAR END */
		String caseExprChar = new CaseExpression(colNotNullChar).when(colNotNullChar)
																.then(colNotNullChar)
																.end().serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " when " + COL_NOTNULLCHAR + 
						        " then " + COL_NOTNULLCHAR + " end ",caseExprChar);
		
		/* CASE NOTNULLCHAR WHEN '0123456789' THEN 'A123456789' || NOTNULLCHAR END */
		String caseExprLiteral = new CaseExpression(colNotNullChar)
				.when(Expression.string(STRING_EXPR))
				.then(Expression.string(STRING_EXPR_A)).end().serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " when '" + STRING_EXPR + 
						        "' then '" + STRING_EXPR_A + "' end ",caseExprLiteral);

		/* CASE NOTNULLCHAR || 10 WHEN '112345678910' THEN 'B123456789' || '10' END */
		CaseExpression caseExpr = new CaseExpression(colNotNullChar.concat(Expression.string("10")));		
		String caseExprLiteralB = caseExpr.when(Expression.string(STRING_EXPR_1))
										  .then(Expression.string(STRING_EXPR_B).concat("10")).end()
										  .serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " || '10'"+ " when '" + STRING_EXPR_1 + 
		     "' then '" + STRING_EXPR_B + "' || '10' end ",caseExprLiteralB);
		
		/* CASE DFLTINTEGER WHEN 100000 THEN DFLTINTEGER + 200000 END */
		String caseExprNumb = new CaseExpression(colDfltInteger)
				.when(Expression.number(ONE_HUNDRED_THOUSAND))
				.then(colDfltInteger.add(Expression.number(TWO_HUNDRED_THOUSAND)))
				.end().serialize();
		Assertions.assertEquals(" case " + COL_DFLTINTEGER + 
				" when " + String.valueOf(ONE_HUNDRED_THOUSAND) +
				" then " + COL_DFLTINTEGER + " + " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end ",caseExprNumb);

		/* CASE DFLTINTEGER + 10 WHEN 100000 - 10 THEN 200000 - 10 END */
		CaseExpression caseAdd = new CaseExpression(colDfltInteger.add(10));				
		String caseExprAdd = caseAdd
				.when(Expression.number(ONE_HUNDRED_THOUSAND).subtract(Expression.number(10)))
				.then(Expression.number(TWO_HUNDRED_THOUSAND).subtract(10))
				.end().serialize();
		Assertions.assertEquals(" case " + COL_DFLTINTEGER + " + " + 10 + 
								" when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " - 10" +
								" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10" + " end ",
								caseExprAdd);

		/* String Expressions */
		/* CASE 'Abc' || 'Def' WHEN 'AbcDef' THEN 'True' || 'That!' END */
		CaseExpression caseString = new CaseExpression(Expression.string(STRING_EXPR_ABC).concat(STRING_EXPR_DEF));
		String caseExprConcat2 = caseString.when(Expression.string(STRING_EXPR_ABC + STRING_EXPR_DEF))
										  .then(Expression.string("True").concat(Expression.string(" That!")))
										  .end().serialize();
		Assertions.assertEquals(" case 'Abc' || 'Def'" + " when 'AbcDef'" +
								" then 'True' || ' That!' end ", caseExprConcat2);
		
		/* CASE '10' || '10' WHEN '1010' THEN '10' || '10' END */
		CaseExpression caseString10 = new CaseExpression(Expression.string("10").concat("10"));
		String caseExpr10 = caseString10.when(Expression.string("1010"))
										  .then(Expression.string("10").concat(Expression.string("10")))
										  .end().serialize();
		Assertions.assertEquals(" case '10' || '10' when '1010'" + 
								" then '10' || '10' end ", caseExpr10);

		/* Number Expressions */
		/* CASE 10 / 10 WHEN 0 THEN 0 END */
		CaseExpression caseDiv0 = new CaseExpression(Expression.number(10).divide(Expression.number(10)));
		String caseDiv0Str = caseDiv0.when(Expression.number(0))
										  .then(Expression.number(0))
										  .end().serialize();
		Assertions.assertEquals(" case 10 / 10 when 0 then 0 end ", caseDiv0Str);
		
		/* CASE 10 / 10 WHEN 0 THEN 10 / 10 END */
		CaseExpression caseDiv10 = new CaseExpression(Expression.number(10).divide(Expression.number(10)));
		String caseDiv10Str = caseDiv10.when(Expression.number(0))
										  .then(Expression.number(10).divide(Expression.number(10)))
										  .end().serialize();
		Assertions.assertEquals(" case 10 / 10 when 0 then 10 / 10 end ", caseDiv10Str);
	}

	@Test
	void testElseClause() {

		/* Column Expressions */
		/* CASE ? WHEN ? THEN ? ELSE ? */ 
		String caseExprParm = new CaseExpression(Expression.parm(Types.CHAR, "Abc"))
				.when(Expression.parm(Types.CHAR, "Def"))
				.then(Expression.parm(Types.CHAR, "Test"))
				.elseClause(Expression.parm(Types.CHAR, "Test2"))
				.end().serialize();
		Assertions.assertEquals(" case ?" + " when ? then ? else ? end ",caseExprParm);
		
		/*	CASE NOTNULLCHAR || 'ab' WHEN NOTNULLCHAR || 'ab' THEN NOTNULLCHAR || 'ab' 
		 *  ELSE NOTNULLCHAR || 'ab' END */
		String caseExprConcat = new CaseExpression(colNotNullChar.concat("ab"))
				.when(colNotNullChar.concat(Expression.string("ab")))
				.then(colNotNullChar.concat("ab"))
				.elseClause(colNotNullChar.concat("ab"))
				.end().serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " || 'ab' when " + COL_NOTNULLCHAR + " || 'ab' " +
						        "then " + COL_NOTNULLCHAR + " || 'ab' " + 
						        "else " + COL_NOTNULLCHAR + " || 'ab' end ",caseExprConcat);
		
		/* CASE NOTNULLCHAR WHEN NOTNULLCHAR THEN NOTNULLCHAR ELSE NOTNULLCHAR END */
		String caseExprChar = new CaseExpression(colNotNullChar).when(colNotNullChar)
																.then(colNotNullChar)
																.elseClause(colNotNullChar)
																.end().serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " when " + COL_NOTNULLCHAR + 
						        " then " + COL_NOTNULLCHAR + 
						        " else " + COL_NOTNULLCHAR + " end ",caseExprChar);
		
		/* CASE NOTNULLCHAR WHEN '0123456789' THEN 'A123456789' || NOTNULLCHAR 
		 * ELSE 'Else' || NOTNULLCHAR END */
		String caseExprLiteral = new CaseExpression(colNotNullChar)
				.when(Expression.string(STRING_EXPR))
				.then(Expression.string(STRING_EXPR_A))
				.elseClause(Expression.string("Else").concat(colNotNullChar))
				.end().serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " when '" + STRING_EXPR + 
						        "' then '" + STRING_EXPR_A + "' " + 
						        "else 'Else' || " + COL_NOTNULLCHAR + " end ",caseExprLiteral);

		/* CASE NOTNULLCHAR || 10 WHEN '112345678910' THEN 'B123456789' || '10' 
		 * ELSE 'Else' || '10' END */
		CaseExpression caseExpr = new CaseExpression(colNotNullChar.concat(Expression.string("10")));		
		String caseExprLiteralB = caseExpr.when(Expression.string(STRING_EXPR_1))
										  .then(Expression.string(STRING_EXPR_B).concat("10"))
										  .elseClause(Expression.string(STRING_EXPR_B).concat("10"))
										  .end()
										  .serialize();
		Assertions.assertEquals(" case " + COL_NOTNULLCHAR + " || '10'"+ " when '" + STRING_EXPR_1 + 
		     "' then '" + STRING_EXPR_B + "' || '10' " + 
			 "else '" + STRING_EXPR_B + "' || '10' end ",caseExprLiteralB);
		
		/* CASE DFLTINTEGER WHEN 100000 THEN DFLTINTEGER + 200000 
		 * ELSE DFLTINTEGER + 200000 END */
		String caseExprNumb = new CaseExpression(colDfltInteger)
				.when(Expression.number(ONE_HUNDRED_THOUSAND))
				.then(colDfltInteger.add(Expression.number(TWO_HUNDRED_THOUSAND)))
				.elseClause(colDfltInteger.add(Expression.number(TWO_HUNDRED_THOUSAND)))
				.end().serialize();
		Assertions.assertEquals(" case " + COL_DFLTINTEGER + 
				" when " + String.valueOf(ONE_HUNDRED_THOUSAND) +
				" then " + COL_DFLTINTEGER + " + " + String.valueOf(TWO_HUNDRED_THOUSAND) + 
				" else " + COL_DFLTINTEGER + " + " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end ",caseExprNumb);

		/* CASE DFLTINTEGER + 10 WHEN 100000 - 10 THEN 200000 - 10 
		 * ELSE 200000 - 10 END */
		CaseExpression caseAdd = new CaseExpression(colDfltInteger.add(10));				
		String caseExprAdd = caseAdd
				.when(Expression.number(ONE_HUNDRED_THOUSAND).subtract(Expression.number(10)))
				.then(Expression.number(TWO_HUNDRED_THOUSAND).subtract(10))
				.elseClause(Expression.number(TWO_HUNDRED_THOUSAND).subtract(10))
				.end().serialize();
		Assertions.assertEquals(" case " + COL_DFLTINTEGER + " + " + 10 + 
								" when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " - 10" +
								" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10" +
								" else " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10" + " end ", caseExprAdd);

		/* String Expressions */
		/* CASE 'Abc' || 'Def' WHEN 'AbcDef' THEN 'True' || 'That!' 
		 * ELSE 'Thats' || ' False' END */
		CaseExpression caseString = new CaseExpression(Expression.string(STRING_EXPR_ABC).concat(STRING_EXPR_DEF));
		String caseExprConcat2 = caseString.when(Expression.string(STRING_EXPR_ABC + STRING_EXPR_DEF))
										  .then(Expression.string("True").concat(Expression.string(" That!")))
										  .elseClause(Expression.string("Thats").concat(Expression.string(" False")))
										  .end().serialize();
		Assertions.assertEquals(" case 'Abc' || 'Def'" + " when 'AbcDef'" +
								" then 'True' || ' That!'" +
								" else 'Thats' || ' False' end ", caseExprConcat2);
		
		/* CASE '10' || '10' WHEN '1010' THEN '10' || '10' 
		 * ELSE '10' || '10' END */
		CaseExpression caseString10 = new CaseExpression(Expression.string("10").concat("10"));
		String caseExpr10 = caseString10.when(Expression.string("1010"))
										  .then(Expression.string("10").concat(Expression.string("10")))
										  .elseClause(Expression.string("10").concat(Expression.string("10")))
										  .end().serialize();
		Assertions.assertEquals(" case '10' || '10' when '1010'" + 
								" then '10' || '10'" +
								" else '10' || '10' end ", caseExpr10);

		/* Number Expressions */
		/* CASE 10 / 10 WHEN 0 THEN 0 ELSE 10 END */
		CaseExpression caseDiv0 = new CaseExpression(Expression.number(10).divide(Expression.number(10)));
		String caseDiv0Str = caseDiv0.when(Expression.number(0))
										  .then(Expression.number(0))
										  .elseClause(Expression.number(10))
										  .end().serialize();
		Assertions.assertEquals(" case 10 / 10 when 0 then 0 else 10 end ", caseDiv0Str);
		
		/* CASE 10 / 10 WHEN 0 THEN 10 / 10 ELSE 10 / 10 END */
		CaseExpression caseDiv10 = new CaseExpression(Expression.number(10).divide(Expression.number(10)));
		String caseDiv10Str = caseDiv10.when(Expression.number(0))
										  .then(Expression.number(10).divide(Expression.number(10)))
										  .elseClause(Expression.number(10).divide(Expression.number(10)))
										  .end().serialize();
		Assertions.assertEquals(" case 10 / 10 when 0 then 10 / 10 else 10 / 10 end ", caseDiv10Str);
	}

}
