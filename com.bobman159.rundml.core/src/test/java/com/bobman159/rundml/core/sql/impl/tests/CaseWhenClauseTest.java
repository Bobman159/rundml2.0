package com.bobman159.rundml.core.sql.impl.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.sql.serialize.impl.TestBaseSQLSerializer;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;

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
		void testWhenIExpressionOpIExpression() {
			
			/* Parameter Marker Expressions */
			/* CASE WHEN ? = 'ab' THEN NOTNULLCHAR || 'ab' */ 
			
			/* SELECT CASE WHEN ? = 'ab' THEN ? END */
			String caseExprParm = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"),Op.EQ,SQLTypeFactory.getInstance().constant("ab"))
					.then(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"))
					.end());
			Assertions.assertEquals("case when ? = 'ab' then ? end",caseExprParm);
			
			/* SELECT CASE WHEN ? / 100000 = 0 THEN ? / 100000  END */
			String caseExprParmDiv = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().parm(Types.INTEGER, 100000))
									.divide(100000),Op.EQ,SQLTypeFactory.getInstance().constant(0))
					.then(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().parm(Types.INTEGER, 100000)).divide(100000))
					.end());
			Assertions.assertEquals("case when ? / 100000 = 0 then ? / 100000 end",caseExprParmDiv);
			
			/* SELECT CASE WHEN ? || 'Def' = 'AbcDef' THEN ? || 'Xyz'  END */
			String caseExprParmConcat = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().parm(Types.VARCHAR, "Abc"))
									.concat("Def"),Op.EQ,SQLTypeFactory.getInstance().constant("AbcDef"))
					.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().parm(Types.VARCHAR, "Abc")).concat("Xyz"))
					.end());
			Assertions.assertEquals("case when ? || 'Def' = 'AbcDef'"+ 
									" then ? || 'Xyz' end",caseExprParmConcat);
			
			/* Column Expressions - same conditions are repeated for THEN and ELSE as well */
			/* Not sure if parameter markers are fully supported in CASE expressions, but for now at least test 
			 * the syntax is generated correctly.
			 */
			
			/* CASE WHEN NOTNULLCHAR = '012345678' THEN NOTNULLCHAR || 'ab' END */
			String caseExprCol = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR),Op.EQ,SQLTypeFactory.getInstance().constant(STRING_EXPR))
					.then(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat("ab"))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + "' then " + 
									COL_NOTNULLCHAR + " || 'ab' end",caseExprCol);		

			/* CASE WHEN NOTNULLCHAR || 'Abc' = NOTNULLCHAR || 'Abc' THEN NOTNULLCHAR || 'Abc' END */
			String caseExprNotNullChar = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR)).concat(STRING_EXPR_ABC),Op.EQ,
						  SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.then(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "' = " + 
									COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "'" +
									" then " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "' end",caseExprNotNullChar);		

			/* CASE WHEN NOTNULLCHAR = '0123456789' THEN 'A123456789' END */
			String caseExprNotNullChar2 = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR),Op.EQ,SQLTypeFactory.getInstance().constant(STRING_EXPR))
					.then(SQLTypeFactory.getInstance().constant(STRING_EXPR_A))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + "' then " + 
									"'" + STRING_EXPR_A +"' end",caseExprNotNullChar2);
			
			/* CASE WHEN NOTNULLCHAR || 10 >= '112345678910' THEN 'B123456789' END */
			String caseExprNotNullGE = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR))
																.concat(SQLTypeFactory.getInstance().constant(10)),Op.GTE,
						  SQLTypeFactory.getInstance().constant(STRING_EXPR_1))
					.then(SQLTypeFactory.getInstance().constant(STRING_EXPR_B))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || 10 >= '" + STRING_EXPR_1 + "' " +
									"then " + "'" + STRING_EXPR_B + "' end",caseExprNotNullGE);
			
			/* Numeric Column Tests */
			/* CASE WHEN DFLTINTEGER <> 100000 THEN 200000 END */
			String caseExprDfltIntNE = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(colDfltInteger,Op.NOTEQ,SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND))
					.then(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND))
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " <> " + ONE_HUNDRED_THOUSAND +
									" then " + TWO_HUNDRED_THOUSAND + " end",caseExprDfltIntNE);

			/* CASE WHEN DFLTINTEGER + 10 <= 100010 THEN 200010 + 10 END */
			String caseExprDfltIntLE = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().mathExpression(colDfltInteger)
									.add(SQLTypeFactory.getInstance().constant(10)),Op.LTE,SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND + 10))
					.then(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND + 10))
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " + 10 <= 100010" +
									" then 200010" + " end",caseExprDfltIntLE);
			
			/* String Expressions */
			/* CASE WHEN 'Abc' || 'Def' = 'AbcDef' THEN 'True' END */
			String caseClauseStrConcat = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC))
									.concat(STRING_EXPR_DEF),Op.EQ,SQLTypeFactory.getInstance().constant("AbcDef"))
					.then(SQLTypeFactory.getInstance().constant("True"))
					.end());
			Assertions.assertEquals("case when " + "'Abc' || 'Def' = 'AbcDef'" +
									" then 'True' end",caseClauseStrConcat);
			
			/* CASE WHEN '10' || 10 = 1010 THEN 1010 END */
			String caseClauseStrConcat10 = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("10"))
									.concat(SQLTypeFactory.getInstance().constant(10)),Op.EQ,SQLTypeFactory.getInstance().constant(1010))
					.then(SQLTypeFactory.getInstance().constant(1010))
					.end());
			Assertions.assertEquals("case when " + "'10' || 10 = 1010" +
									" then 1010 end",caseClauseStrConcat10);
			
			/* CASE WHEN 'Abc' || 'Def' > 'AbcDef' THEN 'True' ELSE 'False' END */
			String caseClauseStrConcatAbc = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC))
																.concat(SQLTypeFactory.getInstance().constant(STRING_EXPR_DEF)),Op.GT,
						  SQLTypeFactory.getInstance().constant("AbcDef"))
					.then(SQLTypeFactory.getInstance().constant("True"))
					.elseClause(SQLTypeFactory.getInstance().constant("False"))			
					.end());
			Assertions.assertEquals("case when '" + STRING_EXPR_ABC + "' || '" + STRING_EXPR_DEF + "' > 'AbcDef'" +
									" then 'True' else 'False' end",caseClauseStrConcatAbc);
			
			/* CASE WHEN '10' || 10 <= 1010 THEN 1010 ELSE 'False' END */
			String caseClauseStrConcatNbr = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("10")).concat(SQLTypeFactory.getInstance().constant(10)),Op.LTE,
						  SQLTypeFactory.getInstance().constant(1010))
					.then(SQLTypeFactory.getInstance().constant(1010))
					.elseClause(SQLTypeFactory.getInstance().constant("False"))			
					.end());
			Assertions.assertEquals("case when '10' || 10 <= 1010"  +
									" then 1010 else 'False' end",caseClauseStrConcatNbr);
			
			/* Number Expressions */
			/* CASE WHEN 10 = 20 THEN 20 END */
			String caseClauseNbr = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().constant(10),Op.EQ,SQLTypeFactory.getInstance().constant(20))
					.then(SQLTypeFactory.getInstance().constant(20))
					.end());
			Assertions.assertEquals("case when 10 = 20"  +
									" then 20 end",caseClauseNbr);
			
			/* CASE WHEN 10 + 10 = 20 THEN 20 END */
			String caseClauseNbrAdd = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10)).add(SQLTypeFactory.getInstance().constant(10)),Op.EQ,
						  SQLTypeFactory.getInstance().constant(20))
					.then(SQLTypeFactory.getInstance().constant(20))
					.end());
			Assertions.assertEquals("case when 10 + 10 = 20"  +
									" then 20 end",caseClauseNbrAdd);
			
		/* CASE WHEN 10 + 10 > 30 THEN 20 ELSE -1 END */
			String caseClauseNbrAdd2 = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(10)).add(SQLTypeFactory.getInstance().constant(10)),Op.GT,
						  SQLTypeFactory.getInstance().constant(30))
					.then(SQLTypeFactory.getInstance().constant(20))
					.elseClause(SQLTypeFactory.getInstance().constant(-1))
					.end());
			Assertions.assertEquals("case when 10 + 10 > 30"  +
									" then 20 else -1 end",caseClauseNbrAdd2);

		}

		@Test
		void testThen() {
			
			/* Parameter Marker Expressions */
			/* SELECT CASE WHEN ? = 'ab' THEN ? END */
			String caseExprParm = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"),Op.EQ,SQLTypeFactory.getInstance().constant("ab"))
					.then(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"))
					.end());
			Assertions.assertEquals("case when ? = 'ab' then ? end",caseExprParm);
			
			/* SELECT CASE WHEN ? / 100000 = 0 THEN ? / 100000  END */
			String caseExprParmDiv = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().parm(Types.INTEGER, 100000)).divide(100000),Op.EQ,SQLTypeFactory.getInstance().constant(0))
					.then(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().parm(Types.INTEGER, 100000)).divide(100000))
					.end());
			Assertions.assertEquals("case when ? / 100000 = 0 then ? / 100000 end",caseExprParmDiv);
			
			/* SELECT CASE WHEN ? || 'Def' = 'AbcDef' THEN ? || 'Xyz'  END */
			String caseExprParmConcat = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().parm(Types.VARCHAR, "Abc")).concat("Def"),Op.EQ,SQLTypeFactory.getInstance().constant("AbcDef"))
					.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().parm(Types.VARCHAR, "Abc")).concat("Xyz"))
					.end());
			Assertions.assertEquals("case when ? || 'Def' = 'AbcDef'"+ 
									" then ? || 'Xyz' end",caseExprParmConcat);

			/* Column Expressions */
			/* CASE WHEN NOTNULLCHAR = '0123456789' THEN NOTNULLCHAR END */
			String caseThenColExpr = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(colNotNullChar,Op.EQ,SQLTypeFactory.getInstance().constant(STRING_EXPR))
					.then(colNotNullChar)
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + "'" +
									" then " + COL_NOTNULLCHAR + " end",caseThenColExpr);
			
			/* CASE WHEN DFLTINTEGER > 100000 THEN DFLTINTEGER END */
			String caseThenDfltIntColExpr = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(colDfltInteger,Op.GT,SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND))
					.then(colDfltInteger)
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " > " + ONE_HUNDRED_THOUSAND + 
									" then " + COL_DFLTINTEGER + " end",caseThenDfltIntColExpr);
			
			/* CASE WHEN DFLTINTEGER < 100000 THEN DFLTINTEGER - 10 END */
			String caseThenDfltIntColSub = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(colDfltInteger,Op.LT,SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND))
					.then(SQLTypeFactory.getInstance().mathExpression(colDfltInteger).subtract(10))
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " < " + ONE_HUNDRED_THOUSAND + 
									" then " + COL_DFLTINTEGER + " - 10 end",caseThenDfltIntColSub);

			/* String Expressions */
			/* CASE WHEN NOTNULLCHAR || 10 >= '112345678910' THEN 'B123456789' || 10 END */
			String caseThenStringExpr = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(SQLTypeFactory.getInstance().constant(10)),Op.GTE,SQLTypeFactory.getInstance().constant(STRING_EXPR_1))
					.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_B)).concat(SQLTypeFactory.getInstance().constant(10)))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || 10 >= '" +  STRING_EXPR_1 + "'" +
									" then '" + STRING_EXPR_B + "' || 10 end",caseThenStringExpr);
			
			/* CASE WHEN 'Abc' || 'Def' = 'AbcDef' THEN 'True' || ' That!" END */
			String caseThenConcat = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("Abc"))
									.concat("Def"),Op.EQ,SQLTypeFactory.getInstance().constant("AbcDef"))
					.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("True")).concat(" That!"))
					.end());
			Assertions.assertEquals("case when 'Abc' || 'Def' = 'AbcDef'" + 
									" then 'True' || ' That!' end",caseThenConcat);
				
			/* Number Expressions */
			/* CASE WHEN 200000 <> 100000 THEN 200000 END */
			String caseThenNumbExpr = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND),Op.NOTEQ,SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND))
					.then(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND))
					.end());
			Assertions.assertEquals("case when " + TWO_HUNDRED_THOUSAND + " <> " + ONE_HUNDRED_THOUSAND + 
									" then " + TWO_HUNDRED_THOUSAND + " end",caseThenNumbExpr);

			/* CASE WHEN 200000 * 10 <= 200010 THEN 200000 * 10 END */
			String caseThenAddExpr = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND)).multiply(SQLTypeFactory.getInstance().constant(10)),Op.LTE,
						  SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND + 10))
					.then(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND)).multiply(SQLTypeFactory.getInstance().constant(10)))
					.end());
			Assertions.assertEquals("case when " + TWO_HUNDRED_THOUSAND + " * 10 <= " + 200010 + 
									" then " + TWO_HUNDRED_THOUSAND + " * 10 end",caseThenAddExpr);
			
		}

		@Test
		void testElseClause() {
			
			/* Parameter Marker Expressions */
			/* CASE WHEN ? = 'ab' THEN NOTNULLCHAR || 'ab' */ 
			
			/* SELECT CASE WHEN ? = 'ab' THEN ? ELSE ? END */
			String caseExprParm = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"),Op.EQ,SQLTypeFactory.getInstance().constant("ab"))
					.then(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"))
					.elseClause(SQLTypeFactory.getInstance().parm(Types.CHAR, "Def"))
					.end());
			Assertions.assertEquals("case when ? = 'ab' then ? else ? end",caseExprParm);
			
			/* SELECT CASE WHEN ? / 100000 = 0 THEN ? / 100000 ELSE ? / 200000  END */
			String caseExprParmDiv = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().parm(Types.INTEGER, 100000)).divide(100000),Op.EQ,SQLTypeFactory.getInstance().constant(0))
					.then(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().parm(Types.INTEGER, 100000)).divide(100000))
					.elseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().parm(Types.INTEGER, 200000)).divide(200000))
					.end());
			Assertions.assertEquals("case when ? / 100000 = 0 then ? / 100000 else ? / 200000 end",caseExprParmDiv);
			
			/* SELECT CASE WHEN ? || 'Def' = 'AbcDef' THEN ? || 'Xyz' ELSE ? || 'Xyz'  END */
			String caseExprParmConcat = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().parm(Types.VARCHAR, "Abc")).concat("Def"),Op.EQ,SQLTypeFactory.getInstance().constant("AbcDef"))
					.then(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().parm(Types.VARCHAR, "Abc")).concat("Xyz"))
					.elseClause(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().parm(Types.VARCHAR, "Abc")).concat("Xyz"))
					.end());
			Assertions.assertEquals("case when ? || 'Def' = 'AbcDef'"+ 
									" then ? || 'Xyz'" + 
									" else ? || 'Xyz' end",caseExprParmConcat);
			
			/* Column Expressions - same conditions are repeated for THEN and ELSE as well */
			/* Not sure if parameter markers are fully supported in CASE expressions, but for now at least test 
			 * the syntax is generated correctly.
			 */
			
			/* CASE WHEN NOTNULLCHAR = '012345678' THEN NOTNULLCHAR || 'ab' ELSE NOTNULLCHAR END */
			String caseExprCol = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR),Op.EQ,SQLTypeFactory.getInstance().constant(STRING_EXPR))
					.then(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat("ab"))
					.elseClause(colNotNullChar)
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + 
									"' then " + COL_NOTNULLCHAR + " || 'ab' " +
									"else " + COL_NOTNULLCHAR + " end",caseExprCol);		

			/* CASE WHEN NOTNULLCHAR || 'Abc' = NOTNULLCHAR || 'Abc' THEN NOTNULLCHAR || 'Abc' 
			 * ELSE NOTNULLCHAR || 'Abc' END */
			String caseExprNotNullChar = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when( SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR)).concat(STRING_EXPR_ABC),Op.EQ,
						  SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.then(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.elseClause(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(STRING_EXPR_ABC))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "' = " + 
									COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + "'" +
									" then " + COL_NOTNULLCHAR + " || '" + STRING_EXPR_ABC + 
									"' else " + COL_NOTNULLCHAR + " || 'Abc' end",caseExprNotNullChar);		

			/* CASE WHEN NOTNULLCHAR = '0123456789' THEN 'A123456789' ELSE 'B123456789' END */
			String caseExprNotNullChar2 = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR),Op.EQ,SQLTypeFactory.getInstance().constant(STRING_EXPR))
					.then(SQLTypeFactory.getInstance().constant(STRING_EXPR_A))
					.elseClause(SQLTypeFactory.getInstance().constant(STRING_EXPR_B))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " = '" + STRING_EXPR + 
									"' then " + "'" + STRING_EXPR_A + 
									"' else '" + STRING_EXPR_B + "' end",caseExprNotNullChar2);
			
			/* CASE WHEN NOTNULLCHAR || 10 >= '112345678910' THEN 'B123456789' ELSE NOTNULLCHAR || 'B23456789' END */
			String caseExprNotNullGE = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().column(COL_NOTNULLCHAR))
									.concat(SQLTypeFactory.getInstance().constant(10)),Op.GTE,
						  SQLTypeFactory.getInstance().constant(STRING_EXPR_1))
					.then(SQLTypeFactory.getInstance().constant(STRING_EXPR_B))
					.elseClause(SQLTypeFactory.getInstance().stringExpression(colNotNullChar).concat(STRING_EXPR_B))
					.end());
			Assertions.assertEquals("case when " + COL_NOTNULLCHAR + " || 10 >= '" + STRING_EXPR_1 + "' " +
									"then " + "'" + STRING_EXPR_B + "' " +
									"else " + COL_NOTNULLCHAR + " || 'B123456789' end",caseExprNotNullGE);
					
			/* Numeric Column Tests */		
			/* CASE WHEN DFLTINTEGER <> 100000 THEN 200000 ELSE DFLTINTEGER END */
			String caseExprDfltIntNE = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(colDfltInteger,Op.NOTEQ,SQLTypeFactory.getInstance().constant(ONE_HUNDRED_THOUSAND))
					.then(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND))
					.elseClause(colDfltInteger)
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " <> " + ONE_HUNDRED_THOUSAND +
									" then " + TWO_HUNDRED_THOUSAND + 
									" else " + COL_DFLTINTEGER + " end",caseExprDfltIntNE);
		
			/* CASE WHEN DFLTINTEGER + 10 >=  10 THEN 200000 ELSE DFLTINTEGER - 10  END */
			String caseExprDfltIntGE = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().column(COL_DFLTINTEGER)).add(SQLTypeFactory.getInstance().constant(10)),Op.GTE,
						  SQLTypeFactory.getInstance().constant(10))
					.then(SQLTypeFactory.getInstance().constant(TWO_HUNDRED_THOUSAND))
					.elseClause(SQLTypeFactory.getInstance().mathExpression(colDfltInteger).subtract(10))
					.end());
			Assertions.assertEquals("case when " + COL_DFLTINTEGER + " + 10 >= 10" +
									" then " + TWO_HUNDRED_THOUSAND + 
									" else " + COL_DFLTINTEGER + " - 10 end",caseExprDfltIntGE);
			
			/* String Expressions */
			/* CASE WHEN 'Abc' || 'Def' = 'AbcDef' THEN 'True' ELSE 'It's' || 'False' END */
			String caseClauseStrConcat = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC))
									.concat(STRING_EXPR_DEF),Op.EQ,SQLTypeFactory.getInstance().constant("AbcDef"))
					.then(SQLTypeFactory.getInstance().constant("True"))
					.elseClause(SQLTypeFactory.getInstance().stringExpression("It's").concat("False"))
					.end());
			Assertions.assertEquals("case when " + "'Abc' || 'Def' = 'AbcDef'" +
									" then 'True'" +
									" else 'It\'s' || 'False' end",caseClauseStrConcat);
			
			/* CASE WHEN '10' || 10 = 1010 THEN 1010 ELSE '10' || 10 END */
			String caseClauseStrConcat10 = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("10"))
									.concat(SQLTypeFactory.getInstance().constant(10)),Op.EQ,SQLTypeFactory.getInstance().constant(1010))
					.then(SQLTypeFactory.getInstance().constant(1010))
					.elseClause(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("10")).concat(SQLTypeFactory.getInstance().constant(10)))
					.end());
			Assertions.assertEquals("case when " + "'10' || 10 = 1010" +
									" then 1010" + 
									" else '10' || 10 end",caseClauseStrConcat10);
			
			/* CASE WHEN 'Abc' || 'Def' > 'AbcDef' THEN 'True' ELSE 'False' END */
			String caseClauseStrConcatAbc = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(STRING_EXPR_ABC)).concat(SQLTypeFactory.getInstance().constant(STRING_EXPR_DEF)),Op.GT,
						  SQLTypeFactory.getInstance().constant("AbcDef"))
					.then(SQLTypeFactory.getInstance().constant("True"))
					.elseClause(SQLTypeFactory.getInstance().constant("False"))			
					.end());
			Assertions.assertEquals("case when '" + STRING_EXPR_ABC + "' || '" + STRING_EXPR_DEF + "' > 'AbcDef'" +
									" then 'True' else 'False' end",caseClauseStrConcatAbc);
			
			/* CASE WHEN '10' || 10 <= 1010 THEN 1010 ELSE 'False' END */
			String caseClauseStrConcatNbr = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("10")).concat(SQLTypeFactory.getInstance().constant(10)),Op.LTE,
						  SQLTypeFactory.getInstance().constant(1010))
					.then(SQLTypeFactory.getInstance().constant(1010))
					.elseClause(SQLTypeFactory.getInstance().constant("False"))			
					.end());
			Assertions.assertEquals("case when '10' || 10 <= 1010"  +
									" then 1010 else 'False' end",caseClauseStrConcatNbr);
			
			/* Number Expressions */
			/* CASE WHEN 10 = 20 THEN 20 ELSE 30 END */
			String caseClauseNbr = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant(10)),Op.EQ,SQLTypeFactory.getInstance().constant(20))
					.then(SQLTypeFactory.getInstance().constant(20))
					.elseClause(SQLTypeFactory.getInstance().constant(30))
					.end());
			Assertions.assertEquals("case when 10 = 20"  +
									" then 20" +
									" else 30 end",caseClauseNbr);
			
		/* CASE WHEN 10 + 10 > 30 THEN 20 ELSE 20 + 10 END */
			String caseClauseNbrAdd2 = serializer.serialize(SQLTypeFactory.getInstance().caseClause()
					.when(SQLTypeFactory.getInstance().mathExpression(10).add(SQLTypeFactory.getInstance().constant(10)),Op.GT,
						  SQLTypeFactory.getInstance().constant(30))
					.then(SQLTypeFactory.getInstance().constant(20))
					.elseClause(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(20)).add(10))
					.end());
			Assertions.assertEquals("case when 10 + 10 > 30"  +
									" then 20" + 
									" else 20 + 10 end",caseClauseNbrAdd2);

		}
}
