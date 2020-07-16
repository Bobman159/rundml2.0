package com.bobman159.rundml.sql.mysql.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.predicates.impl.PredicateBuilder;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.core.sql.types.impl.ParmMarker;
import com.bobman159.rundml.sql.factory.RunDMLSQLFactory;
import com.bobman159.rundml.sql.mysql.mocktables.MockMySQLPrimitivesTypeTest;

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
	private static final String SELECT_1000 = "select 100000,'Abcdefg',dfltInteger ";
	private static final String SELECT_NOTNULLCHAR = "select notNullChar ";
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
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.having(PredicateBuilder.having(SQLTypeFactory.getInstance().column(DFLTINTEGER)).isGreater(100000)
								 .build())
				.getStatementText();
				

		Assertions.assertEquals("select dfltInteger " +
				     FROM_CLAUSE_SPACE +
				     "group by dfltInteger HAVING dfltInteger > 100000",stmtText);

		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.getInstance().column(NOTNULLCHAR))
				.having(PredicateBuilder.having(SQLTypeFactory.getInstance().column(NOTNULLCHAR)).isGreater(NUMERIC_LITERAL)
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by notNullChar HAVING notNullChar > '0123456789'",stmtText2);

		String stmtText3 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.getInstance().column(NOTNULLCHAR))
				.having(PredicateBuilder.having(SQLTypeFactory.getInstance().column(NOTNULLCHAR))
								 .isGreaterOrEqual(NUMERIC_LITERAL)
								 .or(SQLTypeFactory.getInstance().column(NOTNULLCHAR)).isEqual("223456789")
								 .and(SQLTypeFactory.getInstance().column(NOTNULLCHAR)).isLess("1123456789")
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by notNullChar HAVING notNullChar >= '0123456789' " +
				     "OR notNullChar = '223456789' AND notNullChar < " +
				     "'1123456789'",stmtText3);

		String stmtText4 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.getInstance().constant("Abcdef"),
						 SQLTypeFactory.getInstance().constant("Hiklmnop"))
				.having(PredicateBuilder.having("Abcdef")
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
				.selectExpression(SQLTypeFactory.getInstance().constant(10))
				.selectExpression(SQLTypeFactory.getInstance().constant(20))
				.selectExpression(SQLTypeFactory.getInstance().constant(30))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.getInstance().constant(2),
						 SQLTypeFactory.getInstance().constant(3),
						 SQLTypeFactory.getInstance().constant(1))
				.having(PredicateBuilder.having(20)
								 .isEqual(20)
								 .and(20).isGreater(10)
								 .and(10).isLess(30)
								 .and(SQLTypeFactory.getInstance().parm(Types.CHAR, ABCDEFG_LITERAL)).isEqual(ABCDEFG_LITERAL)
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
				.select("dfltInteger")
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(SQLTypeFactory.getInstance().constant(5)).offset(SQLTypeFactory.getInstance().constant(1))
				.getStatementText();

		Assertions.assertEquals("select dfltInteger " + FROM_CLAUSE_SPACE + "limit 5 offset 1",stmtText);

	}


	@Test
	void mySQLGroupByTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.getInstance().constant(1),
						 SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL),
						 SQLTypeFactory.getInstance().parm(Types.DECIMAL, 100000),
						 SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "group by 1,'Abcdefg',?,dfltInteger",stmtText);

	}

	@Test
	void mySQLLimitTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select(MockMySQLPrimitivesTypeTest.class)
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(SQLTypeFactory.getInstance().constant(5))
				.getStatementText();

		Assertions.assertEquals("select DfltInteger,NotNullMediumInt,DfltIntUnsigned,DfltTinyInt,NotNullSmint," + 
								"NotNullDec72,NotNullTime,NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar," + 
								"NotNullChar,NotNullBlob,NotNullText,NotNullBoolean,NotNullBit,NotNullBigInt," + 
								"NotNullBinary,NotNullVarBinary "	+ 
								"" + FROM_CLAUSE_SPACE + "limit 5",stmtText);

	}

	@Test
	void mySQLOrderByTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(1),SQLTypeFactory.getInstance().orderBy(2),SQLTypeFactory.getInstance().orderBy(3))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,3",stmtText);
		
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(1),
						 SQLTypeFactory.getInstance().orderBy(2),
						 SQLTypeFactory.getInstance().orderBy(SQLTypeFactory.getInstance().column(DFLTINTEGER)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,dfltInteger",stmtText2);		
		
		String stmtText3 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 'Abcdefg'",stmtText3);
		
		String stmtText4 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(1).asc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1 asc",stmtText4);
		
		String stmtText5 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(SQLTypeFactory.getInstance().column(DFLTINTEGER)).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger desc",stmtText5);

		String stmtText6 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(1),SQLTypeFactory.getInstance().orderBy(2).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText6);		

		
		String stmtText7 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(1),SQLTypeFactory.getInstance().orderBy(2).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText7);		


		String stmtText8 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(SQLTypeFactory.getInstance().column(DFLTINTEGER)),
						 SQLTypeFactory.getInstance().orderBy(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR)).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger,notNullVarchar desc",stmtText8);	

		String stmtText9 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLDEC72))
				.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR)).desc(),
						 SQLTypeFactory.getInstance().orderBy(1).asc(),
						 SQLTypeFactory.getInstance().orderBy(SQLTypeFactory.getInstance().column(NOTNULLDEC72)))
				.getStatementText();

		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullVarchar " +
				     FROM_CLAUSE_SPACE +
				     "order by notNullVarchar desc,1 asc," +
				     "notNullDec72",stmtText9);
		
		String stmtText10 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(100000))
				.selectExpression(SQLTypeFactory.getInstance().constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.getInstance().orderBy(SQLTypeFactory.getInstance().column(DFLTINTEGER)).asc(),
						 SQLTypeFactory.getInstance().orderBy(2).desc(),
						 SQLTypeFactory.getInstance().orderBy(3).desc(),
						 SQLTypeFactory.getInstance().orderBy(SQLTypeFactory.getInstance().parm(Types.TINYINT,10)).asc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger asc,2 desc," +
				     "3 desc,? asc",stmtText10);

	}

	@Test
	void mySQLWhereTest() {
		PredicateBuilder pred = PredicateBuilder.where(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				  .isGreater(100000)
				  .and(SQLTypeFactory.getInstance().column(NOTNULLDEC72))
				  .isGreaterOrEqual(12345.10)
				  .or(SQLTypeFactory.getInstance().column(NOTNULLDATE))
				  .isEqual("2019-03-15")
				  .or(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				  .isGreaterOrEqual(ABCDEFG_LITERAL)
				  .or(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				  .isEqual(SQLTypeFactory.getInstance().parm(Types.BIGINT, "This is a test"))
				  .build();
				  

		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
						.select()
						.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
						.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLDEC72))
						.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLDATE))
						.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
						.from(RUNDML_SCHEMA,RUNDML_TABLE)
						.where(pred)
						.getStatementText();
		
		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullDate," +
				     "notNullVarchar from rundml.typetest " + 
				     "WHERE dfltInteger > 100000 " + 
				     "AND notNullDec72 >= 12345.1 " +
				     "OR notNullDate = '2019-03-15' " + 
				     "OR notNullVarchar >= 'Abcdefg' " +
				     "OR notNullVarchar = ?",stmtText);

	}


	@Test
	void mySQLSelectExpressionTest() {
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				  .select()
				  .selectExpression(SQLTypeFactory.getInstance().mathExpression(10)).add(SQLTypeFactory.getInstance().constant(10))
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
	
		Assertions.assertEquals("select 10 + 10 from rundml.typetest",stmtText);		

	}
	
	@Test
	void mySQLSelectProviderTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				  .select(SQLTypeFactory.getInstance().column("dfltInteger"),SQLTypeFactory.getInstance().column("notNullChar"))
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
	
		Assertions.assertEquals("select dfltInteger,notNullChar from rundml.typetest",stmtText);		

	}

	
	@Test
	void mySQLSelectTableDefinitionTest() {
		
		String stmtText =  RunDMLSQLFactory.createMySQLSelectStatement()
				  .select()
				  .selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				  .selectExpression(SQLTypeFactory.getInstance().column(NOTNULLDEC72))
				  .selectExpression(SQLTypeFactory.getInstance().column(NOTNULLDATE))
				  .selectExpression(SQLTypeFactory.getInstance().column(NOTNULLCHAR))
				  .selectExpression(SQLTypeFactory.getInstance().column("dfltText"))
				  .selectExpression(SQLTypeFactory.getInstance().column("dfltBlob"))
				  .selectExpression(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
		
		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullDate," + 
						    "notNullChar,dfltText,dfltBlob,notNullVarchar "	   + 
							FROM_CLAUSE,stmtText);

	}


	@Test
	void mySQLAllTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.all()
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select all dfltInteger " +
							FROM_CLAUSE,stmtText);

	}


	@Test
	void mySQLDistinctTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select().distinct()
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();

		Assertions.assertEquals("select distinct dfltInteger " +
							FROM_CLAUSE,stmtText);

	}
	
	@Test
	void mySQLAllDistinctTest() {
		
		//SELECT DISTINCT ALL is not valid in MySQL
		//This test should return no rows  (possibly throw an exception?)
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.distinct().all()
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all dfltInteger " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.distinct().all()
				.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all notNullVarchar " +
							FROM_CLAUSE,stmtText2);
		
	}


	@Test
	void mySQLsmallResultTest() {
		
//		VALID
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.smallResult()
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select sql_small_result dfltInteger " +
							FROM_CLAUSE,stmtText);

//		INVALID 	
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				.smallResult()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select notNullVarchar sql_small_result " +
							FROM_CLAUSE,stmtText2);

	}

	@Test
	void mySQLbigResultTest() {
		
//		VALID
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.bigResult()
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select sql_big_result dfltInteger " +
							FROM_CLAUSE,stmtText);

//		INVALID
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				.bigResult()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select notNullVarchar sql_big_result " +
							FROM_CLAUSE,stmtText2);

	}

	@Test
	void mySQLbufferResultTest() {
		
//		VALID
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.bufferResult()
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select sql_buffer_result dfltInteger " +
							FROM_CLAUSE,stmtText);

//		VALID? - Executed ok in rundml db
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().column(NOTNULLVARCHAR))
				.bufferResult()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select notNullVarchar sql_buffer_result " +
							FROM_CLAUSE,stmtText2);

	}

	@Test
	void mySQLselectExpressionTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.getInstance().constant(10))
				.selectExpression(SQLTypeFactory.getInstance().constant("This is a string"))
				.selectExpression(SQLTypeFactory.getInstance().column(DFLTINTEGER))
				.selectExpression(new ParmMarker(Types.VARCHAR,"This is a string too"))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select 10,'This is a string',dfltInteger," +
							"? from rundml.typetest",stmtText);

	}

}
