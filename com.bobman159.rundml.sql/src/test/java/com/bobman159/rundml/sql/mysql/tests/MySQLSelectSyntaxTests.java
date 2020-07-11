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
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.column(DFLTINTEGER))
				.having(PredicateBuilder.having(SQLTypeFactory.column(DFLTINTEGER)).isGreater(100000)
								 .build())
				.getStatementText();
				

		Assertions.assertEquals("select dfltInteger " +
				     FROM_CLAUSE_SPACE +
				     "group by dfltInteger HAVING dfltInteger > 100000",stmtText);

		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.column(NOTNULLCHAR))
				.having(PredicateBuilder.having(SQLTypeFactory.column(NOTNULLCHAR)).isGreater(NUMERIC_LITERAL)
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by notNullChar HAVING notNullChar > '0123456789'",stmtText2);

		String stmtText3 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.column(NOTNULLCHAR))
				.having(PredicateBuilder.having(SQLTypeFactory.column(NOTNULLCHAR))
								 .isGreaterOrEqual(NUMERIC_LITERAL)
								 .or(SQLTypeFactory.column(NOTNULLCHAR)).isEqual("223456789")
								 .and(SQLTypeFactory.column(NOTNULLCHAR)).isLess("1123456789")
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by notNullChar HAVING notNullChar >= '0123456789' " +
				     "OR notNullChar = '223456789' AND notNullChar < " +
				     "'1123456789'",stmtText3);

		String stmtText4 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.constant("Abcdef"),
						 SQLTypeFactory.constant("Hiklmnop"))
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
				.selectExpression(SQLTypeFactory.constant(10))
				.selectExpression(SQLTypeFactory.constant(20))
				.selectExpression(SQLTypeFactory.constant(30))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.constant(2),
						 SQLTypeFactory.constant(3),
						 SQLTypeFactory.constant(1))
				.having(PredicateBuilder.having(20)
								 .isEqual(20)
								 .and(20).isGreater(10)
								 .and(10).isLess(30)
								 .and(SQLTypeFactory.parm(Types.CHAR, ABCDEFG_LITERAL)).isEqual(ABCDEFG_LITERAL)
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
				.limit(SQLTypeFactory.constant(5)).offset(SQLTypeFactory.constant(1))
				.getStatementText();

		Assertions.assertEquals("select dfltInteger " + FROM_CLAUSE_SPACE + "limit 5 offset 1",stmtText);

	}


	@Test
	void mySQLGroupByTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.constant(1),
						 SQLTypeFactory.constant(ABCDEFG_LITERAL),
						 SQLTypeFactory.parm(Types.DECIMAL, 100000),
						 SQLTypeFactory.column(DFLTINTEGER))
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
				.limit(SQLTypeFactory.constant(5))
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
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1),SQLTypeFactory.orderBy(2),SQLTypeFactory.orderBy(3))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,3",stmtText);
		
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1),
						 SQLTypeFactory.orderBy(2),
						 SQLTypeFactory.orderBy(SQLTypeFactory.column(DFLTINTEGER)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,dfltInteger",stmtText2);		
		
		String stmtText3 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.constant(ABCDEFG_LITERAL)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 'Abcdefg'",stmtText3);
		
		String stmtText4 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1).asc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1 asc",stmtText4);
		
		String stmtText5 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.column(DFLTINTEGER)).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger desc",stmtText5);

		String stmtText6 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1),SQLTypeFactory.orderBy(2).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText6);		

		
		String stmtText7 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1),SQLTypeFactory.orderBy(2).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText7);		


		String stmtText8 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.column(DFLTINTEGER)),
						 SQLTypeFactory.orderBy(SQLTypeFactory.column(NOTNULLVARCHAR)).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger,notNullVarchar desc",stmtText8);	

		String stmtText9 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.selectExpression(SQLTypeFactory.column(NOTNULLDEC72))
				.selectExpression(SQLTypeFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.column(NOTNULLVARCHAR)).desc(),
						 SQLTypeFactory.orderBy(1).asc(),
						 SQLTypeFactory.orderBy(SQLTypeFactory.column(NOTNULLDEC72)))
				.getStatementText();

		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullVarchar " +
				     FROM_CLAUSE_SPACE +
				     "order by notNullVarchar desc,1 asc," +
				     "notNullDec72",stmtText9);
		
		String stmtText10 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.constant(100000))
				.selectExpression(SQLTypeFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.column(DFLTINTEGER)).asc(),
						 SQLTypeFactory.orderBy(2).desc(),
						 SQLTypeFactory.orderBy(3).desc(),
						 SQLTypeFactory.orderBy(SQLTypeFactory.parm(Types.TINYINT,10)).asc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger asc,2 desc," +
				     "3 desc,? asc",stmtText10);

	}

	@Test
	void mySQLWhereTest() {
		PredicateBuilder pred = PredicateBuilder.where(SQLTypeFactory.column(DFLTINTEGER))
				  .isGreater(100000)
				  .and(SQLTypeFactory.column(NOTNULLDEC72))
				  .isGreaterOrEqual(12345.10)
				  .or(SQLTypeFactory.column(NOTNULLDATE))
				  .isEqual("2019-03-15")
				  .or(SQLTypeFactory.column(NOTNULLVARCHAR))
				  .isGreaterOrEqual(ABCDEFG_LITERAL)
				  .or(SQLTypeFactory.column(NOTNULLVARCHAR))
				  .isEqual(SQLTypeFactory.parm(Types.BIGINT, "This is a test"))
				  .build();
				  

		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
						.select()
						.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
						.selectExpression(SQLTypeFactory.column(NOTNULLDEC72))
						.selectExpression(SQLTypeFactory.column(NOTNULLDATE))
						.selectExpression(SQLTypeFactory.column(NOTNULLVARCHAR))
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
				  .selectExpression(SQLTypeFactory.mathExpression(10)).add(SQLTypeFactory.constant(10))
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
	
		Assertions.assertEquals("select 10 + 10 from rundml.typetest",stmtText);		

	}
	
	@Test
	void mySQLSelectProviderTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				  .select(SQLTypeFactory.column("dfltInteger"),SQLTypeFactory.column("notNullChar"))
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
	
		Assertions.assertEquals("select dfltInteger,notNullChar from rundml.typetest",stmtText);		

	}

	
	@Test
	void mySQLSelectTableDefinitionTest() {
		
		String stmtText =  RunDMLSQLFactory.createMySQLSelectStatement()
				  .select()
				  .selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				  .selectExpression(SQLTypeFactory.column(NOTNULLDEC72))
				  .selectExpression(SQLTypeFactory.column(NOTNULLDATE))
				  .selectExpression(SQLTypeFactory.column(NOTNULLCHAR))
				  .selectExpression(SQLTypeFactory.column("dfltText"))
				  .selectExpression(SQLTypeFactory.column("dfltBlob"))
				  .selectExpression(SQLTypeFactory.column(NOTNULLVARCHAR))
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
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select all dfltInteger " +
							FROM_CLAUSE,stmtText);

	}


	@Test
	void mySQLDistinctTest() {
		
		String stmtText = RunDMLSQLFactory.createMySQLSelectStatement()
				.select().distinct()
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
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
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all dfltInteger " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.distinct().all()
				.selectExpression(SQLTypeFactory.column(NOTNULLVARCHAR))
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
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select sql_small_result dfltInteger " +
							FROM_CLAUSE,stmtText);

//		INVALID 	
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.column(NOTNULLVARCHAR))
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
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select sql_big_result dfltInteger " +
							FROM_CLAUSE,stmtText);

//		INVALID
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.column(NOTNULLVARCHAR))
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
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select sql_buffer_result dfltInteger " +
							FROM_CLAUSE,stmtText);

//		VALID? - Executed ok in rundml db
		String stmtText2 = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(SQLTypeFactory.column(NOTNULLVARCHAR))
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
				.selectExpression(SQLTypeFactory.constant(10))
				.selectExpression(SQLTypeFactory.constant("This is a string"))
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.selectExpression(new ParmMarker(Types.VARCHAR,"This is a string too"))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select 10,'This is a string',dfltInteger," +
							"? from rundml.typetest",stmtText);

	}

}
