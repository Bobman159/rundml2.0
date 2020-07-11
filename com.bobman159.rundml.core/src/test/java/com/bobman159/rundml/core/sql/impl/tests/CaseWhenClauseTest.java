package com.bobman159.rundml.core.sql.impl.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.sql.BaseSQLSerializer;
import com.bobman159.rundml.core.sql.ICaseClause;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.types.impl.Column;

class CaseWhenClauseTest {
		
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
		private final BaseSQLSerializer serializer = new BaseSQLSerializer();

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

//		@Test
//		void testcaseClauseExpression() {
//			
//			CaseClause colCase = serializer.serialize(SQLTypeFactory.caseClause();
//			Assertions.assertNotNull(colCase);
//			Assertions.assertTrue(colCase.iscaseClause());
//			
//		}

//		@Test
//		void testWhenNumberOpNumber() {
//			fail("Not yet implemented");
//		}

		@Test
		void testWhenIExpressionOpIExpression() {
			
			/* Parameter Marker Expressions */
			/* CASE WHEN ? = 'ab' THEN NOTNULLCHAR || 'ab' */ 
			
			/* SELECT CASE WHEN ? = 'ab' THEN ? END */
			String caseExprParm = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.parm(Types.CHAR, "Def"),Op.EQ,SQLTypeFactory.constant("ab"))
					.then(SQLTypeFactory.parm(Types.CHAR, "Def"))
					.end());
			Assertions.assertEquals("case when ? = 'ab' then ? end",caseExprParm);
			
			/* SELECT CASE WHEN ? / 100000 = 0 THEN ? / 100000  END */
			String caseExprParmDiv = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.mathExpression(SQLTypeFactory.parm(Types.INTEGER, 100000))
									.divide(100000),Op.EQ,SQLTypeFactory.constant(0))
					.then(SQLTypeFactory.mathExpression(SQLTypeFactory.parm(Types.INTEGER, 100000)).divide(100000))
					.end());
			Assertions.assertEquals("case when ? / 100000 = 0 then ? / 100000 end",caseExprParmDiv);
			
			/* SELECT CASE WHEN ? || 'Def' = 'AbcDef' THEN ? || 'Xyz'  END */
			String caseExprParmConcat = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.parm(Types.VARCHAR, "Abc"))
									.concat("Def"),Op.EQ,SQLTypeFactory.constant("AbcDef"))
					.then(SQLTypeFactory.stringExpression(SQLTypeFactory.parm(Types.VARCHAR, "Abc")).concat("Xyz"))
					.end());
			Assertions.assertEquals("case when ? || 'Def' = 'AbcDef'"+ 
									" then ? || 'Xyz' end",caseExprParmConcat);
			
			/* Column Expressions - same conditions are repeated for THEN and ELSE as well */
			/* Not sure if parameter markers are fully supported in CASE expressions, but for now at least test 
			 * the syntax is generated correctly.
			 */
			
			/* CASE WHEN NOTNULLCHAR = '012345678' THEN NOTNULLCHAR || 'ab' END */
			String caseExprCol = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.column(COL_NOTNULLCHAR),Op.EQ,SQLTypeFactory.constant(STRING_EXPR))
					.then(SQLTypeFactory.stringExpression(colNotNullChar).concat("ab"))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + "' then " + 
									COL_NOTNULLCHAR + " || 'ab' end",caseExprCol);		

			/* CASE WHEN NOTNULLCHAR || 'Abc' = NOTNULLCHAR || 'Abc' THEN NOTNULLCHAR || 'Abc' END */
			String caseExprNotNullChar = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.column(COL_NOTNULLCHAR)).concat(STRING_EXPR_ABC),Op.EQ,
						  SQLTypeFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.then(SQLTypeFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "' = " + 
									COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "'" +
									" then " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "' end",caseExprNotNullChar);		

			/* CASE WHEN NOTNULLCHAR = '0123456789' THEN 'A123456789' END */
			String caseExprNotNullChar2 = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.column(COL_NOTNULLCHAR),Op.EQ,SQLTypeFactory.constant(STRING_EXPR))
					.then(SQLTypeFactory.constant(STRING_EXPR_A))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + "' then " + 
									"'" + STRING_EXPR_A +"' end",caseExprNotNullChar2);
			
			/* CASE WHEN NOTNULLCHAR || 10 >= '112345678910' THEN 'B123456789' END */
			String caseExprNotNullGE = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.column(COL_NOTNULLCHAR))
																.concat(SQLTypeFactory.constant(10)),Op.GTE,
						  SQLTypeFactory.constant(STRING_EXPR_1))
					.then(SQLTypeFactory.constant(STRING_EXPR_B))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || 10 >= '" + STRING_EXPR_1 + "' " +
									"then " + "'" + STRING_EXPR_B + "' end",caseExprNotNullGE);
			
			/* Numeric Column Tests */
			/* CASE WHEN DFLTINTEGER <> 100000 THEN 200000 END */
			String caseExprDfltIntNE = serializer.serialize(SQLTypeFactory.caseClause()
					.when(colDfltInteger,Op.NOTEQ,SQLTypeFactory.constant(ONE_HUNDRED_THOUSAND))
					.then(SQLTypeFactory.constant(TWO_HUNDRED_THOUSAND))
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " <> " + ONE_HUNDRED_THOUSAND +
									" then " + TWO_HUNDRED_THOUSAND + " end",caseExprDfltIntNE);

			/* CASE WHEN DFLTINTEGER + 10 <= 100010 THEN 200010 + 10 END */
			String caseExprDfltIntLE = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.mathExpression(colDfltInteger)
									.add(SQLTypeFactory.constant(10)),Op.LTE,SQLTypeFactory.constant(ONE_HUNDRED_THOUSAND + 10))
					.then(SQLTypeFactory.constant(TWO_HUNDRED_THOUSAND + 10))
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " + 10 <= 100010" +
									" then 200010" + " end",caseExprDfltIntLE);
			
			/* String Expressions */
			/* CASE WHEN 'Abc' || 'Def' = 'AbcDef' THEN 'True' END */
			String caseClauseStrConcat = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant(STRING_EXPR_ABC))
									.concat(STRING_EXPR_DEF),Op.EQ,SQLTypeFactory.constant("AbcDef"))
					.then(SQLTypeFactory.constant("True"))
					.end());
			Assertions.assertEquals("case when " + "'Abc' || 'Def' = 'AbcDef'" +
									" then 'True' end",caseClauseStrConcat);
			
			/* CASE WHEN '10' || 10 = 1010 THEN 1010 END */
			String caseClauseStrConcat10 = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("10"))
									.concat(SQLTypeFactory.constant(10)),Op.EQ,SQLTypeFactory.constant(1010))
					.then(SQLTypeFactory.constant(1010))
					.end());
			Assertions.assertEquals("case when " + "'10' || 10 = 1010" +
									" then 1010 end",caseClauseStrConcat10);
			
			/* CASE WHEN 'Abc' || 'Def' > 'AbcDef' THEN 'True' ELSE 'False' END */
			String caseClauseStrConcatAbc = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant(STRING_EXPR_ABC))
																.concat(SQLTypeFactory.constant(STRING_EXPR_DEF)),Op.GT,
						  SQLTypeFactory.constant("AbcDef"))
					.then(SQLTypeFactory.constant("True"))
					.elseClause(SQLTypeFactory.constant("False"))			
					.end());
			Assertions.assertEquals("case when '" + STRING_EXPR_ABC + "' || '" + STRING_EXPR_DEF + "' > 'AbcDef'" +
									" then 'True' else 'False' end",caseClauseStrConcatAbc);
			
			/* CASE WHEN '10' || 10 <= 1010 THEN 1010 ELSE 'False' END */
			String caseClauseStrConcatNbr = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("10")).concat(SQLTypeFactory.constant(10)),Op.LTE,
						  SQLTypeFactory.constant(1010))
					.then(SQLTypeFactory.constant(1010))
					.elseClause(SQLTypeFactory.constant("False"))			
					.end());
			Assertions.assertEquals("case when '10' || 10 <= 1010"  +
									" then 1010 else 'False' end",caseClauseStrConcatNbr);
			
			/* Number Expressions */
			/* CASE WHEN 10 = 20 THEN 20 END */
			String caseClauseNbr = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.constant(10),Op.EQ,SQLTypeFactory.constant(20))
					.then(SQLTypeFactory.constant(20))
					.end());
			Assertions.assertEquals("case when 10 = 20"  +
									" then 20 end",caseClauseNbr);
			
			/* CASE WHEN 10 + 10 = 20 THEN 20 END */
			String caseClauseNbrAdd = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.mathExpression(SQLTypeFactory.constant(10)).add(SQLTypeFactory.constant(10)),Op.EQ,
						  SQLTypeFactory.constant(20))
					.then(SQLTypeFactory.constant(20))
					.end());
			Assertions.assertEquals("case when 10 + 10 = 20"  +
									" then 20 end",caseClauseNbrAdd);
			
		/* CASE WHEN 10 + 10 > 30 THEN 20 ELSE -1 END */
			String caseClauseNbrAdd2 = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.mathExpression(SQLTypeFactory.constant(10)).add(SQLTypeFactory.constant(10)),Op.GT,
						  SQLTypeFactory.constant(30))
					.then(SQLTypeFactory.constant(20))
					.elseClause(SQLTypeFactory.constant(-1))
					.end());
			Assertions.assertEquals("case when 10 + 10 > 30"  +
									" then 20 else -1 end",caseClauseNbrAdd2);

		}

		@Test
		void testThen() {
			
			/* Parameter Marker Expressions */
			/* SELECT CASE WHEN ? = 'ab' THEN ? END */
			String caseExprParm = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.parm(Types.CHAR, "Def"),Op.EQ,SQLTypeFactory.constant("ab"))
					.then(SQLTypeFactory.parm(Types.CHAR, "Def"))
					.end());
			Assertions.assertEquals("case when ? = 'ab' then ? end",caseExprParm);
			
			/* SELECT CASE WHEN ? / 100000 = 0 THEN ? / 100000  END */
			String caseExprParmDiv = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.mathExpression(SQLTypeFactory.parm(Types.INTEGER, 100000)).divide(100000),Op.EQ,SQLTypeFactory.constant(0))
					.then(SQLTypeFactory.mathExpression(SQLTypeFactory.parm(Types.INTEGER, 100000)).divide(100000))
					.end());
			Assertions.assertEquals("case when ? / 100000 = 0 then ? / 100000 end",caseExprParmDiv);
			
			/* SELECT CASE WHEN ? || 'Def' = 'AbcDef' THEN ? || 'Xyz'  END */
			String caseExprParmConcat = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.parm(Types.VARCHAR, "Abc")).concat("Def"),Op.EQ,SQLTypeFactory.constant("AbcDef"))
					.then(SQLTypeFactory.stringExpression(SQLTypeFactory.parm(Types.VARCHAR, "Abc")).concat("Xyz"))
					.end());
			Assertions.assertEquals("case when ? || 'Def' = 'AbcDef'"+ 
									" then ? || 'Xyz' end",caseExprParmConcat);

			/* Column Expressions */
			/* CASE WHEN NOTNULLCHAR = '0123456789' THEN NOTNULLCHAR END */
			String caseThenColExpr = serializer.serialize(SQLTypeFactory.caseClause()
					.when(colNotNullChar,Op.EQ,SQLTypeFactory.constant(STRING_EXPR))
					.then(colNotNullChar)
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + "'" +
									" then " + COL_NOTNULLCHAR + " end",caseThenColExpr);
			
			/* CASE WHEN DFLTINTEGER > 100000 THEN DFLTINTEGER END */
			String caseThenDfltIntColExpr = serializer.serialize(SQLTypeFactory.caseClause()
					.when(colDfltInteger,Op.GT,SQLTypeFactory.constant(ONE_HUNDRED_THOUSAND))
					.then(colDfltInteger)
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " > " + ONE_HUNDRED_THOUSAND + 
									" then " + COL_DFLTINTEGER + " end",caseThenDfltIntColExpr);
			
			/* CASE WHEN DFLTINTEGER < 100000 THEN DFLTINTEGER - 10 END */
			String caseThenDfltIntColSub = serializer.serialize(SQLTypeFactory.caseClause()
					.when(colDfltInteger,Op.LT,SQLTypeFactory.constant(ONE_HUNDRED_THOUSAND))
					.then(SQLTypeFactory.mathExpression(colDfltInteger).subtract(10))
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " < " + ONE_HUNDRED_THOUSAND + 
									" then " + COL_DFLTINTEGER + " - 10 end",caseThenDfltIntColSub);

			/* String Expressions */
			/* CASE WHEN NOTNULLCHAR || 10 >= '112345678910' THEN 'B123456789' || 10 END */
			String caseThenStringExpr = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(colNotNullChar).concat(SQLTypeFactory.constant(10)),Op.GTE,SQLTypeFactory.constant(STRING_EXPR_1))
					.then(SQLTypeFactory.stringExpression(SQLTypeFactory.constant(STRING_EXPR_B)).concat(SQLTypeFactory.constant(10)))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || 10 >= '" +  STRING_EXPR_1 + "'" +
									" then '" + STRING_EXPR_B + "' || 10 end",caseThenStringExpr);
			
			/* CASE WHEN 'Abc' || 'Def' = 'AbcDef' THEN 'True' || ' That!" END */
			String caseThenConcat = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("Abc"))
									.concat("Def"),Op.EQ,SQLTypeFactory.constant("AbcDef"))
					.then(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("True")).concat(" That!"))
					.end());
			Assertions.assertEquals("case when 'Abc' || 'Def' = 'AbcDef'" + 
									" then 'True' || ' That!' end",caseThenConcat);
				
			/* Number Expressions */
			/* CASE WHEN 200000 <> 100000 THEN 200000 END */
			String caseThenNumbExpr = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.constant(TWO_HUNDRED_THOUSAND),Op.NOTEQ,SQLTypeFactory.constant(ONE_HUNDRED_THOUSAND))
					.then(SQLTypeFactory.constant(TWO_HUNDRED_THOUSAND))
					.end());
			Assertions.assertEquals("case when " + TWO_HUNDRED_THOUSAND + " <> " + ONE_HUNDRED_THOUSAND + 
									" then " + TWO_HUNDRED_THOUSAND + " end",caseThenNumbExpr);

			/* CASE WHEN 200000 * 10 <= 200010 THEN 200000 * 10 END */
			String caseThenAddExpr = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.mathExpression(SQLTypeFactory.constant(TWO_HUNDRED_THOUSAND)).multiply(SQLTypeFactory.constant(10)),Op.LTE,
						  SQLTypeFactory.constant(TWO_HUNDRED_THOUSAND + 10))
					.then(SQLTypeFactory.mathExpression(SQLTypeFactory.constant(TWO_HUNDRED_THOUSAND)).multiply(SQLTypeFactory.constant(10)))
					.end());
			Assertions.assertEquals("case when " + TWO_HUNDRED_THOUSAND + " * 10 <= " + 200010 + 
									" then " + TWO_HUNDRED_THOUSAND + " * 10 end",caseThenAddExpr);
			
		}

		@Test
		void testElseClause() {
			
			/* Parameter Marker Expressions */
			/* CASE WHEN ? = 'ab' THEN NOTNULLCHAR || 'ab' */ 
			
			/* SELECT CASE WHEN ? = 'ab' THEN ? ELSE ? END */
			String caseExprParm = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.parm(Types.CHAR, "Def"),Op.EQ,SQLTypeFactory.constant("ab"))
					.then(SQLTypeFactory.parm(Types.CHAR, "Def"))
					.elseClause(SQLTypeFactory.parm(Types.CHAR, "Def"))
					.end());
			Assertions.assertEquals("case when ? = 'ab' then ? else ? end",caseExprParm);
			
			/* SELECT CASE WHEN ? / 100000 = 0 THEN ? / 100000 ELSE ? / 200000  END */
			String caseExprParmDiv = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.mathExpression(SQLTypeFactory.parm(Types.INTEGER, 100000)).divide(100000),Op.EQ,SQLTypeFactory.constant(0))
					.then(SQLTypeFactory.mathExpression(SQLTypeFactory.parm(Types.INTEGER, 100000)).divide(100000))
					.elseClause(SQLTypeFactory.mathExpression(SQLTypeFactory.parm(Types.INTEGER, 200000)).divide(200000))
					.end());
			Assertions.assertEquals("case when ? / 100000 = 0 then ? / 100000 else ? / 200000 end",caseExprParmDiv);
			
			/* SELECT CASE WHEN ? || 'Def' = 'AbcDef' THEN ? || 'Xyz' ELSE ? || 'Xyz'  END */
			String caseExprParmConcat = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.parm(Types.VARCHAR, "Abc")).concat("Def"),Op.EQ,SQLTypeFactory.constant("AbcDef"))
					.then(SQLTypeFactory.stringExpression(SQLTypeFactory.parm(Types.VARCHAR, "Abc")).concat("Xyz"))
					.elseClause(SQLTypeFactory.stringExpression(SQLTypeFactory.parm(Types.VARCHAR, "Abc")).concat("Xyz"))
					.end());
			Assertions.assertEquals("case when ? || 'Def' = 'AbcDef'"+ 
									" then ? || 'Xyz'" + 
									" else ? || 'Xyz' end",caseExprParmConcat);
			
			/* Column Expressions - same conditions are repeated for THEN and ELSE as well */
			/* Not sure if parameter markers are fully supported in CASE expressions, but for now at least test 
			 * the syntax is generated correctly.
			 */
			
			/* CASE WHEN NOTNULLCHAR = '012345678' THEN NOTNULLCHAR || 'ab' ELSE NOTNULLCHAR END */
			String caseExprCol = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.column(COL_NOTNULLCHAR),Op.EQ,SQLTypeFactory.constant(STRING_EXPR))
					.then(SQLTypeFactory.stringExpression(colNotNullChar).concat("ab"))
					.elseClause(colNotNullChar)
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + 
									"' then " + COL_NOTNULLCHAR + " || 'ab' " +
									"else " + COL_NOTNULLCHAR + " end",caseExprCol);		

			/* CASE WHEN NOTNULLCHAR || 'Abc' = NOTNULLCHAR || 'Abc' THEN NOTNULLCHAR || 'Abc' 
			 * ELSE NOTNULLCHAR || 'Abc' END */
			String caseExprNotNullChar = serializer.serialize(SQLTypeFactory.caseClause()
					.when( SQLTypeFactory.stringExpression(SQLTypeFactory.column(COL_NOTNULLCHAR)).concat(STRING_EXPR_ABC),Op.EQ,
						  SQLTypeFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.then(SQLTypeFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.elseClause(SQLTypeFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "' = " + 
									COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "'" +
									" then " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + 
									"' else " + COL_NOTNULLCHAR + " || 'Abc' end",caseExprNotNullChar);		

			/* CASE WHEN NOTNULLCHAR = '0123456789' THEN 'A123456789' ELSE 'B123456789' END */
			String caseExprNotNullChar2 = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.column(COL_NOTNULLCHAR),Op.EQ,SQLTypeFactory.constant(STRING_EXPR))
					.then(SQLTypeFactory.constant(STRING_EXPR_A))
					.elseClause(SQLTypeFactory.constant(STRING_EXPR_B))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + 
									"' then " + "'" + STRING_EXPR_A + 
									"' else '" + STRING_EXPR_B + "' end",caseExprNotNullChar2);
			
			/* CASE WHEN NOTNULLCHAR || 10 >= '112345678910' THEN 'B123456789' ELSE NOTNULLCHAR || 'B23456789' END */
			String caseExprNotNullGE = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.column(COL_NOTNULLCHAR))
									.concat(SQLTypeFactory.constant(10)),Op.GTE,
						  SQLTypeFactory.constant(STRING_EXPR_1))
					.then(SQLTypeFactory.constant(STRING_EXPR_B))
					.elseClause(SQLTypeFactory.stringExpression(colNotNullChar).concat(STRING_EXPR_B))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || 10 >= '" + STRING_EXPR_1 + "' " +
									"then " + "'" + STRING_EXPR_B + "' " +
									"else " + COL_NOTNULLCHAR + " || 'B123456789' end",caseExprNotNullGE);
					
			/* Numeric Column Tests */		
			/* CASE WHEN DFLTINTEGER <> 100000 THEN 200000 ELSE DFLTINTEGER END */
			String caseExprDfltIntNE = serializer.serialize(SQLTypeFactory.caseClause()
					.when(colDfltInteger,Op.NOTEQ,SQLTypeFactory.constant(ONE_HUNDRED_THOUSAND))
					.then(SQLTypeFactory.constant(TWO_HUNDRED_THOUSAND))
					.elseClause(colDfltInteger)
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " <> " + ONE_HUNDRED_THOUSAND +
									" then " + TWO_HUNDRED_THOUSAND + 
									" else " + COL_DFLTINTEGER + " end",caseExprDfltIntNE);
		
			/* CASE WHEN DFLTINTEGER + 10 >=  10 THEN 200000 ELSE DFLTINTEGER - 10  END */
			String caseExprDfltIntGE = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.mathExpression(SQLTypeFactory.column(COL_DFLTINTEGER)).add(SQLTypeFactory.constant(10)),Op.GTE,
						  SQLTypeFactory.constant(10))
					.then(SQLTypeFactory.constant(TWO_HUNDRED_THOUSAND))
					.elseClause(SQLTypeFactory.mathExpression(colDfltInteger).subtract(10))
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " + 10 >= 10" +
									" then " + TWO_HUNDRED_THOUSAND + 
									" else " + COL_DFLTINTEGER + " - 10 end",caseExprDfltIntGE);
			
			/* String Expressions */
			/* CASE WHEN 'Abc' || 'Def' = 'AbcDef' THEN 'True' ELSE 'It's' || 'False' END */
			String caseClauseStrConcat = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant(STRING_EXPR_ABC))
									.concat(STRING_EXPR_DEF),Op.EQ,SQLTypeFactory.constant("AbcDef"))
					.then(SQLTypeFactory.constant("True"))
					.elseClause(SQLTypeFactory.stringExpression("It's").concat("False"))
					.end());
			Assertions.assertEquals("case when " + "'Abc' || 'Def' = 'AbcDef'" +
									" then 'True'" +
									" else 'It\'s' || 'False' end",caseClauseStrConcat);
			
			/* CASE WHEN '10' || 10 = 1010 THEN 1010 ELSE '10' || 10 END */
			String caseClauseStrConcat10 = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("10"))
									.concat(SQLTypeFactory.constant(10)),Op.EQ,SQLTypeFactory.constant(1010))
					.then(SQLTypeFactory.constant(1010))
					.elseClause(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("10")).concat(SQLTypeFactory.constant(10)))
					.end());
			Assertions.assertEquals("case when " + "'10' || 10 = 1010" +
									" then 1010" + 
									" else '10' || 10 end",caseClauseStrConcat10);
			
			/* CASE WHEN 'Abc' || 'Def' > 'AbcDef' THEN 'True' ELSE 'False' END */
			String caseClauseStrConcatAbc = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant(STRING_EXPR_ABC)).concat(SQLTypeFactory.constant(STRING_EXPR_DEF)),Op.GT,
						  SQLTypeFactory.constant("AbcDef"))
					.then(SQLTypeFactory.constant("True"))
					.elseClause(SQLTypeFactory.constant("False"))			
					.end());
			Assertions.assertEquals("case when '" + STRING_EXPR_ABC + "' || '" + STRING_EXPR_DEF + "' > 'AbcDef'" +
									" then 'True' else 'False' end",caseClauseStrConcatAbc);
			
			/* CASE WHEN '10' || 10 <= 1010 THEN 1010 ELSE 'False' END */
			String caseClauseStrConcatNbr = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("10")).concat(SQLTypeFactory.constant(10)),Op.LTE,
						  SQLTypeFactory.constant(1010))
					.then(SQLTypeFactory.constant(1010))
					.elseClause(SQLTypeFactory.constant("False"))			
					.end());
			Assertions.assertEquals("case when '10' || 10 <= 1010"  +
									" then 1010 else 'False' end",caseClauseStrConcatNbr);
			
			/* Number Expressions */
			/* CASE WHEN 10 = 20 THEN 20 ELSE 30 END */
			String caseClauseNbr = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.stringExpression(SQLTypeFactory.constant(10)),Op.EQ,SQLTypeFactory.constant(20))
					.then(SQLTypeFactory.constant(20))
					.elseClause(SQLTypeFactory.constant(30))
					.end());
			Assertions.assertEquals("case when 10 = 20"  +
									" then 20" +
									" else 30 end",caseClauseNbr);
			
		/* CASE WHEN 10 + 10 > 30 THEN 20 ELSE 20 + 10 END */
			String caseClauseNbrAdd2 = serializer.serialize(SQLTypeFactory.caseClause()
					.when(SQLTypeFactory.mathExpression(10).add(SQLTypeFactory.constant(10)),Op.GT,
						  SQLTypeFactory.constant(30))
					.then(SQLTypeFactory.constant(20))
					.elseClause(SQLTypeFactory.mathExpression(SQLTypeFactory.constant(20)).add(10))
					.end());
			Assertions.assertEquals("case when 10 + 10 > 30"  +
									" then 20" + 
									" else 20 + 10 end",caseClauseNbrAdd2);

		}
}
