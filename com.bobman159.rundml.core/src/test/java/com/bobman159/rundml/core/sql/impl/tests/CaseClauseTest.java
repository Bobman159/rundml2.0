package com.bobman159.rundml.core.sql.impl.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.sql.ICaseClause;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.core.sql.impl.CaseClause;
import com.bobman159.rundml.core.sql.serialize.impl.TestBaseSQLSerializer;
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
	private final TestBaseSQLSerializer serializer = new TestBaseSQLSerializer();
	
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
		ICaseClause colCase = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().column("Col01")).end();
		Assertions.assertNotNull(colCase);
		Assertions.assertTrue(colCase instanceof CaseClause);
		
		ICaseClause nbrCase = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().constant(10)).end();
		Assertions.assertNotNull(nbrCase);
		Assertions.assertTrue(nbrCase instanceof CaseClause);
		
		ICaseClause mathCase = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																	.add(SQLTypeFactory.getInstance().constant(20))).end();
		Assertions.assertNotNull(mathCase);
		Assertions.assertTrue(mathCase instanceof CaseClause);
		
		ICaseClause parmCase = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().parm(Types.INTEGER, 10)).end();
		Assertions.assertNotNull(parmCase);
		Assertions.assertTrue(parmCase instanceof CaseClause);
		
		ICaseClause stringCase = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().constant("abc")).end();
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
		String caseExprParm = serializer.serialize(SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().parm(Types.CHAR, "Abc"))
				.when(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"))
				.then(colNotNullChar)
				.end());
		Assertions.assertEquals("case ?" + " when ? then " + COL_NOTNULLCHAR + " end",caseExprParm);		

		/*	CASE NOTNULLCHAR || 'ab' WHEN NOTNULLCHAR || 'ab' THEN NOTNULLCHAR END */
		String caseExprConcat = serializer.serialize(
				SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat("ab"))
							  .when(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(SQLTypeFactory.getInstance().constant("ab")))
							  .then(colNotNullChar)
							  .end());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || 'ab' when " + COL_NOTNULLCHAR + " || 'ab' " +
						        "then " + COL_NOTNULLCHAR + " end",caseExprConcat);
		
		/* CASE NOTNULLCHAR WHEN NOTNULCHAR THEN 'A123456789' END */
		String caseExprChar = serializer.serialize(SQLTypeFactory.getInstance().caseClause(colNotNullChar).when(colNotNullChar)
																.then(SQLTypeFactory.getInstance().constant(STRING_EXPR_A))
																.end());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when " + COL_NOTNULLCHAR + 
						        " then '" + STRING_EXPR_A + "' end",caseExprChar);
		
		/* CASE NOTNULLCHAR WHEN '0123456789' || NOTNULLCHAR THEN 'A123456789' END */
		String caseExprLiteral = serializer.serialize(SQLTypeFactory.getInstance().caseClause(colNotNullChar)
			.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR))
							 .concat(colNotNullChar))
				.then(SQLTypeFactory.getInstance().constant(STRING_EXPR_A)).end());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + 
					" when '" + STRING_EXPR + "' || " + COL_NOTNULLCHAR +
					" then '" + STRING_EXPR_A + "' end",caseExprLiteral);

		/* CASE NOTNULLCHAR || 10 WHEN '112345678910' THEN 'B123456789' END */
		ICaseClause caseExpr = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(colNotNullChar)
																.concat(SQLTypeFactory.getInstance().constant("10")))
											 .when(SQLTypeFactory.getInstance().constant(STRING_EXPR_1))
											 .then(SQLTypeFactory.getInstance().constant(STRING_EXPR_B))
											 .end();		
		
		String caseExprLiteralB = serializer.serialize(caseExpr);
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || '10'"+ " when '" + STRING_EXPR_1 + 
		     "' then '" + STRING_EXPR_B + "' end",caseExprLiteralB);

		/* CASE DFLTINTEGER WHEN DFLTINTEGER + 100000 THEN 200000 END */
		String caseExprNumb = serializer.serialize(SQLTypeFactory.getInstance().caseClause(colDfltInteger)
				.when(SQLTypeFactory.getInstance().mathExpression(colDfltInteger).add(ONE_HUNDRED_THOUSAND))
				.then(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND))
				.end());
		Assertions.assertEquals("case " + COL_DFLTINTEGER + 
				" when " + COL_DFLTINTEGER + " + " + String.valueOf(ONE_HUNDRED_THOUSAND) +
				" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end",caseExprNumb);

		/* CASE DFLTINTEGER + 10 WHEN 100000 + DFLTINTEGER THEN 200010 END */
		ICaseClause caseAdd = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(colDfltInteger)
																	 .add(10))
											.when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(100000))
																.add(colDfltInteger))
											.then(SQLTypeFactory.getInstance().constant(200000))
											.end();				
		
		String caseExprAdd = serializer.serialize(caseAdd);
		Assertions.assertEquals("case " + COL_DFLTINTEGER + " + " + 10 + 
								" when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " + " + COL_DFLTINTEGER +
								" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end",
								caseExprAdd);

		/* String Expressions */
		/* CASE 'Abc' || 'Def' WHEN 'Abc' || 'Def' THEN 'True' END */
		CaseClause caseString = (CaseClause) SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(
																	   SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC))
						  															 .concat(STRING_EXPR_DEF))
				.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC))
						   											.concat(STRING_EXPR_DEF))
				.then(SQLTypeFactory.getInstance().constant("True"))
				.end();
		
		String caseExprConcat2 = serializer.serialize(caseString);
		Assertions.assertEquals("case 'Abc' || 'Def'" + 
								" when 'Abc' || 'Def' then 'True' end", caseExprConcat2);
		
		/* CASE '10' || '10' WHEN '10' || '10' THEN '1010' END */
		CaseClause caseString10 = (CaseClause) SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(
																		 SQLTypeFactory.getInstance().constant("10")).concat("10"))
															 .when(SQLTypeFactory.getInstance().stringExpression(
																   SQLTypeFactory.getInstance().constant("10"))
																	 			 .concat(SQLTypeFactory.getInstance().constant("10")))
															 .then(SQLTypeFactory.getInstance().constant("1010"))
															 .end();
		String caseExpr10 = serializer.serialize(caseString10);
		Assertions.assertEquals("case '10' || '10' when '10' || '10' then '1010' end", caseExpr10);

		/* Number Expressions */
		/* CASE 10 + 10 WHEN 20 THEN 20 END */
		ICaseClause caseNumber10 = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																   		  .add(SQLTypeFactory.getInstance().constant(10)))
												.when(SQLTypeFactory.getInstance().constant(20))
												.then(SQLTypeFactory.getInstance().constant(20))
												.end();
		String caseNumber10Str = serializer.serialize(caseNumber10);
		Assertions.assertEquals("case 10 + 10 when 20 then 20 end", caseNumber10Str);
		
		/* CASE 10 * 10 WHEN 10 * 10 THEN 100 END */
		ICaseClause caseMult10 = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(10)
																		.multiply(SQLTypeFactory.getInstance().constant(10)))
											  .when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																  .multiply(SQLTypeFactory.getInstance().constant(10)))
											  .then(SQLTypeFactory.getInstance().constant(100))
											  .end();
		String caseMult10Str = serializer.serialize(caseMult10);
		Assertions.assertEquals("case 10 * 10 when 10 * 10 then 100 end", caseMult10Str);

	}

	@Test
	void testThen() {
		
		/* Column Expressions */
		/* CASE ? WHEN ? THEN ? */ 
		String caseExprParm = serializer.serialize(SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().parm(Types.CHAR, "Abc"))
				.when(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"))
				.then(SQLTypeFactory.getInstance().parm(Types.CHAR, "Test"))
				.end());
		Assertions.assertEquals("case ?" + " when ? then ? end",caseExprParm);
		
		/*	CASE NOTNULLCHAR || 'ab' WHEN NOTNULLCHAR || 'ab' THEN NOTNULLCHAR || 'ab' END */
		String caseExprConcat = serializer.serialize(SQLTypeFactory.getInstance().caseClause(
				SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat("ab"))
				.when(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(SQLTypeFactory.getInstance().constant("ab")))
				.then(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat("ab"))
				.end());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || 'ab' when " + COL_NOTNULLCHAR + " || 'ab' " +
						        "then " + COL_NOTNULLCHAR + " || 'ab' end",caseExprConcat);
		
		/* CASE NOTNULLCHAR WHEN NOTNULLCHAR THEN NOTNULLCHAR END */
		String caseExprChar = serializer.serialize(SQLTypeFactory.getInstance().caseClause(colNotNullChar).when(colNotNullChar)
																.then(colNotNullChar)
																.end());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when " + COL_NOTNULLCHAR + 
						        " then " + COL_NOTNULLCHAR + " end",caseExprChar);
		
		/* CASE NOTNULLCHAR WHEN '0123456789' THEN 'A123456789' || NOTNULLCHAR END */
		String caseExprLiteral = serializer.serialize(SQLTypeFactory.getInstance().caseClause(colNotNullChar)
				.when(SQLTypeFactory.getInstance().constant(STRING_EXPR))
				.then(SQLTypeFactory.getInstance().constant(STRING_EXPR_A)).end());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when '" + STRING_EXPR + 
						        "' then '" + STRING_EXPR_A + "' end",caseExprLiteral);

		/* CASE NOTNULLCHAR || 10 WHEN '112345678910' THEN 'B123456789' || '10' END */
		ICaseClause caseExpr = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(colNotNullChar)
																	   .concat(SQLTypeFactory.getInstance().constant("10")))
											 .when(SQLTypeFactory.getInstance().constant(STRING_EXPR_1))
											 .then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_B))
													 			 .concat("10"))
											 .end();
		String caseExprLiteralB = serializer.serialize(caseExpr);
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || '10'"+ " when '" + STRING_EXPR_1 + 
		     "' then '" + STRING_EXPR_B + "' || '10' end",caseExprLiteralB);
		
		/* CASE DFLTINTEGER WHEN 100000 THEN DFLTINTEGER + 200000 END */
		String caseExprNumb = serializer.serialize(SQLTypeFactory.getInstance().caseClause(colDfltInteger)
				.when(SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND))
				.then(SQLTypeFactory.getInstance().mathExpression(colDfltInteger).add(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND)))
				.end());
		Assertions.assertEquals("case " + COL_DFLTINTEGER + 
				" when " + String.valueOf(ONE_HUNDRED_THOUSAND) +
				" then " + COL_DFLTINTEGER + " + " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end",caseExprNumb);

		/* CASE DFLTINTEGER + 10 WHEN 100000 - 10 THEN 200000 - 10 END */
		ICaseClause caseAdd = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(colDfltInteger).add(10))
										   .when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance()
												   			   .constant(ONE_HUNDRED_THOUSAND))
												   			   .subtract(SQLTypeFactory.getInstance().constant(10)))
										   .then(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND))
												   			   .subtract(10))
										   .end();
		String caseExprAdd = serializer.serialize(caseAdd);

		Assertions.assertEquals("case " + COL_DFLTINTEGER + " + " + 10 + 
								" when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " - 10" +
								" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10" + " end",
								caseExprAdd);

		/* String Expressions */
		/* CASE 'Abc' || 'Def' WHEN 'AbcDef' THEN 'True' || 'That!' END */
		ICaseClause caseString = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(
														  SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC))
																		.concat(STRING_EXPR_DEF))
											   .when(SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC + STRING_EXPR_DEF))
											   .then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("True"))
												        			.concat(SQLTypeFactory.getInstance().constant(" That!")))
												.end();
		String caseExprConcat2 = serializer.serialize(caseString);
		Assertions.assertEquals("case 'Abc' || 'Def'" + " when 'AbcDef'" +
								" then 'True' || ' That!' end", caseExprConcat2);
		
		/* CASE '10' || '10' WHEN '1010' THEN '10' || '10' END */
		ICaseClause caseString10 = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(
								   SQLTypeFactory.getInstance().constant("10")).concat("10"))
												.when(SQLTypeFactory.getInstance().constant("1010"))
												.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("10"))
																	.concat(SQLTypeFactory.getInstance().constant("10")))
												.end();
		String caseExpr10 = serializer.serialize(caseString10);
		Assertions.assertEquals("case '10' || '10' when '1010'" + 
								" then '10' || '10' end", caseExpr10);

		/* Number Expressions */
		/* CASE 10 / 10 WHEN 0 THEN 0 END */
		ICaseClause caseDiv0 = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																.divide(SQLTypeFactory.getInstance().constant(10)))
											.when(SQLTypeFactory.getInstance().constant(0))
											.then(SQLTypeFactory.getInstance().constant(0))
											.end();
		String caseDiv0Str = serializer.serialize(caseDiv0);
		Assertions.assertEquals("case 10 / 10 when 0 then 0 end", caseDiv0Str);
		
		/* CASE 10 / 10 WHEN 0 THEN 10 / 10 END */
		ICaseClause caseDiv10 = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																.divide(SQLTypeFactory.getInstance().constant(10)))
											 .when(SQLTypeFactory.getInstance().constant(0))
											 .then(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																 .divide(SQLTypeFactory.getInstance().constant(10)))
											 .end();
		String caseDiv10Str = serializer.serialize(caseDiv10);
		Assertions.assertEquals("case 10 / 10 when 0 then 10 / 10 end", caseDiv10Str);
	}

	@Test
	void testElseClause() {

		/* Column Expressions */
		/* CASE ? WHEN ? THEN ? ELSE ? */ 
		String caseExprParm = serializer.serialize(SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().parm(Types.CHAR, "Abc"))
				.when(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"))
				.then(SQLTypeFactory.getInstance().parm(Types.CHAR, "Test"))
				.elseClause(SQLTypeFactory.getInstance().parm(Types.CHAR, "Test2"))
				.end());
		Assertions.assertEquals("case ?" + " when ? then ? else ? end",caseExprParm);
		
		/*	CASE NOTNULLCHAR || 'ab' WHEN NOTNULLCHAR || 'ab' THEN NOTNULLCHAR || 'ab' 
		 *  ELSE NOTNULLCHAR || 'ab' END */
		String caseExprConcat = serializer.serialize(SQLTypeFactory.getInstance().caseClause(
													 SQLTypeFactory.getInstance().stringExpression(colNotNullChar)
													 				.concat("ab"))
				.when(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(SQLTypeFactory.getInstance().constant("ab")))
				.then(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat("ab"))
				.elseClause(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat("ab"))
				.end());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || 'ab' when " + COL_NOTNULLCHAR + " || 'ab' " +
						        "then " + COL_NOTNULLCHAR + " || 'ab' " + 
						        "else " + COL_NOTNULLCHAR + " || 'ab' end",caseExprConcat);
		
		/* CASE NOTNULLCHAR WHEN NOTNULLCHAR THEN NOTNULLCHAR ELSE NOTNULLCHAR END */
		String caseExprChar = serializer.serialize(SQLTypeFactory.getInstance().caseClause(colNotNullChar).when(colNotNullChar)
																.then(colNotNullChar)
																.elseClause(colNotNullChar)
																.end());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when " + COL_NOTNULLCHAR + 
						        " then " + COL_NOTNULLCHAR + 
						        " else " + COL_NOTNULLCHAR + " end",caseExprChar);
		
		/* CASE NOTNULLCHAR WHEN '0123456789' THEN 'A123456789' || NOTNULLCHAR 
		 * ELSE 'Else' || NOTNULLCHAR END */
		String caseExprLiteral = serializer.serialize(SQLTypeFactory.getInstance().caseClause(colNotNullChar)
				.when(SQLTypeFactory.getInstance().constant(STRING_EXPR))
				.then(SQLTypeFactory.getInstance().constant(STRING_EXPR_A))
				.elseClause(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("Else")).concat(colNotNullChar))
				.end());
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " when '" + STRING_EXPR + 
						        "' then '" + STRING_EXPR_A + "' " + 
						        "else 'Else' || " + COL_NOTNULLCHAR + " end",caseExprLiteral);

		/* CASE NOTNULLCHAR || 10 WHEN '112345678910' THEN 'B123456789' || '10' 
		 * ELSE 'Else' || '10' END */
		ICaseClause caseExpr = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(colNotNullChar)
																.concat(SQLTypeFactory.getInstance().constant("10")))		
											.when(SQLTypeFactory.getInstance().constant(STRING_EXPR_1))
											.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_B))
																.concat("10"))
											.elseClause(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_B))
																		  				.concat("10"))
											.end();
		String caseExprLiteralB = serializer.serialize(caseExpr);
		Assertions.assertEquals("case " + COL_NOTNULLCHAR + " || '10'"+ " when '" + STRING_EXPR_1 + 
		     "' then '" + STRING_EXPR_B + "' || '10' " + 
			 "else '" + STRING_EXPR_B + "' || '10' end",caseExprLiteralB);
		
		/* CASE DFLTINTEGER WHEN 100000 THEN DFLTINTEGER + 200000 
		 * ELSE DFLTINTEGER + 200000 END */
		String caseExprNumb = serializer.serialize(SQLTypeFactory.getInstance().caseClause(colDfltInteger)
				.when(SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND))
				.then(SQLTypeFactory.getInstance().mathExpression(colDfltInteger).add(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND)))
				.elseClause(SQLTypeFactory.getInstance().mathExpression(colDfltInteger).add(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND)))
				.end());
		Assertions.assertEquals("case " + COL_DFLTINTEGER + 
				" when " + String.valueOf(ONE_HUNDRED_THOUSAND) +
				" then " + COL_DFLTINTEGER + " + " + String.valueOf(TWO_HUNDRED_THOUSAND) + 
				" else " + COL_DFLTINTEGER + " + " + String.valueOf(TWO_HUNDRED_THOUSAND) + " end",caseExprNumb);

		/* CASE DFLTINTEGER + 10 WHEN 100000 - 10 THEN 200000 - 10 
		 * ELSE 200000 - 10 END */
		ICaseClause caseAdd = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(colDfltInteger).add(10))				
										   .when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND))
												   			   .subtract(SQLTypeFactory.getInstance().constant(10)))
										   .then(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND))
												   			   .subtract(10))
										   .elseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND))
												   					 .subtract(10))
										   .end();
		String caseExprAdd = serializer.serialize(caseAdd);
		Assertions.assertEquals("case " + COL_DFLTINTEGER + " + " + 10 + 
								" when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " - 10" +
								" then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10" +
								" else " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10" + " end", caseExprAdd);

		/* String Expressions */
		/* CASE 'Abc' || 'Def' WHEN 'AbcDef' THEN 'True' || 'That!' 
		 * ELSE 'Thats' || ' False' END */
		ICaseClause caseString = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(
														  SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC))
																		.concat(STRING_EXPR_DEF))
											  .when(SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC + STRING_EXPR_DEF))
											  .then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("True"))
																  .concat(SQLTypeFactory.getInstance().constant(" That!")))
											  .elseClause(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("Thats"))
																		.concat(SQLTypeFactory.getInstance().constant(" False")))
											  .end();
		String caseExprConcat2 = serializer.serialize(caseString);
		Assertions.assertEquals("case 'Abc' || 'Def'" + " when 'AbcDef'" +
								" then 'True' || ' That!'" +
								" else 'Thats' || ' False' end", caseExprConcat2);
		
		/* CASE '10' || '10' WHEN '1010' THEN '10' || '10' 
		 * ELSE '10' || '10' END */
		ICaseClause caseString10 = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().stringExpression(
															SQLTypeFactory.getInstance().constant("10")).concat("10"))
												.when(SQLTypeFactory.getInstance().constant("1010"))
												.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("10"))
																	.concat(SQLTypeFactory.getInstance().constant("10")))
												.elseClause(SQLTypeFactory.getInstance().stringExpression("10").concat(SQLTypeFactory.getInstance().constant("10")))
												.end();
		String caseExpr10 = serializer.serialize(caseString10);
		Assertions.assertEquals("case '10' || '10' when '1010'" + 
								" then '10' || '10'" +
								" else '10' || '10' end", caseExpr10);

		/* Number Expressions */
		/* CASE 10 / 10 WHEN 0 THEN 0 ELSE 10 END */
		ICaseClause caseDiv0 = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																.divide(SQLTypeFactory.getInstance().constant(10)))
											.when(SQLTypeFactory.getInstance().constant(0))
											.then(SQLTypeFactory.getInstance().constant(0))
											.elseClause(SQLTypeFactory.getInstance().constant(10))
											.end();
		String caseDiv0Str = serializer.serialize(caseDiv0);
		Assertions.assertEquals("case 10 / 10 when 0 then 0 else 10 end", caseDiv0Str);
		
		/* CASE 10 / 10 WHEN 0 THEN 10 / 10 ELSE 10 / 10 END */
		ICaseClause caseDiv10 = SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																	   .divide(SQLTypeFactory.getInstance().constant(10)))
											 .when(SQLTypeFactory.getInstance().constant(0))
											 .then(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																 .divide(SQLTypeFactory.getInstance().constant(10)))
											 .elseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10))
																	   .divide(SQLTypeFactory.getInstance().constant(10)))
											 .end();
		String caseDiv10Str = serializer.serialize(caseDiv10);
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
		String caseExprParm = serializer.serialize(SQLTypeFactory.getInstance().caseClause(SQLTypeFactory.getInstance().parm(Types.CHAR, "Abc"))
				.when(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def")).then(SQLTypeFactory.getInstance().parm(Types.CHAR, "Test"))
				.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR)).concat(SQLTypeFactory.getInstance().constant("ab")))
				.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR)).concat("ab"))
				
				.when(SQLTypeFactory.getInstance().constant(STRING_EXPR_1))
				.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_B))
						  									  .concat("10"))
				.when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND))
						    		.subtract(SQLTypeFactory.getInstance().constant(10)))
				.then(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND))
									.subtract(10))				
				.when(SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC + STRING_EXPR_DEF))
				.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("True"))
						  		   .concat(SQLTypeFactory.getInstance().constant(" That!")))
				.elseClause(SQLTypeFactory.getInstance().parm(Types.CHAR, "Test2"))
				.end());
		Assertions.assertEquals("case ?" + " when ? then ? " + 
				"when NotNullChar || 'ab' then NotNullChar || 'ab' " +
				"when '" + STRING_EXPR_1 + "' then '" + STRING_EXPR_B + "' || '10' " +
				"when " + String.valueOf(ONE_HUNDRED_THOUSAND) + " - 10 " +
				"then " + String.valueOf(TWO_HUNDRED_THOUSAND) + " - 10 " +
				"when 'AbcDef'" + " then 'True' || ' That!'" +				
				" else ? end",caseExprParm);
	
	}

}
