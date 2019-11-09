package com.bobman159.rundml.sql.mysql.tests;

import java.sql.Connection;
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
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.sql.factory.RunDMLSQLFactory;

class MySQLSelectSyntaxTests {
	
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
	
	@BeforeAll
	static void setUpBeforeClass() {
		//No set up needed
	}

	@AfterAll
	static void tearDownAfterClass() {
		//No tear down needed
	}

	@BeforeEach
	void setUp() {
		//No set up needed
	}

	@AfterEach
	void tearDown() {
		//No tear down needed
	}

	@Test
	void mySQLHavingTest() {
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				
				.select()
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.column(DFLTINTEGER))
				.having(Predicate.having(Expression.column(DFLTINTEGER)).isGreater(100000)
								 .build())
				.getStatementText();
				

		Assertions.assertEquals("select DFLTINTEGER " +
				     FROM_CLAUSE_SPACE +
				     "group by DFLTINTEGER HAVING DFLTINTEGER > 100000",stmtText);

		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.column(NOTNULLCHAR))
				.having(Predicate.having(Expression.column(NOTNULLCHAR)).isGreater(NUMERIC_LITERAL)
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by NOTNULLCHAR HAVING NOTNULLCHAR > '0123456789'",stmtText2);

		String stmtText3 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.column(NOTNULLCHAR))
				.having(Predicate.having(Expression.column(NOTNULLCHAR))
								 .isGreaterOrEqual(NUMERIC_LITERAL)
								 .or(Expression.column(NOTNULLCHAR)).isEqual("223456789")
								 .and(Expression.column(NOTNULLCHAR)).isLess("1123456789")
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by NOTNULLCHAR HAVING NOTNULLCHAR >= '0123456789' " +
				     "OR NOTNULLCHAR = '223456789' AND NOTNULLCHAR < " +
				     "'1123456789'",stmtText3);

		String stmtText4 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.column(NOTNULLCHAR))
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

		String stmtText5 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(10))
				.selectExpression(Expression.number(20))
				.selectExpression(Expression.number(30))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.number(2),
						 Expression.number(3),
						 Expression.number(1))
				.having(Predicate.having(20)
								 .isEqual(20)
								 .and(20).isGreater(10)
								 .and(10).isLess(30)
								 .and(Expression.parm(Types.CHAR, ABCDEFG_LITERAL)).isEqual(ABCDEFG_LITERAL)
								 .build())
				.getStatementText();

		Assertions.assertEquals("select 10,20,30 " +
				     FROM_CLAUSE_SPACE +
				     "group by 2,3,1 HAVING 20 = 20 " +
				     "AND 20 > 10 AND 10 < 30 " + 
				     "AND ? = 'Abcdefg'",stmtText5);

	}

	@Test
	void mySQLOffsetTest() {
		

		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.selectStar()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(Expression.number(5)).offset(Expression.number(1))
				.getStatementText();

		Assertions.assertEquals("select * " + FROM_CLAUSE_SPACE + "limit 5 offset 1",stmtText);

	}


	@Test
	void mySQLGroupByTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.number(1),
						 Expression.string(ABCDEFG_LITERAL),
						 Expression.parm(Types.DECIMAL, 100000),
						 Expression.column(DFLTINTEGER))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "group by 1,'Abcdefg',?,DFLTINTEGER",stmtText);

	}

	@Test
	void mySQLLimitTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.selectStar()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(Expression.number(5))
				.getStatementText();

		Assertions.assertEquals("select * " + FROM_CLAUSE_SPACE + "limit 5",stmtText);

	}

	@Test
	void mySQLOrderByTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2),Expression.orderBy(3))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,3",stmtText);
		
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),
						 Expression.orderBy(2),
						 Expression.orderBy(Expression.column(DFLTINTEGER)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,DFLTINTEGER",stmtText2);		
		
		String stmtText3 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(Expression.string(ABCDEFG_LITERAL)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 'Abcdefg'",stmtText3);
		
		String stmtText4 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1).asc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1 asc",stmtText4);
		
		String stmtText5 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(Expression.column(DFLTINTEGER)).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER desc",stmtText5);

		String stmtText6 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText6);		

		
		String stmtText7 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText7);		


		String stmtText8 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(Expression.column(DFLTINTEGER)),
						 Expression.orderBy(Expression.column(NOTNULLVARCHAR)).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER,NOTNULLVARCHAR desc",stmtText8);	

		String stmtText9 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.column(DFLTINTEGER))
				.selectExpression(Expression.column(NOTNULLDEC72))
				.selectExpression(Expression.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(Expression.column(NOTNULLVARCHAR)).desc(),
						 Expression.orderBy(1).asc(),
						 Expression.orderBy(Expression.column(NOTNULLDEC72)))
				.getStatementText();

		Assertions.assertEquals("select DFLTINTEGER,NOTNULLDEC72,NOTNULLVARCHAR " +
				     FROM_CLAUSE_SPACE +
				     "order by NOTNULLVARCHAR desc,1 asc," +
				     "NOTNULLDEC72",stmtText9);
		
		String stmtText10 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(Expression.column(DFLTINTEGER)).asc(),
						 Expression.orderBy(2).desc(),
						 Expression.orderBy(3).desc(),
						 Expression.orderBy(Expression.parm(Types.TINYINT,10)).asc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER asc,2 desc," +
				     "3 desc,? asc",stmtText10);

	}

	@Test
	void mySQLWhereTest() {
		Predicate pred = Predicate.where(Expression.column(DFLTINTEGER))
				  .isGreater(100000)
				  .and(Expression.column(NOTNULLDEC72))
				  .isGreaterOrEqual(12345.10)
				  .or(Expression.column(NOTNULLDATE))
				  .isEqual("2019-03-15")
				  .or(Expression.column(NOTNULLVARCHAR))
				  .isGreaterOrEqual(ABCDEFG_LITERAL)
				  .or(Expression.column(NOTNULLVARCHAR))
				  .isEqual(Expression.parm(Types.BIGINT, "This is a test"))
				  .build();
				  

		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
						.select()
						.selectExpression(Expression.column(DFLTINTEGER))
						.selectExpression(Expression.column(NOTNULLDEC72))
						.selectExpression(Expression.column(NOTNULLDATE))
						.selectExpression(Expression.column(NOTNULLVARCHAR))
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
	void mySQLSelectStarTest() {
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				  .selectStar()
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
	
		Assertions.assertEquals("select * from rundml.typetest",stmtText);		

	}
	
	@Test
	void mySQLSelectProviderTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				  .selectStar()
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
	
		Assertions.assertEquals("select * from rundml.typetest",stmtText);		

	}

	
	@Test
	void mySQLSelectTableDefinitionTest() {
		
		String stmtText =  RunDMLSQLFactory.createMySQLSelectStatement()
				  .select()
				  .selectExpression(Expression.column(DFLTINTEGER))
				  .selectExpression(Expression.column(NOTNULLDEC72))
				  .selectExpression(Expression.column(NOTNULLDATE))
				  .selectExpression(Expression.column(NOTNULLCHAR))
				  .selectExpression(Expression.column("dfltText"))
				  .selectExpression(Expression.column("dfltBlob"))
				  .selectExpression(Expression.column(NOTNULLVARCHAR))
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
		
		Assertions.assertEquals("select DFLTINTEGER,NOTNULLDEC72,NOTNULLDATE," + 
						    "NOTNULLCHAR,DFLTTEXT,DFLTBLOB,NOTNULLVARCHAR "	   + 
							FROM_CLAUSE,stmtText);

	}


	@Test
	void mySQLAllTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.all()
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select all DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

	}


	@Test
	void mySQLDistinctTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select().distinct()
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();

		Assertions.assertEquals("select distinct DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

	}
	
	@Test
	void mySQLAllDistinctTest() {
		
		//SELECT DISTINCT ALL is not valid in MySQL
		//This test should return no rows  (possibly throw an exception?)
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.distinct().all()
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.distinct().all()
				.selectExpression(Expression.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all NOTNULLVARCHAR " +
							FROM_CLAUSE,stmtText2);
		
	}


	@Test
	void mySQLsmallResultTest() {
		
//		VALID
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.smallResult()
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select sql_small_result DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

//		INVALID 	
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.column(NOTNULLVARCHAR))
				.smallResult()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select NOTNULLVARCHAR sql_small_result " +
							FROM_CLAUSE,stmtText2);

	}

	@Test
	void mySQLbigResultTest() {
		
//		VALID
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.bigResult()
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select sql_big_result DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

//		INVALID
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.column(NOTNULLVARCHAR))
				.bigResult()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select NOTNULLVARCHAR sql_big_result " +
							FROM_CLAUSE,stmtText2);

	}

	@Test
	void mySQLbufferResultTest() {
		
//		VALID
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.bufferResult()
				.selectExpression(Expression.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select sql_buffer_result DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

//		VALID? - Executed ok in rundml db
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.column(NOTNULLVARCHAR))
				.bufferResult()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select NOTNULLVARCHAR sql_buffer_result " +
							FROM_CLAUSE,stmtText2);

	}

	@Test
	void mySQLselectExpressionTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.number(10))
				.selectExpression(Expression.string("This is a string"))
				.selectExpression(Expression.column(DFLTINTEGER))
				.selectExpression(new ParmMarker(Types.VARCHAR,"This is a string too"))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select 10,'This is a string',DFLTINTEGER," +
							"? from rundml.typetest",stmtText);

	}

}
