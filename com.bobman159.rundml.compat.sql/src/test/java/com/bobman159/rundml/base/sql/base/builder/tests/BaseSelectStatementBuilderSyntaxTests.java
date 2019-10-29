package com.bobman159.rundml.base.sql.base.builder.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.exprtypes.ParmMarker;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.sql.factory.RunDMLSQLFactory;

class BaseSelectStatementBuilderSyntaxTests {
	
	private static final String ABCDEFG_LITERAL = "Abcdefg";
	private static final String DFLTINTEGER = "dfltInteger";
	private static final String NOTNULLVARCHAR = "notNullVarchar";
	private static final String NOTNULLCHAR = "notNullChar";
	private static final String FROM_CLAUSE = "from rundml.typetest";
	private static final String FROM_CLAUSE_SPACE = "from rundml.typetest ";
	private static final String NOTNULLDATE = "notNullDate";
	private static final String NOTNULLDEC72 = "notNullDec72";
	private static final String RUNDML_SCHEMA = "rundml";
	private static final String RUNDML_TABLE = "typetest";
	private static final String SELECT_1000 = "select 100000,'Abcdefg',DFLTINTEGER ";
	private static final String SELECT_NOTNULLCHAR = "select NOTNULLCHAR ";
	private static final String NUMERIC_LITERAL = "0123456789";

	private static TableDefinition tbDef;
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		tbDef = new TableDefinition(RUNDML_SCHEMA,RUNDML_TABLE);
		tbDef.addColumn(DFLTINTEGER, Types.INTEGER);
		tbDef.addColumn(NOTNULLDEC72, Types.DECIMAL);
		tbDef.addColumn(NOTNULLDATE, Types.INTEGER);
		tbDef.addColumn(NOTNULLCHAR, Types.CHAR);
		tbDef.addColumn("dfltSigned", Types.INTEGER);
		tbDef.addColumn("dfltTinyInt", Types.TINYINT);
		tbDef.addColumn(NOTNULLVARCHAR, Types.VARCHAR);

	}

	@AfterAll
	static void tearDownAfterClass() {
		//tear down not needed at this time
	}
	

	@BeforeEach
	void setUp() {
		//set up not needed at this time
	}

	@AfterEach
	void tearDown() {
		//tear down not needed at this time
	}

	@Test
	void compatibleSelectTest() {
		
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.selectStar()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
	
		Assertions.assertEquals("select * from rundml.typetest",stmtText);

	}
	
	@Test
	void compatibleSelectStarTest() {
		
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.selectStar()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
	
		Assertions.assertEquals("select * from rundml.typetest",stmtText);

	}
	
	@Test
	void compatibleSelectExpressionTest() {
		/*
		 * Test each of the supported IExpression types.
		 * The expression type combinations (math, concat, etc) are tested by 
		 * the com.bobman159.rundml.core.exprtypes.tests JUnits.
		 */
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(10))
				.selectExpression(Expression.string("This is a string"))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.selectExpression(new ParmMarker(Types.VARCHAR,"This is a string too"))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
			
		Assertions.assertEquals("select 10,'This is a string',DFLTINTEGER," +
							"? from rundml.typetest",stmtText);

	}
	
	@Test
	void compatSelectTableDefinitionTest() {
				
		String stmtText =  RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select(tbDef)
				.getStatementText();

		Assertions.assertEquals("select DFLTINTEGER,NOTNULLDEC72,NOTNULLDATE," + 
						    "NOTNULLCHAR,DFLTSIGNED,DFLTTINYINT,NOTNULLVARCHAR "	   + 
							FROM_CLAUSE,stmtText);		

	}
	
	@Test
	void comptibleSelectAllTest() {
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select().all()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select all DFLTINTEGER " + FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.all()
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select all NOTNULLVARCHAR " +
							FROM_CLAUSE,stmtText2);

	}
	
	@Test
	void compatibleSelectDistinctAllTest() {
		
		//This test should return no rows  (possibly throw an exception?)
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.distinct().all()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.distinct().all()
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all NOTNULLVARCHAR " +
							FROM_CLAUSE,stmtText2);

	}
	

	@Test
	void compatibleSelectWhereTest() {
		
		Predicate pred = Predicate.where(tbDef.column(DFLTINTEGER))
				  .isGreater(100000)
				  .and(tbDef.column(NOTNULLDEC72))
				  .isGreaterOrEqual(12345.10)
				  .or(tbDef.column(NOTNULLDATE))
				  .isEqual("2019-03-15")
				  .or(tbDef.column(NOTNULLVARCHAR))
				  .isGreaterOrEqual(ABCDEFG_LITERAL)
				  .or(tbDef.column(NOTNULLVARCHAR))
				  .isEqual(Expression.parm(Types.BIGINT, "This is a test"))
				  .build();
					  
	
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
						.select()
						.selectExpression(tbDef.column(DFLTINTEGER))
						.selectExpression(tbDef.column(NOTNULLDEC72))
						.selectExpression(tbDef.column(NOTNULLDATE))
						.selectExpression(tbDef.column(NOTNULLVARCHAR))
						.from(RUNDML_SCHEMA,RUNDML_TABLE)
						.where(pred)
						.getStatementText();
		Assertions.assertEquals("select DFLTINTEGER,NOTNULLDEC72,NOTNULLDATE," +
				     "NOTNULLVARCHAR from rundml.typetest " + 
				     "WHERE DFLTINTEGER > 100000 " + 
				     "AND NOTNULLDEC72 >= 12345.1 " +
				     "OR NOTNULLDATE = '2019-03-15' " + 
				     "OR NOTNULLVARCHAR >= 'Abcdefg' " +
				     "OR NOTNULLVARCHAR = ?",stmtText);

	}
	
	
	@Test
	void compatibleSelectGroupByTest() {
		
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.number(100000),
						 Expression.string(ABCDEFG_LITERAL),
						 Expression.parm(Types.DECIMAL, 100000),
						 tbDef.column(DFLTINTEGER))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "group by 100000,'Abcdefg',?,DFLTINTEGER",stmtText);

	}
	
	@Test
	void compatibleSelectOrderByTest() {
		
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2),Expression.orderBy(3))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,3",stmtText);
		
		String stmtText2 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),
						 Expression.orderBy(2),
						 Expression.orderBy(tbDef.column(DFLTINTEGER)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,DFLTINTEGER",stmtText2);		
		
		String stmtText3 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(Expression.string(ABCDEFG_LITERAL)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 'Abcdefg'",stmtText3);
		
		String stmtText4 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1).asc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1 asc",stmtText4);
		
		String stmtText5 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(DFLTINTEGER)).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER desc",stmtText5);

		String stmtText6 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText6);		

		String stmtText7 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2).desc().nullsLast())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc nulls last",stmtText7);		

		String stmtText8 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(DFLTINTEGER)),
						 Expression.orderBy(tbDef.column(NOTNULLVARCHAR)).desc()
						 			.nullsFirst())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER,NOTNULLVARCHAR desc nulls first",stmtText8);	

		String stmtText9 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.selectExpression(tbDef.column(NOTNULLDEC72))
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(NOTNULLVARCHAR)).desc()
								   .nullsLast(),
						 Expression.orderBy(1).asc().nullsFirst(),
						 Expression.orderBy(tbDef.column(NOTNULLDEC72)))
				.getStatementText();

		Assertions.assertEquals("select DFLTINTEGER,NOTNULLDEC72,NOTNULLVARCHAR " +
				     FROM_CLAUSE_SPACE +
				     "order by NOTNULLVARCHAR desc nulls last,1 asc nulls first," +
				     "NOTNULLDEC72",stmtText9);
		
		String stmtText10 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(DFLTINTEGER)).asc()
								   .nullsFirst(),
						 Expression.orderBy(2).desc().nullsLast(),
						 Expression.orderBy(3).desc().nullsFirst(),
						 Expression.orderBy(Expression.parm(Types.TINYINT,10)).asc()
				 		   .nullsLast())				
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER asc nulls first,2 desc nulls last," +
				     "3 desc nulls first,? asc nulls last",stmtText10);

	}

	
	@Test
	void compatibleSelectHavingTest() {
		
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(tbDef.column(DFLTINTEGER))
				.having(Predicate.having(tbDef.column(DFLTINTEGER)).isGreater(100000)
								 .build())
				.getStatementText();

		Assertions.assertEquals("select DFLTINTEGER " +
				     FROM_CLAUSE_SPACE +
				     "group by DFLTINTEGER HAVING DFLTINTEGER > 100000",stmtText);

		String stmtText2 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(tbDef.column(NOTNULLCHAR))
				.having(Predicate.having(tbDef.column(NOTNULLCHAR)).isGreater(NUMERIC_LITERAL)
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by NOTNULLCHAR HAVING NOTNULLCHAR > '0123456789'",stmtText2);

		String stmtText3 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(tbDef.column(NOTNULLCHAR))
				.having(Predicate.having(tbDef.column(NOTNULLCHAR))
								 .isGreaterOrEqual(NUMERIC_LITERAL)
								 .or(tbDef.column(NOTNULLCHAR)).isEqual("223456789")
								 .and(tbDef.column(NOTNULLCHAR)).isLess("1123456789")
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by NOTNULLCHAR HAVING NOTNULLCHAR >= '0123456789' " +
				     "OR NOTNULLCHAR = '223456789' AND NOTNULLCHAR < " +
				     "'1123456789'",stmtText3);

		String stmtText4 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.string("Abcdef"),
						 Expression.string("Hiklmnop"))
				.having(Predicate.having("Abcdef")
								 .isEqual("Abcdef2")
								 .or("Hijklmnop").isGreater("Hijklmno")
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by 'Abcdef','Hiklmnop' HAVING 'Abcdef' = 'Abcdef2' " +
				     "OR 'Hijklmnop' > 'Hijklmno'",stmtText4);

		String stmtText5 = RunDMLSQLFactory.createBaseSelectStatement(tbDef)
				.select()
				.selectExpression(Expression.number(10))
				.selectExpression(Expression.number(20))
				.selectExpression(Expression.number(30))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.number(20),
						 Expression.number(30),
						 Expression.number(10))
					.having(Predicate.having(20)
								 .isEqual(20)
								 .and(20).isGreater(10)
								 .and(10).isLess(30)
								 .and(Expression.parm(Types.CHAR, ABCDEFG_LITERAL)).isEqual(ABCDEFG_LITERAL)
								 .build())
				.getStatementText();

		Assertions.assertEquals("select 10,20,30 " +
				     FROM_CLAUSE_SPACE +
				     "group by 20,30,10 HAVING 20 = 20 " +
				     "AND 20 > 10 AND 10 < 30 " + 
				     "AND ? = 'Abcdefg'",stmtText5);

	}

}
