package com.bobman159.rundml.core.sql.impl.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.sql.impl.CaseClause;
import com.bobman159.rundml.core.sql.impl.SQLClauseVisitor;
import com.bobman159.rundml.core.sql.types.impl.Column;

class CaseClauseTest {

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
	void testExpressionCaseExpression() {
		
		/* Not all combinations of expressions in a CASE are tested here 
		 * I think the likelihood/validity of CASE expression in nested CASE expressions is low */
		CaseClause colCase = Expression.caseExpr(Expression.column("Col01"));
		Assertions.assertNotNull(colCase);
		Assertions.assertTrue(colCase instanceof CaseClause);
		
		CaseClause nbrCase = Expression.caseExpr(Expression.constant(10));
		Assertions.assertNotNull(nbrCase);
		Assertions.assertTrue(nbrCase instanceof CaseClause);
		
		CaseClause mathCase = Expression.caseExpr(Expression.mathExpression(Expression.constant(10))
																.add(Expression.constant(20)));
		Assertions.assertNotNull(mathCase);
		Assertions.assertTrue(mathCase instanceof CaseClause);
		
		CaseClause parmCase = Expression.caseExpr(Expression.parm(Types.INTEGER, 10));
		Assertions.assertNotNull(parmCase);
		Assertions.assertTrue(parmCase instanceof CaseClause);
		
		CaseClause stringCase = Expression.caseExpr(Expression.constant("abc"));
		Assertions.assertNotNull(stringCase);
		Assertions.assertTrue(stringCase instanceof CaseClause);
	}

	@Test
	void testWhen() {
				
		/* Column Expressions - same conditions are repeated for THEN and ELSE as well */
		/* Not sure if parameter markers are fully supported in CASE expressions, but for now at least test 
		 * the syntax is generated correctly.
		 */
		/* CASE ? WHEN ? THEN NOTNULLCHAR || 'ab' */ 
		String caseExprParm = new CaseClause(Expression.parm(Types.CHAR, "Abc"))
				.when(Expression.parm(Types.CHAR, "Def"))
				.then(colNotNullChar)
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case ?" + " when ? then " + COL_NOTNULLCHAR + " end",caseExprParm);		

		/*	CASE NOTNULLCHAR || 'ab' WHEN NOTNULLCHAR || 'ab' THEN NOTNULLCHAR END */
		String caseExprConcat = new CaseClause(Expression.stringExpression(colNotNullChar).concat("ab"))
				.when(Expression.stringExpression(colNotNullChar).concat(Expression.constant("ab")))
				.then(colNotNullChar)
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || 'ab' when " + COL_NOTNULLCHAR + " || 'ab' " +
						        "then " + COL_NOTNULLCHAR + " end",caseExprConcat);
		
		/* CASE NOTNULLCHAR WHEN NOTNULCHAR THEN 'A123456789' END */
		String caseExprChar = new CaseClause(colNotNullChar).when(colNotNullChar)
																.then(Expression.constant(STRING_EXPR_A))
																.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when " + COL_NOTNULLCHAR + 
						        " then '" + STRING_EXPR_A + "' end",caseExprChar);
		
		/* CASE NOTNULLCHAR WHEN '0123456789' || NOTNULLCHAR THEN 'A123456789' END */
		String caseExprLiteral = new CaseClause(colNotNullChar)
				.when(Expression.stringExpression(Expression.constant(STRING_EXPR)).concat(colNotNullChar))
				.then(Expression.constant(STRING_EXPR_A)).end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + 
					" when '" + STRING_EXPR + "' || " + COL_NOTNULLCHAR +
					" then '" + STRING_EXPR_A + "' end",caseExprLiteral);

		/* CASE NOTNULLCHAR || 10 WHEN '112345678910' THEN 'B123456789' END */
		CaseClause caseExpr = new CaseClause(Expression.stringExpression(colNotNullChar).concat(Expression.constant("10")));		
		String caseExprLiteralB = caseExpr.when(Expression.constant(STRING_EXPR_1))
										  .then(Expression.constant(STRING_EXPR_B)).end()
										  .toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || '10'"+ " when '" + STRING_EXPR_1 + 
		     "' then '" + STRING_EXPR_B + "' end",caseExprLiteralB);

		/* CASE DFLTINTEGER WHEN DFLTINTEGER + 100000 THEN 200000 END */
		String caseExprNumb = new CaseClause(colDfltInteger)
				.when(Expression.mathExpression(colDfltInteger).add(ONE_HUNDRED_THOUSAND))
				.then(Expression.constant(TWO_HUNDRED_THOUSAND))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_DFLTINTEGER + 
				" when " + COL_DFLTINTEGER + " + " + String.valueOf(ONE_HUNDRED_THOUSAND) +
				" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end",caseExprNumb);

		/* CASE DFLTINTEGER + 10 WHEN 100000 + DFLTINTEGER THEN 200010 END */
		CaseClause caseAdd = new CaseClause(Expression.mathExpression(colDfltInteger).add(10));				
		String caseExprAdd = caseAdd.when(Expression.mathExpression(Expression.constant(100000)).add(colDfltInteger))
									.then(Expression.constant(200000))
				   					.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_DFLTINTEGER + " + " + 10 + 
								" when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " + " + COL_DFLTINTEGER +
								" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end",
								caseExprAdd);

		/* String Expressions */
		/* CASE 'Abc' || 'Def' WHEN 'Abc' || 'Def' THEN 'True' END */
		CaseClause caseString = new CaseClause(
				Expression.stringExpression(Expression.constant(STRING_EXPR_ABC))
						  .concat(STRING_EXPR_DEF));
		String caseExprConcat2 = caseString.when(Expression.stringExpression(Expression.constant(STRING_EXPR_ABC))
																					   .concat(STRING_EXPR_DEF))
										  .then(Expression.constant("True"))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case 'Abc' || 'Def'" + 
								" when 'Abc' || 'Def' then 'True' end", caseExprConcat2);
		
		/* CASE '10' || '10' WHEN '10' || '10' THEN '1010' END */
		CaseClause caseString10 = new CaseClause(Expression.stringExpression(Expression.constant("10"))
																   .concat("10"));
		String caseExpr10 = caseString10.when(Expression.stringExpression(Expression.constant("10"))
																				    .concat(Expression.constant("10")))
										  .then(Expression.constant("1010"))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case '10' || '10' when '10' || '10' then '1010' end", caseExpr10);

		/* Number Expressions */
		/* CASE 10 + 10 WHEN 20 THEN 20 END */
		CaseClause caseNumber10 = new CaseClause(Expression.mathExpression(Expression.constant(10))
																   .add(Expression.constant(10)));
		String caseNumber10Str = caseNumber10.when(Expression.constant(20))
										  .then(Expression.constant(20))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case 10 + 10 when 20 then 20 end", caseNumber10Str);
		
		/* CASE 10 * 10 WHEN 10 * 10 THEN 100 END */
		CaseClause caseMult10 = new CaseClause(Expression.mathExpression(10).multiply(Expression.constant(10)));
		String caseMult10Str = caseMult10.when(Expression.mathExpression(Expression.constant(10))
														 .multiply(Expression.constant(10)))
										  .then(Expression.constant(100))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case 10 * 10 when 10 * 10 then 100 end", caseMult10Str);

	}

	@Test
	void testThen() {
		
		/* Column Expressions */
		/* CASE ? WHEN ? THEN ? */ 
		String caseExprParm = new CaseClause(Expression.parm(Types.CHAR, "Abc"))
				.when(Expression.parm(Types.CHAR, "Def"))
				.then(Expression.parm(Types.CHAR, "Test"))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case ?" + " when ? then ? end",caseExprParm);
		
		/*	CASE NOTNULLCHAR || 'ab' WHEN NOTNULLCHAR || 'ab' THEN NOTNULLCHAR || 'ab' END */
		String caseExprConcat = new CaseClause(Expression.stringExpression(colNotNullChar).concat("ab"))
				.when(Expression.stringExpression(colNotNullChar).concat(Expression.constant("ab")))
				.then(Expression.stringExpression(colNotNullChar).concat("ab"))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || 'ab' when " + COL_NOTNULLCHAR + " || 'ab' " +
						        "then " + COL_NOTNULLCHAR + " || 'ab' end",caseExprConcat);
		
		/* CASE NOTNULLCHAR WHEN NOTNULLCHAR THEN NOTNULLCHAR END */
		String caseExprChar = new CaseClause(colNotNullChar).when(colNotNullChar)
																.then(colNotNullChar)
																.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when " + COL_NOTNULLCHAR + 
						        " then " + COL_NOTNULLCHAR + " end",caseExprChar);
		
		/* CASE NOTNULLCHAR WHEN '0123456789' THEN 'A123456789' || NOTNULLCHAR END */
		String caseExprLiteral = new CaseClause(colNotNullChar)
				.when(Expression.constant(STRING_EXPR))
				.then(Expression.constant(STRING_EXPR_A)).end()
				.toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when '" + STRING_EXPR + 
						        "' then '" + STRING_EXPR_A + "' end",caseExprLiteral);

		/* CASE NOTNULLCHAR || 10 WHEN '112345678910' THEN 'B123456789' || '10' END */
		CaseClause caseExpr = new CaseClause(Expression.stringExpression(colNotNullChar)
															   .concat(Expression.constant("10")));		
		String caseExprLiteralB = caseExpr.when(Expression.constant(STRING_EXPR_1))
										  .then(Expression.stringExpression(Expression.constant(STRING_EXPR_B))
												  									  .concat("10")).end()
										  .toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || '10'"+ " when '" + STRING_EXPR_1 + 
		     "' then '" + STRING_EXPR_B + "' || '10' end",caseExprLiteralB);
		
		/* CASE DFLTINTEGER WHEN 100000 THEN DFLTINTEGER + 200000 END */
		String caseExprNumb = new CaseClause(colDfltInteger)
				.when(Expression.constant(ONE_HUNDRED_THOUSAND))
				.then(Expression.mathExpression(colDfltInteger).add(Expression.constant(TWO_HUNDRED_THOUSAND)))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_DFLTINTEGER + 
				" when " + String.valueOf(ONE_HUNDRED_THOUSAND) +
				" then " + COL_DFLTINTEGER + " + " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end",caseExprNumb);

		/* CASE DFLTINTEGER + 10 WHEN 100000 - 10 THEN 200000 - 10 END */
		CaseClause caseAdd = new CaseClause(Expression.mathExpression(colDfltInteger).add(10));				
		String caseExprAdd = caseAdd
				.when(Expression.mathExpression(Expression.constant(ONE_HUNDRED_THOUSAND))
														  .subtract(Expression.constant(10)))
				.then(Expression.mathExpression(Expression.constant(TWO_HUNDRED_THOUSAND)).subtract(10))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_DFLTINTEGER + " + " + 10 + 
								" when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " - 10" +
								" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10" + " end",
								caseExprAdd);

		/* String Expressions */
		/* CASE 'Abc' || 'Def' WHEN 'AbcDef' THEN 'True' || 'That!' END */
		CaseClause caseString = new CaseClause(Expression.stringExpression(Expression.constant(STRING_EXPR_ABC))
																							 .concat(STRING_EXPR_DEF));
		String caseExprConcat2 = caseString.when(Expression.constant(STRING_EXPR_ABC + STRING_EXPR_DEF))
										  .then(Expression.stringExpression(Expression.constant("True"))
												  			.concat(Expression.constant(" That!")))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case 'Abc' || 'Def'" + " when 'AbcDef'" +
								" then 'True' || ' That!' end", caseExprConcat2);
		
		/* CASE '10' || '10' WHEN '1010' THEN '10' || '10' END */
		CaseClause caseString10 = new CaseClause(Expression.stringExpression(Expression.constant("10"))
																								.concat("10"));
		String caseExpr10 = caseString10.when(Expression.constant("1010"))
										  .then(Expression.stringExpression(Expression.constant("10"))
												  			.concat(Expression.constant("10")))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case '10' || '10' when '1010'" + 
								" then '10' || '10' end", caseExpr10);

		/* Number Expressions */
		/* CASE 10 / 10 WHEN 0 THEN 0 END */
		CaseClause caseDiv0 = new CaseClause(Expression.mathExpression(Expression.constant(10))
																.divide(Expression.constant(10)));
		String caseDiv0Str = caseDiv0.when(Expression.constant(0))
										  .then(Expression.constant(0))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case 10 / 10 when 0 then 0 end", caseDiv0Str);
		
		/* CASE 10 / 10 WHEN 0 THEN 10 / 10 END */
		CaseClause caseDiv10 = new CaseClause(Expression.mathExpression(Expression.constant(10))
																.divide(Expression.constant(10)));
		String caseDiv10Str = caseDiv10.when(Expression.constant(0))
										  .then(Expression.mathExpression(Expression.constant(10))
												  			.divide(Expression.constant(10)))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case 10 / 10 when 0 then 10 / 10 end", caseDiv10Str);
	}

	@Test
	void testElseClause() {

		/* Column Expressions */
		/* CASE ? WHEN ? THEN ? ELSE ? */ 
		String caseExprParm = new CaseClause(Expression.parm(Types.CHAR, "Abc"))
				.when(Expression.parm(Types.CHAR, "Def"))
				.then(Expression.parm(Types.CHAR, "Test"))
				.elseClause(Expression.parm(Types.CHAR, "Test2"))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case ?" + " when ? then ? else ? end",caseExprParm);
		
		/*	CASE NOTNULLCHAR || 'ab' WHEN NOTNULLCHAR || 'ab' THEN NOTNULLCHAR || 'ab' 
		 *  ELSE NOTNULLCHAR || 'ab' END */
		String caseExprConcat = new CaseClause(Expression.stringExpression(colNotNullChar).concat("ab"))
				.when(Expression.stringExpression(colNotNullChar).concat(Expression.constant("ab")))
				.then(Expression.stringExpression(colNotNullChar).concat("ab"))
				.elseClause(Expression.stringExpression(colNotNullChar).concat("ab"))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || 'ab' when " + COL_NOTNULLCHAR + " || 'ab' " +
						        "then " + COL_NOTNULLCHAR + " || 'ab' " + 
						        "else " + COL_NOTNULLCHAR + " || 'ab' end",caseExprConcat);
		
		/* CASE NOTNULLCHAR WHEN NOTNULLCHAR THEN NOTNULLCHAR ELSE NOTNULLCHAR END */
		String caseExprChar = new CaseClause(colNotNullChar).when(colNotNullChar)
																.then(colNotNullChar)
																.elseClause(colNotNullChar)
																.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when " + COL_NOTNULLCHAR + 
						        " then " + COL_NOTNULLCHAR + 
						        " else " + COL_NOTNULLCHAR + " end",caseExprChar);
		
		/* CASE NOTNULLCHAR WHEN '0123456789' THEN 'A123456789' || NOTNULLCHAR 
		 * ELSE 'Else' || NOTNULLCHAR END */
		String caseExprLiteral = new CaseClause(colNotNullChar)
				.when(Expression.constant(STRING_EXPR))
				.then(Expression.constant(STRING_EXPR_A))
				.elseClause(Expression.stringExpression(Expression.constant("Else")).concat(colNotNullChar))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when '" + STRING_EXPR + 
						        "' then '" + STRING_EXPR_A + "' " + 
						        "else 'Else' || " + COL_NOTNULLCHAR + " end",caseExprLiteral);

		/* CASE NOTNULLCHAR || 10 WHEN '112345678910' THEN 'B123456789' || '10' 
		 * ELSE 'Else' || '10' END */
		CaseClause caseExpr = new CaseClause(Expression.stringExpression(colNotNullChar)
																.concat(Expression.constant("10")));		
		String caseExprLiteralB = caseExpr.when(Expression.constant(STRING_EXPR_1))
										  .then(Expression.stringExpression(Expression.constant(STRING_EXPR_B))
												  									  .concat("10"))
										  .elseClause(Expression.stringExpression(Expression.constant(STRING_EXPR_B))
												  				.concat("10"))
										  .end()
										  .toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || '10'"+ " when '" + STRING_EXPR_1 + 
		     "' then '" + STRING_EXPR_B + "' || '10' " + 
			 "else '" + STRING_EXPR_B + "' || '10' end",caseExprLiteralB);
		
		/* CASE DFLTINTEGER WHEN 100000 THEN DFLTINTEGER + 200000 
		 * ELSE DFLTINTEGER + 200000 END */
		String caseExprNumb = new CaseClause(colDfltInteger)
				.when(Expression.constant(ONE_HUNDRED_THOUSAND))
				.then(Expression.mathExpression(colDfltInteger).add(Expression.constant(TWO_HUNDRED_THOUSAND)))
				.elseClause(Expression.mathExpression(colDfltInteger).add(Expression.constant(TWO_HUNDRED_THOUSAND)))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_DFLTINTEGER + 
				" when " + String.valueOf(ONE_HUNDRED_THOUSAND) +
				" then " + COL_DFLTINTEGER + " + " + String.valueOf(TWO_HUNDRED_THOUSAND) + 
				" else " + COL_DFLTINTEGER + " + " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end",caseExprNumb);

		/* CASE DFLTINTEGER + 10 WHEN 100000 - 10 THEN 200000 - 10 
		 * ELSE 200000 - 10 END */
		CaseClause caseAdd = new CaseClause(Expression.mathExpression(colDfltInteger).add(10));				
		String caseExprAdd = caseAdd
				.when(Expression.mathExpression(Expression.constant(ONE_HUNDRED_THOUSAND)).subtract(Expression.constant(10)))
				.then(Expression.mathExpression(Expression.constant(TWO_HUNDRED_THOUSAND)).subtract(10))
				.elseClause(Expression.mathExpression(Expression.constant(TWO_HUNDRED_THOUSAND)).subtract(10))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case " + COL_DFLTINTEGER + " + " + 10 + 
								" when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " - 10" +
								" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10" +
								" else " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10" + " end", caseExprAdd);

		/* String Expressions */
		/* CASE 'Abc' || 'Def' WHEN 'AbcDef' THEN 'True' || 'That!' 
		 * ELSE 'Thats' || ' False' END */
		CaseClause caseString = new CaseClause(Expression.stringExpression(Expression.constant(STRING_EXPR_ABC))
																.concat(STRING_EXPR_DEF));
		String caseExprConcat2 = caseString.when(Expression.constant(STRING_EXPR_ABC + STRING_EXPR_DEF))
										  .then(Expression.stringExpression(Expression.constant("True"))
												  		   .concat(Expression.constant(" That!")))
										  .elseClause(Expression.stringExpression(Expression.constant("Thats"))
												  				.concat(Expression.constant(" False")))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case 'Abc' || 'Def'" + " when 'AbcDef'" +
								" then 'True' || ' That!'" +
								" else 'Thats' || ' False' end", caseExprConcat2);
		
		/* CASE '10' || '10' WHEN '1010' THEN '10' || '10' 
		 * ELSE '10' || '10' END */
		CaseClause caseString10 = new CaseClause(Expression.stringExpression(Expression.constant("10"))
																	.concat("10"));
		String caseExpr10 = caseString10.when(Expression.constant("1010"))
										  .then(Expression.stringExpression(Expression.constant("10"))
												  			.concat(Expression.constant("10")))
										  .elseClause(Expression.stringExpression("10").concat(Expression.constant("10")))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case '10' || '10' when '1010'" + 
								" then '10' || '10'" +
								" else '10' || '10' end", caseExpr10);

		/* Number Expressions */
		/* CASE 10 / 10 WHEN 0 THEN 0 ELSE 10 END */
		CaseClause caseDiv0 = new CaseClause(Expression.mathExpression(Expression.constant(10))
																.divide(Expression.constant(10)));
		String caseDiv0Str = caseDiv0.when(Expression.constant(0))
										  .then(Expression.constant(0))
										  .elseClause(Expression.constant(10))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case 10 / 10 when 0 then 0 else 10 end", caseDiv0Str);
		
		/* CASE 10 / 10 WHEN 0 THEN 10 / 10 ELSE 10 / 10 END */
		CaseClause caseDiv10 = new CaseClause(Expression.mathExpression(Expression.constant(10))
																.divide(Expression.constant(10)));
		String caseDiv10Str = caseDiv10.when(Expression.constant(0))
										  .then(Expression.mathExpression(Expression.constant(10))
												  		  .divide(Expression.constant(10)))
										  .elseClause(Expression.mathExpression(Expression.constant(10))
												  				.divide(Expression.constant(10)))
										  .end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case 10 / 10 when 0 then 10 / 10 else 10 / 10 end", caseDiv10Str);
	}
	
	
	@Test
	void testMultiWhenThenClauses() {

		/* Column Expressions */
		/* CASE ? when ? THEN ?
		 *		  when colNotNullChar || 'ab' then colNotNullChar || 'ab' 
			 	  when '123456789' then 'B123456789' || '10' "
			 	  when 100000 - 10 then 200000 + - 10 
		 		  when 'AbcDef' then 'True' || ' That!'	
		 * 
		 * ELSE ? */ 
		String caseExprParm = new CaseClause(Expression.parm(Types.CHAR, "Abc"))
				.when(Expression.parm(Types.CHAR, "Def")).then(Expression.parm(Types.CHAR, "Test"))
				.when(Expression.stringExpression(Expression.column(COL_NOTNULLCHAR)).concat(Expression.constant("ab")))
				.then(Expression.stringExpression(Expression.column(COL_NOTNULLCHAR)).concat("ab"))
				
				.when(Expression.constant(STRING_EXPR_1))
				.then(Expression.stringExpression(Expression.constant(STRING_EXPR_B))
						  									  .concat("10"))
				.when(Expression.mathExpression(Expression.constant(ONE_HUNDRED_THOUSAND)).subtract(Expression.constant(10)))
				.then(Expression.mathExpression(Expression.constant(TWO_HUNDRED_THOUSAND)).subtract(10))				

				.when(Expression.constant(STRING_EXPR_ABC + STRING_EXPR_DEF))
				.then(Expression.stringExpression(Expression.constant("True"))
						  		   .concat(Expression.constant(" That!")))
				
				.elseClause(Expression.parm(Types.CHAR, "Test2"))
				.end().toSQLClause(SQLClauseVisitor.getInstance());
		Assertions.assertEquals("case ?" + " when ? then ? " + 
				"when NotNullChar || 'ab' then NotNullChar || 'ab' " +
				"when '" + STRING_EXPR_1 + "' then '" + STRING_EXPR_B + "' || '10' " +
				"when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " - 10 " +
				"then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10 " +
				"when 'AbcDef'" + " then 'True' || ' That!'" +				
				" else ? end",caseExprParm);
	
	}

}
