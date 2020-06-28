package com.bobman159.rundml.sql.h2.tests;

import java.sql.Connection;
import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.testFactory;
import com.bobman159.rundml.core.predicates.impl.PredicateBuilder;
import com.bobman159.rundml.core.sql.types.impl.ParmMarker;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.sql.factory.RunDMLSQLFactory;
import com.bobman159.rundml.sql.factory.RunDMLSQLTestFactory;
import com.bobman159.rundml.sql.h2.mocktables.H2MockPrimitivesTypeTest;
import com.bobman159.rundml.sql.h2.mocktables.H2MockStringTypeTest;
import com.bobman159.rundml.sql.h2.mocktables.TypeTest;

/* 
 *	*	Unless noted otherwise, the SQL syntax generated and checked 
 *		was verified as executable in H2
 *
 */
class H2SelectSyntaxStatementTests {

	private static Connection conn;
	private static DefaultConnectionProvider provider;
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
	
	private final RunDMLSQLTestFactory testFactory = RunDMLSQLTestFactory.getInstance();

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
	void h2SelectClassNoIFieldMapTest() {
		
		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				  .select(TypeTest.class)
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
	
		Assertions.assertEquals("select DfltInteger,NotNullMediumInt,DfltSigned,DfltTinyInt,NotNullSmint," 	+ 
								"NotNullDec72,DfltNumber72,NotNullTime,NotNullDate,NotNullTimestamp," 		+ 
								"NotNullDateTime,NotNullVarchar,NotNullChar,DfltBlob,DfltClob,NotNullBoolean," + 
								"NotNullBool,NotNullBit,DfltBigInt,DfltInt8,NotNullIdentity,NotNullBinary,DfltVarBinary " +
								"from rundml.typetest",
								stmtText);		

	}
	
	@Test
	void h2SelectTableDefinitionTest() {
		String stmtText =  RunDMLSQLFactory.createH2SelectStatement()				
				  .select()
				  .selectExpression(testFactory.column(DFLTINTEGER))
				  .selectExpression(testFactory.column(NOTNULLDEC72))
				  .selectExpression(testFactory.column(NOTNULLDATE))
				  .selectExpression(testFactory.column(NOTNULLCHAR))
				  .selectExpression(testFactory.column("dfltSigned"))
				  .selectExpression(testFactory.column("dfltTinyInt"))
				  .selectExpression(testFactory.column(NOTNULLVARCHAR))				  
				  .from(RUNDML_SCHEMA,RUNDML_TABLE)
				  .getStatementText();
		
		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullDate," + 
						    "notNullChar,dfltSigned,dfltTinyInt,notNullVarchar "	   + 
							FROM_CLAUSE,stmtText);		
	}
	
	@Test
	void h2SelectExpressionTest() {
		/*
		 * Test each of the supported ISQLType types.
		 * The expression type combinations (math, concat, etc) are tested by 
		 * the com.bobman159.rundml.core.types.tests JUnits.
		 */
		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(10))
				.selectExpression(testFactory.constant("This is a string"))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.selectExpression(new ParmMarker(Types.VARCHAR,"This is a string too"))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select 10,'This is a string',dfltInteger," +
							"? from rundml.typetest",stmtText);

	}
	
	@Test
	void h2SelectTopTest() {

		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.top(testFactory.constant(5))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select top 5 dfltInteger " +
							FROM_CLAUSE,stmtText);
		
		String stmtText2 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.top(testFactory.constant(NUMERIC_LITERAL))
				.selectExpression(testFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select top '0123456789' notNullVarchar " +
							FROM_CLAUSE,stmtText2);
		
	}

	@Test
	void h2SelectTopDistinctTest() {

		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.top(testFactory.constant(5)).distinct()
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select top 5 distinct dfltInteger " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.top(testFactory.constant(NUMERIC_LITERAL)).distinct()
				.selectExpression(testFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select top '0123456789' distinct notNullVarchar " +
							FROM_CLAUSE,stmtText2);

	}
	
	@Test
	void h2SelectTopAllTest() {

		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.top(testFactory.constant(5)).all()
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select top 5 all dfltInteger " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.top(testFactory.constant("5")).all()
				.selectExpression(testFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select top '5' all notNullVarchar " +
							FROM_CLAUSE,stmtText2);

	}
	
	@Test
	void h2SelectDistinctAllTest() {
		
		//SELECT DISTINCT ALL is not valid in H2
		//This test should return no rows  (possibly throw an exception?)
		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.distinct().all()
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all dfltInteger " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.distinct().all()
				.selectExpression(testFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all notNullVarchar " +
							FROM_CLAUSE,stmtText2);

	}
	
	@Test
	void h2SelectWhereTest() {
		
		PredicateBuilder pred = PredicateBuilder.where(testFactory.column(DFLTINTEGER))
								  .isGreater(100000)
								  .and(testFactory.column(NOTNULLDEC72))
								  .isGreaterOrEqual(12345.10)
								  .or(testFactory.column(NOTNULLDATE))
								  .isEqual("2019-03-15")
								  .or(testFactory.column(NOTNULLVARCHAR))
								  .isGreaterOrEqual(ABCDEFG_LITERAL)
								  .or(testFactory.column(NOTNULLVARCHAR))
								  .isEqual(testFactory.parm(Types.BIGINT, "This is a test"))
								  .build();
								  
		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
								.select()
								.selectExpression(testFactory.column(DFLTINTEGER))
								.selectExpression(testFactory.column(NOTNULLDEC72))
								.selectExpression(testFactory.column(NOTNULLDATE))
								.selectExpression(testFactory.column(NOTNULLVARCHAR))
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
	void h2SelectGroupByTest() {
		
		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(testFactory.constant(100000),
						 testFactory.constant(ABCDEFG_LITERAL),
						 testFactory.parm(Types.DECIMAL, 100000),
						 testFactory.column(DFLTINTEGER))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "group by 100000,'Abcdefg',?,dfltInteger",stmtText);

	}
	
	@Test
	void h2SelectOrderByTest() {

		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(1),testFactory.orderBy(2),testFactory.orderBy(3))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,3",stmtText);
		
		String stmtText2 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(1),
						 testFactory.orderBy(2),
						 testFactory.orderBy(testFactory.column(DFLTINTEGER)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,dfltInteger",stmtText2);		
		
		String stmtText3 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(testFactory.constant(ABCDEFG_LITERAL)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 'Abcdefg'",stmtText3);
		
		String stmtText4 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(1).asc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1 asc",stmtText4);
		
		String stmtText5 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(testFactory.column(DFLTINTEGER)).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger desc",stmtText5);

		String stmtText6 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(1),testFactory.orderBy(2).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText6);		

		String stmtText7 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(1),testFactory.orderBy(2).desc().nullsLast())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc nulls last",stmtText7);		

		String stmtText8 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(testFactory.column(DFLTINTEGER)),
						 testFactory.orderBy(testFactory.column(NOTNULLVARCHAR)).desc()
						 			.nullsFirst())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger,notNullVarchar desc nulls first",stmtText8);	

		String stmtText9 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.column(DFLTINTEGER))
				.selectExpression(testFactory.column(NOTNULLDEC72))
				.selectExpression(testFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(testFactory.column(NOTNULLVARCHAR)).desc()
								   .nullsLast(),
						 testFactory.orderBy(1).asc().nullsFirst(),
						 testFactory.orderBy(testFactory.column(NOTNULLDEC72)))
				.getStatementText();

		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullVarchar " +
				     FROM_CLAUSE_SPACE +
				     "order by notNullVarchar desc nulls last,1 asc nulls first," +
				     "notNullDec72",stmtText9);
		
		String stmtText10 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(100000))
				.selectExpression(testFactory.constant(ABCDEFG_LITERAL))
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(testFactory.orderBy(testFactory.column(DFLTINTEGER)).asc()
								   .nullsFirst(),
						 testFactory.orderBy(2).desc().nullsLast(),
						 testFactory.orderBy(3).desc().nullsFirst(),
						 testFactory.orderBy(testFactory.parm(Types.TINYINT,10)).asc()
						 		   .nullsLast())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger asc nulls first,2 desc nulls last," +
				     "3 desc nulls first,? asc nulls last",stmtText10);

	}
	
	@Test
	void h2SelectHavingTest() {
				
		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(testFactory.column(DFLTINTEGER))
				.having(PredicateBuilder.having(testFactory.column(DFLTINTEGER)).isGreater(100000)
								 .build())
				.getStatementText();

		Assertions.assertEquals("select dfltInteger " +
				     FROM_CLAUSE_SPACE +
				     "group by dfltInteger HAVING dfltInteger > 100000",stmtText);

		String stmtText2 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(testFactory.column(NOTNULLCHAR))
				.having(PredicateBuilder.having(testFactory.column(NOTNULLCHAR)).isGreater(NUMERIC_LITERAL)
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by notNullChar HAVING notNullChar > '0123456789'",stmtText2);

		String stmtText3 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(testFactory.column(NOTNULLCHAR))
				.having(PredicateBuilder.having(testFactory.column(NOTNULLCHAR))
								 .isGreaterOrEqual(NUMERIC_LITERAL)
								 .or(testFactory.column(NOTNULLCHAR)).isEqual("223456789")
								 .and(testFactory.column(NOTNULLCHAR)).isLess("1123456789")
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by notNullChar HAVING notNullChar >= '0123456789' " +
				     "OR notNullChar = '223456789' AND notNullChar < " +
				     "'1123456789'",stmtText3);

		String stmtText4 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(testFactory.constant("Abcdef"),
						 testFactory.constant("Hiklmnop"))
				.having(PredicateBuilder.having("Abcdef")
								 .isEqual("Abcdef2")
								 .or("Hijklmnop").isGreater("Hijklmno")
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by 'Abcdef','Hiklmnop' HAVING 'Abcdef' = 'Abcdef2' " +
				     "OR 'Hijklmnop' > 'Hijklmno'",stmtText4);

		String stmtText5 = RunDMLSQLFactory.createH2SelectStatement()
				.select()
				.selectExpression(testFactory.constant(10))
				.selectExpression(testFactory.constant(20))
				.selectExpression(testFactory.constant(30))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(testFactory.constant(20),
						 testFactory.constant(30),
						 testFactory.constant(10))
				.having(PredicateBuilder.having(20)
								 .isEqual(20)
								 .and(20).isGreater(10)
								 .and(10).isLess(30)
								 .and(testFactory.parm(Types.CHAR, ABCDEFG_LITERAL)).isEqual("Abcdefg")
								 .build())
				.getStatementText();

		Assertions.assertEquals("select 10,20,30 " +
				     FROM_CLAUSE_SPACE +
				     "group by 20,30,10 HAVING 20 = 20 " +
				     "AND 20 > 10 AND 10 < 30 " + 
				     "AND ? = 'Abcdefg'",stmtText5);		

	}
	
	@Test
	void h2SelectLimitTest() {

		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select(H2MockPrimitivesTypeTest.class)
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(testFactory.constant(5))
				.getStatementText();

		Assertions.assertEquals("select DfltInteger,NotNullMediumInt,DfltSigned,DfltTinyInt,NotNullSmint," + 
								"NotNullDec72,DfltNumber72,NotNullTime,NotNullDate,NotNullTimestamp," + 
								"NotNullDateTime,NotNullVarchar,NotNullChar,DfltBlob,DfltClob,NotNullBoolean," + 
								"NotNullBool,NotNullBit,DfltBigInt,DfltInt8,NotNullIdentity,NotNullBinary,DfltVarBinary " 
								+ FROM_CLAUSE_SPACE + 
								"limit 5",stmtText);
		
	}
	
	@Test
	void h2SelectLimitOffsetTest() {

		String stmtText = RunDMLSQLFactory.createH2SelectStatement()
				.select(H2MockStringTypeTest.class)
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(testFactory.constant(5)).offset(testFactory.constant(1))
				.getStatementText();

		Assertions.assertEquals("select DfltInteger,NotNullMediumInt,DfltSigned,DfltTinyInt,NotNullSmint," + 
								"NotNullDec72,DfltNumber72,NotNullTime,NotNullDate,NotNullTimestamp," + 
								"NotNullDateTime,NotNullVarchar,NotNullChar,DfltBlob,DfltClob,NotNullBoolean," + 
								"NotNullBool,NotNullBit,DfltBigInt,DfltInt8,NotNullIdentity,NotNullBinary,DfltVarBinary " + 
								FROM_CLAUSE_SPACE + "limit 5 offset 1",stmtText);

	}
	
}
