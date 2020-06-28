package com.bobman159.rundml.core.sql.impl.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.IExpressionFactory;
import com.bobman159.rundml.core.factory.RunDMLTestFactory;
import com.bobman159.rundml.core.sql.impl.CaseClause;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.types.impl.Column;

class CaseWhenExpressionTest {
	
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
	
	private final RunDMLTestFactory testFactory = RunDMLTestFactory.getInstance();

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
	void testCaseWhenExpression() {
		
		CaseClause colCase = testFactory.caseWhen();
		Assertions.assertNotNull(colCase);
		Assertions.assertTrue(colCase.isCaseWhen());
		
	}

//	@Test
//	void testWhenNumberOpNumber() {
//		fail("Not yet implemented");
//	}

	@Test
	void testWhenIExpressionOpIExpression() {
		
		/* Parameter Marker Expressions */
		/* CASE WHEN ? = 'ab' THEN NOTNULLCHAR || 'ab' */ 
		
		/* SELECT CASE WHEN ? = 'ab' THEN ? END */
		String caseExprParm = testFactory.caseWhen()
				.when(testFactory.parm(Types.CHAR, "Def"),Op.EQ,testFactory.constant("ab"))
				.then(testFactory.parm(Types.CHAR, "Def"))
				.end().serialize();
		Assertions.assertEquals(" case when ? = 'ab' then ? end ",caseExprParm);
		
		/* SELECT CASE WHEN ? / 100000 = 0 THEN ? / 100000  END */
		String caseExprParmDiv = testFactory.caseWhen()
				.when(testFactory.mathExpression(testFactory.parm(Types.INTEGER, 100000))
								.divide(100000),Op.EQ,testFactory.constant(0))
				.then(testFactory.mathExpression(testFactory.parm(Types.INTEGER, 100000)).divide(100000))
				.end().serialize();
		Assertions.assertEquals(" case when ? / 100000 = 0 then ? / 100000 end ",caseExprParmDiv);
		
		/* SELECT CASE WHEN ? || 'Def' = 'AbcDef' THEN ? || 'Xyz'  END */
		String caseExprParmConcat = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.parm(Types.VARCHAR, "Abc"))
								.concat("Def"),Op.EQ,testFactory.constant("AbcDef"))
				.then(testFactory.stringExpression(testFactory.parm(Types.VARCHAR, "Abc")).concat("Xyz"))
				.end().serialize();
		Assertions.assertEquals(" case when ? || 'Def' = 'AbcDef'"+ 
								" then ? || 'Xyz' end ",caseExprParmConcat);
		
		/* Column Expressions - same conditions are repeated for THEN and ELSE as well */
		/* Not sure if parameter markers are fully supported in CASE expressions, but for now at least test 
		 * the syntax is generated correctly.
		 */
		
		/* CASE WHEN NOTNULLCHAR = '012345678' THEN NOTNULLCHAR || 'ab' END */
		String caseExprCol = testFactory.caseWhen()
				.when(testFactory.column(COL_NOTNULLCHAR),Op.EQ,testFactory.constant(STRING_EXPR))
				.then(testFactory.stringExpression(colNotNullChar).concat("ab"))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + "' then " + 
								COL_NOTNULLCHAR + " || 'ab' end ",caseExprCol);		

		/* CASE WHEN NOTNULLCHAR || 'Abc' = NOTNULLCHAR || 'Abc' THEN NOTNULLCHAR || 'Abc' END */
		String caseExprNotNullChar = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.column(COL_NOTNULLCHAR)).concat(STRING_EXPR_ABC),Op.EQ,
					  testFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
				.then(testFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "' = " + 
								COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "'" +
								" then " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "' end ",caseExprNotNullChar);		

		/* CASE WHEN NOTNULLCHAR = '0123456789' THEN 'A123456789' END */
		String caseExprNotNullChar2 = testFactory.caseWhen()
				.when(testFactory.column(COL_NOTNULLCHAR),Op.EQ,testFactory.constant(STRING_EXPR))
				.then(testFactory.constant(STRING_EXPR_A))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + "' then " + 
								"'" + STRING_EXPR_A +"' end ",caseExprNotNullChar2);
		
		/* CASE WHEN NOTNULLCHAR || 10 >= '112345678910' THEN 'B123456789' END */
		String caseExprNotNullGE = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.column(COL_NOTNULLCHAR))
															.concat(testFactory.constant(10)),Op.GTE,
					  testFactory.constant(STRING_EXPR_1))
				.then(testFactory.constant(STRING_EXPR_B))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " || 10 >= '" + STRING_EXPR_1 + "' " +
								"then " + "'" + STRING_EXPR_B + "' end ",caseExprNotNullGE);
		
		/* Numeric Column Tests */
		/* CASE WHEN DFLTINTEGER <> 100000 THEN 200000 END */
		String caseExprDfltIntNE = testFactory.caseWhen()
				.when(colDfltInteger,Op.NOTEQ,testFactory.constant(ONE_HUNDRED_THOUSAND))
				.then(testFactory.constant(TWO_HUNDRED_THOUSAND))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_DFLTINTEGER + " <> " + ONE_HUNDRED_THOUSAND +
								" then " + TWO_HUNDRED_THOUSAND + " end ",caseExprDfltIntNE);

		/* CASE WHEN DFLTINTEGER + 10 <= 100010 THEN 200010 + 10 END */
		String caseExprDfltIntLE = testFactory.caseWhen()
				.when(testFactory.mathExpression(colDfltInteger)
								.add(testFactory.constant(10)),Op.LTE,testFactory.constant(ONE_HUNDRED_THOUSAND + 10))
				.then(testFactory.constant(TWO_HUNDRED_THOUSAND + 10))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_DFLTINTEGER + " + 10 <= 100010" +
								" then 200010" + " end ",caseExprDfltIntLE);
		
		/* String Expressions */
		/* CASE WHEN 'Abc' || 'Def' = 'AbcDef' THEN 'True' END */
		String caseWhenStrConcat = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant(STRING_EXPR_ABC))
								.concat(STRING_EXPR_DEF),Op.EQ,testFactory.constant("AbcDef"))
				.then(testFactory.constant("True"))
				.end().serialize();
		Assertions.assertEquals(" case when " + "'Abc' || 'Def' = 'AbcDef'" +
								" then 'True' end ",caseWhenStrConcat);
		
		/* CASE WHEN '10' || 10 = 1010 THEN 1010 END */
		String caseWhenStrConcat10 = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant("10"))
								.concat(testFactory.constant(10)),Op.EQ,testFactory.constant(1010))
				.then(testFactory.constant(1010))
				.end().serialize();
		Assertions.assertEquals(" case when " + "'10' || 10 = 1010" +
								" then 1010 end ",caseWhenStrConcat10);
		
		/* CASE WHEN 'Abc' || 'Def' > 'AbcDef' THEN 'True' ELSE 'False' END */
		String caseWhenStrConcatAbc = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant(STRING_EXPR_ABC))
															.concat(testFactory.constant(STRING_EXPR_DEF)),Op.GT,
					  testFactory.constant("AbcDef"))
				.then(testFactory.constant("True"))
				.elseClause(testFactory.constant("False"))			
				.end().serialize();
		Assertions.assertEquals(" case when '" + STRING_EXPR_ABC + "' || '" + STRING_EXPR_DEF + "' > 'AbcDef'" +
								" then 'True' else 'False' end ",caseWhenStrConcatAbc);
		
		/* CASE WHEN '10' || 10 <= 1010 THEN 1010 ELSE 'False' END */
		String caseWhenStrConcatNbr = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant("10")).concat(testFactory.constant(10)),Op.LTE,
					  testFactory.constant(1010))
				.then(testFactory.constant(1010))
				.elseClause(testFactory.constant("False"))			
				.end().serialize();
		Assertions.assertEquals(" case when '10' || 10 <= 1010"  +
								" then 1010 else 'False' end ",caseWhenStrConcatNbr);
		
		/* Number Expressions */
		/* CASE WHEN 10 = 20 THEN 20 END */
		String caseWhenNbr = testFactory.caseWhen()
				.when(testFactory.constant(10),Op.EQ,testFactory.constant(20))
				.then(testFactory.constant(20))
				.end().serialize();
		Assertions.assertEquals(" case when 10 = 20"  +
								" then 20 end ",caseWhenNbr);
		
		/* CASE WHEN 10 + 10 = 20 THEN 20 END */
		String caseWhenNbrAdd = testFactory.caseWhen()
				.when(testFactory.mathExpression(testFactory.constant(10)).add(testFactory.constant(10)),Op.EQ,
					  testFactory.constant(20))
				.then(testFactory.constant(20))
				.end().serialize();
		Assertions.assertEquals(" case when 10 + 10 = 20"  +
								" then 20 end ",caseWhenNbrAdd);
		
	/* CASE WHEN 10 + 10 > 30 THEN 20 ELSE -1 END */
		String caseWhenNbrAdd2 = testFactory.caseWhen()
				.when(testFactory.mathExpression(testFactory.constant(10)).add(testFactory.constant(10)),Op.GT,
					  testFactory.constant(30))
				.then(testFactory.constant(20))
				.elseClause(testFactory.constant(-1))
				.end().serialize();
		Assertions.assertEquals(" case when 10 + 10 > 30"  +
								" then 20 else -1 end ",caseWhenNbrAdd2);

	}

	@Test
	void testThen() {
		
		/* Parameter Marker Expressions */
		/* SELECT CASE WHEN ? = 'ab' THEN ? END */
		String caseExprParm = testFactory.caseWhen()
				.when(testFactory.parm(Types.CHAR, "Def"),Op.EQ,testFactory.constant("ab"))
				.then(testFactory.parm(Types.CHAR, "Def"))
				.end().serialize();
		Assertions.assertEquals(" case when ? = 'ab' then ? end ",caseExprParm);
		
		/* SELECT CASE WHEN ? / 100000 = 0 THEN ? / 100000  END */
		String caseExprParmDiv = testFactory.caseWhen()
				.when(testFactory.mathExpression(testFactory.parm(Types.INTEGER, 100000)).divide(100000),Op.EQ,testFactory.constant(0))
				.then(testFactory.mathExpression(testFactory.parm(Types.INTEGER, 100000)).divide(100000))
				.end().serialize();
		Assertions.assertEquals(" case when ? / 100000 = 0 then ? / 100000 end ",caseExprParmDiv);
		
		/* SELECT CASE WHEN ? || 'Def' = 'AbcDef' THEN ? || 'Xyz'  END */
		String caseExprParmConcat = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.parm(Types.VARCHAR, "Abc")).concat("Def"),Op.EQ,testFactory.constant("AbcDef"))
				.then(testFactory.stringExpression(testFactory.parm(Types.VARCHAR, "Abc")).concat("Xyz"))
				.end().serialize();
		Assertions.assertEquals(" case when ? || 'Def' = 'AbcDef'"+ 
								" then ? || 'Xyz' end ",caseExprParmConcat);

		/* Column Expressions */
		/* CASE WHEN NOTNULLCHAR = '0123456789' THEN NOTNULLCHAR END */
		String caseThenColExpr = testFactory.caseWhen()
				.when(colNotNullChar,Op.EQ,testFactory.constant(STRING_EXPR))
				.then(colNotNullChar)
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + "'" +
								" then " + COL_NOTNULLCHAR + " end ",caseThenColExpr);
		
		/* CASE WHEN DFLTINTEGER > 100000 THEN DFLTINTEGER END */
		String caseThenDfltIntColExpr = testFactory.caseWhen()
				.when(colDfltInteger,Op.GT,testFactory.constant(ONE_HUNDRED_THOUSAND))
				.then(colDfltInteger)
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_DFLTINTEGER + " > " + ONE_HUNDRED_THOUSAND + 
								" then " + COL_DFLTINTEGER + " end ",caseThenDfltIntColExpr);
		
		/* CASE WHEN DFLTINTEGER < 100000 THEN DFLTINTEGER - 10 END */
		String caseThenDfltIntColSub = testFactory.caseWhen()
				.when(colDfltInteger,Op.LT,testFactory.constant(ONE_HUNDRED_THOUSAND))
				.then(testFactory.mathExpression(colDfltInteger).subtract(10))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_DFLTINTEGER + " < " + ONE_HUNDRED_THOUSAND + 
								" then " + COL_DFLTINTEGER + " - 10 end ",caseThenDfltIntColSub);

		/* String Expressions */
		/* CASE WHEN NOTNULLCHAR || 10 >= '112345678910' THEN 'B123456789' || 10 END */
		String caseThenStringExpr = testFactory.caseWhen()
				.when(testFactory.stringExpression(colNotNullChar).concat(testFactory.constant(10)),Op.GTE,testFactory.constant(STRING_EXPR_1))
				.then(testFactory.stringExpression(testFactory.constant(STRING_EXPR_B)).concat(testFactory.constant(10)))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " || 10 >= '" +  STRING_EXPR_1 + "'" +
								" then '" + STRING_EXPR_B + "' || 10 end ",caseThenStringExpr);
		
		/* CASE WHEN 'Abc' || 'Def' = 'AbcDef' THEN 'True' || ' That!" END */
		String caseThenConcat = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant("Abc"))
								.concat("Def"),Op.EQ,testFactory.constant("AbcDef"))
				.then(testFactory.stringExpression(testFactory.constant("True")).concat(" That!"))
				.end().serialize();
		Assertions.assertEquals(" case when 'Abc' || 'Def' = 'AbcDef'" + 
								" then 'True' || ' That!' end ",caseThenConcat);
			
		/* Number Expressions */
		/* CASE WHEN 200000 <> 100000 THEN 200000 END */
		String caseThenNumbExpr = testFactory.caseWhen()
				.when(testFactory.constant(TWO_HUNDRED_THOUSAND),Op.NOTEQ,testFactory.constant(ONE_HUNDRED_THOUSAND))
				.then(testFactory.constant(TWO_HUNDRED_THOUSAND))
				.end().serialize();
		Assertions.assertEquals(" case when " + TWO_HUNDRED_THOUSAND + " <> " + ONE_HUNDRED_THOUSAND + 
								" then " + TWO_HUNDRED_THOUSAND + " end ",caseThenNumbExpr);

		/* CASE WHEN 200000 * 10 <= 200010 THEN 200000 * 10 END */
		String caseThenAddExpr = testFactory.caseWhen()
				.when(testFactory.mathExpression(testFactory.constant(TWO_HUNDRED_THOUSAND)).multiply(testFactory.constant(10)),Op.LTE,
					  testFactory.constant(TWO_HUNDRED_THOUSAND + 10))
				.then(testFactory.mathExpression(testFactory.constant(TWO_HUNDRED_THOUSAND)).multiply(testFactory.constant(10)))
				.end().serialize();
		Assertions.assertEquals(" case when " + TWO_HUNDRED_THOUSAND + " * 10 <= " + 200010 + 
								" then " + TWO_HUNDRED_THOUSAND + " * 10 end ",caseThenAddExpr);
		
	}

	@Test
	void testElseClause() {
		
		/* Parameter Marker Expressions */
		/* CASE WHEN ? = 'ab' THEN NOTNULLCHAR || 'ab' */ 
		
		/* SELECT CASE WHEN ? = 'ab' THEN ? ELSE ? END */
		String caseExprParm = testFactory.caseWhen()
				.when(testFactory.parm(Types.CHAR, "Def"),Op.EQ,testFactory.constant("ab"))
				.then(testFactory.parm(Types.CHAR, "Def"))
				.elseClause(testFactory.parm(Types.CHAR, "Def"))
				.end().serialize();
		Assertions.assertEquals(" case when ? = 'ab' then ? else ? end ",caseExprParm);
		
		/* SELECT CASE WHEN ? / 100000 = 0 THEN ? / 100000 ELSE ? / 200000  END */
		String caseExprParmDiv = testFactory.caseWhen()
				.when(testFactory.mathExpression(testFactory.parm(Types.INTEGER, 100000)).divide(100000),Op.EQ,testFactory.constant(0))
				.then(testFactory.mathExpression(testFactory.parm(Types.INTEGER, 100000)).divide(100000))
				.elseClause(testFactory.mathExpression(testFactory.parm(Types.INTEGER, 200000)).divide(200000))
				.end().serialize();
		Assertions.assertEquals(" case when ? / 100000 = 0 then ? / 100000 else ? / 200000 end ",caseExprParmDiv);
		
		/* SELECT CASE WHEN ? || 'Def' = 'AbcDef' THEN ? || 'Xyz' ELSE ? || 'Xyz'  END */
		String caseExprParmConcat = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.parm(Types.VARCHAR, "Abc")).concat("Def"),Op.EQ,testFactory.constant("AbcDef"))
				.then(testFactory.stringExpression(testFactory.parm(Types.VARCHAR, "Abc")).concat("Xyz"))
				.elseClause(testFactory.stringExpression(testFactory.parm(Types.VARCHAR, "Abc")).concat("Xyz"))
				.end().serialize();
		Assertions.assertEquals(" case when ? || 'Def' = 'AbcDef'"+ 
								" then ? || 'Xyz'" + 
								" else ? || 'Xyz' end ",caseExprParmConcat);
		
		/* Column Expressions - same conditions are repeated for THEN and ELSE as well */
		/* Not sure if parameter markers are fully supported in CASE expressions, but for now at least test 
		 * the syntax is generated correctly.
		 */
		
		/* CASE WHEN NOTNULLCHAR = '012345678' THEN NOTNULLCHAR || 'ab' ELSE NOTNULLCHAR END */
		String caseExprCol = testFactory.caseWhen()
				.when(testFactory.column(COL_NOTNULLCHAR),Op.EQ,testFactory.constant(STRING_EXPR))
				.then(testFactory.stringExpression(colNotNullChar).concat("ab"))
				.elseClause(colNotNullChar)
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + 
								"' then " + COL_NOTNULLCHAR + " || 'ab' " +
								"else " + COL_NOTNULLCHAR + " end ",caseExprCol);		

		/* CASE WHEN NOTNULLCHAR || 'Abc' = NOTNULLCHAR || 'Abc' THEN NOTNULLCHAR || 'Abc' 
		 * ELSE NOTNULLCHAR || 'Abc' END */
		String caseExprNotNullChar = testFactory.caseWhen()
				.when( testFactory.stringExpression(testFactory.column(COL_NOTNULLCHAR)).concat(STRING_EXPR_ABC),Op.EQ,
					  testFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC) )
				.then(testFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
				.elseClause(testFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "' = " + 
								COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "'" +
								" then " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + 
								"' else " + COL_NOTNULLCHAR + " || 'Abc' end ",caseExprNotNullChar);		

		/* CASE WHEN NOTNULLCHAR = '0123456789' THEN 'A123456789' ELSE 'B123456789' END */
		String caseExprNotNullChar2 = testFactory.caseWhen()
				.when(testFactory.column(COL_NOTNULLCHAR),Op.EQ,testFactory.constant(STRING_EXPR))
				.then(testFactory.constant(STRING_EXPR_A))
				.elseClause(testFactory.constant(STRING_EXPR_B))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + 
								"' then " + "'" + STRING_EXPR_A + 
								"' else '" + STRING_EXPR_B + "' end ",caseExprNotNullChar2);
		
		/* CASE WHEN NOTNULLCHAR || 10 >= '112345678910' THEN 'B123456789' ELSE NOTNULLCHAR || 'B23456789' END */
		String caseExprNotNullGE = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.column(COL_NOTNULLCHAR))
								.concat(testFactory.constant(10)),Op.GTE,
					  testFactory.constant(STRING_EXPR_1))
				.then(testFactory.constant(STRING_EXPR_B))
				.elseClause(testFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_B))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_NOTNULLCHAR + " || 10 >= '" + STRING_EXPR_1 + "' " +
								"then " + "'" + STRING_EXPR_B + "' " +
								"else " + COL_NOTNULLCHAR + " || 'B123456789' end ",caseExprNotNullGE);
				
		/* Numeric Column Tests */		
		/* CASE WHEN DFLTINTEGER <> 100000 THEN 200000 ELSE DFLTINTEGER END */
		String caseExprDfltIntNE = testFactory.caseWhen()
				.when(colDfltInteger,Op.NOTEQ,testFactory.constant(ONE_HUNDRED_THOUSAND))
				.then(testFactory.constant(TWO_HUNDRED_THOUSAND))
				.elseClause(colDfltInteger)
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_DFLTINTEGER + " <> " + ONE_HUNDRED_THOUSAND +
								" then " + TWO_HUNDRED_THOUSAND + 
								" else " + COL_DFLTINTEGER + " end ",caseExprDfltIntNE);
	
		/* CASE WHEN DFLTINTEGER + 10 >=  10 THEN 200000 ELSE DFLTINTEGER - 10  END */
		String caseExprDfltIntGE = testFactory.caseWhen()
				.when(testFactory.mathExpression(testFactory.column(COL_DFLTINTEGER)).add(testFactory.constant(10)),Op.GTE,
					  testFactory.constant(10))
				.then(testFactory.constant(TWO_HUNDRED_THOUSAND))
				.elseClause(testFactory.mathExpression(colDfltInteger).subtract(10))
				.end().serialize();
		Assertions.assertEquals(" case when " + COL_DFLTINTEGER + " + 10 >= 10" +
								" then " + TWO_HUNDRED_THOUSAND + 
								" else " + COL_DFLTINTEGER + " - 10 end ",caseExprDfltIntGE);
		
		/* String Expressions */
		/* CASE WHEN 'Abc' || 'Def' = 'AbcDef' THEN 'True' ELSE 'It's' || 'False' END */
		String caseWhenStrConcat = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant(STRING_EXPR_ABC))
								.concat(STRING_EXPR_DEF),Op.EQ,testFactory.constant("AbcDef"))
				.then(testFactory.constant("True"))
				.elseClause(testFactory.stringExpression("It's").concat("False"))
				.end().serialize();
		Assertions.assertEquals(" case when " + "'Abc' || 'Def' = 'AbcDef'" +
								" then 'True'" +
								" else 'It\'s' || 'False' end ",caseWhenStrConcat);
		
		/* CASE WHEN '10' || 10 = 1010 THEN 1010 ELSE '10' || 10 END */
		String caseWhenStrConcat10 = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant("10"))
								.concat(testFactory.constant(10)),Op.EQ,testFactory.constant(1010))
				.then(testFactory.constant(1010))
				.elseClause(testFactory.stringExpression(testFactory.constant("10")).concat(testFactory.constant(10)))
				.end().serialize();
		Assertions.assertEquals(" case when " + "'10' || 10 = 1010" +
								" then 1010" + 
								" else '10' || 10 end ",caseWhenStrConcat10);
		
		/* CASE WHEN 'Abc' || 'Def' > 'AbcDef' THEN 'True' ELSE 'False' END */
		String caseWhenStrConcatAbc = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant(STRING_EXPR_ABC)).concat(testFactory.constant(STRING_EXPR_DEF)),Op.GT,
					  testFactory.constant("AbcDef"))
				.then(testFactory.constant("True"))
				.elseClause(testFactory.constant("False"))			
				.end().serialize();
		Assertions.assertEquals(" case when '" + STRING_EXPR_ABC + "' || '" + STRING_EXPR_DEF + "' > 'AbcDef'" +
								" then 'True' else 'False' end ",caseWhenStrConcatAbc);
		
		/* CASE WHEN '10' || 10 <= 1010 THEN 1010 ELSE 'False' END */
		String caseWhenStrConcatNbr = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant("10")).concat(testFactory.constant(10)),Op.LTE,
					  testFactory.constant(1010))
				.then(testFactory.constant(1010))
				.elseClause(testFactory.constant("False"))			
				.end().serialize();
		Assertions.assertEquals(" case when '10' || 10 <= 1010"  +
								" then 1010 else 'False' end ",caseWhenStrConcatNbr);
		
		/* Number Expressions */
		/* CASE WHEN 10 = 20 THEN 20 ELSE 30 END */
		String caseWhenNbr = testFactory.caseWhen()
				.when(testFactory.stringExpression(testFactory.constant(10)),Op.EQ,testFactory.constant(20))
				.then(testFactory.constant(20))
				.elseClause(testFactory.constant(30))
				.end().serialize();
		Assertions.assertEquals(" case when 10 = 20"  +
								" then 20" +
								" else 30 end ",caseWhenNbr);
		
	/* CASE WHEN 10 + 10 > 30 THEN 20 ELSE 20 + 10 END */
		String caseWhenNbrAdd2 = testFactory.caseWhen()
				.when(testFactory.mathExpression(10).add(testFactory.constant(10)),Op.GT,
					  testFactory.constant(30))
				.then(testFactory.constant(20))
				.elseClause(testFactory.mathExpression(testFactory.constant(20)).add(10))
				.end().serialize();
		Assertions.assertEquals(" case when 10 + 10 > 30"  +
								" then 20" + 
								" else 20 + 10 end ",caseWhenNbrAdd2);

	}

}
