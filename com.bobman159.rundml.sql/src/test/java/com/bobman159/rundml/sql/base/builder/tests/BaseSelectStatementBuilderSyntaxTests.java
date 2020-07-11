package com.bobman159.rundml.sql.base.builder.tests;

import java.sql.Connection;
import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.predicates.impl.PredicateBuilder;
import com.bobman159.rundml.core.predicates.impl.PredicatesList;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.sql.factory.RunDMLSQLFactory;
import com.bobman159.rundml.sql.h2.mocktables.TypeTest;

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
	private static final String SELECT_1000 = "select 100000,'Abcdefg',dfltInteger ";
	private static final String SELECT_NOTNULLCHAR = "select notNullChar ";
	private static final String NUMERIC_LITERAL = "0123456789";
	
	@BeforeAll
	static void setUpBeforeClass() {

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
	void compatibleSelectExpressionClassTest() {
		
		@SuppressWarnings("unchecked")
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(TypeTest.class)
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
	
		Assertions.assertEquals("select DfltInteger,NotNullMediumInt,DfltSigned,DfltTinyInt,NotNullSmint," + 
								"NotNullDec72,DfltNumber72,NotNullTime,NotNullDate,NotNullTimestamp," + 
								"NotNullDateTime,NotNullVarchar,NotNullChar,DfltBlob,DfltClob,NotNullBoolean," + 
								"NotNullBool,NotNullBit,DfltBigInt,DfltInt8,NotNullIdentity,NotNullBinary,DfltVarBinary " + 
								"from rundml.typetest",
								stmtText);

	}

	@Test
	void compatibleSelectClassTest() {
		
		@SuppressWarnings("unchecked")
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(TypeTest.class)
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
	
		Assertions.assertEquals("select DfltInteger,NotNullMediumInt,DfltSigned,DfltTinyInt,NotNullSmint," + 
								"NotNullDec72,DfltNumber72,NotNullTime,NotNullDate,NotNullTimestamp," + 
								"NotNullDateTime,NotNullVarchar,NotNullChar,DfltBlob,DfltClob,NotNullBoolean," + 
								"NotNullBool,NotNullBit,DfltBigInt,DfltInt8,NotNullIdentity,NotNullBinary,DfltVarBinary " +
								"from rundml.typetest",
								stmtText);

	}
	
	@Test
	void compatibleSelectStringTest() {
		
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select("dfltinteger","notnullmediumint","dflttinyint")
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
	
		Assertions.assertEquals("select dfltinteger,notnullmediumint,dflttinyint from rundml.typetest",stmtText);

	}
	
	@Test
	void compatibleSelectExpressionTest() {
		/*
		 * Test each of the supported ISQLType types.
		 * The expression type combinations (math, concat, etc) are tested by 
		 * the com.bobman159.rundml.core.types.tests JUnits.
		 */
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(SQLTypeFactory.constant(10),SQLTypeFactory.constant("This is a string"),SQLTypeFactory.column(DFLTINTEGER),
				SQLTypeFactory.parm(Types.VARCHAR,"This is a string too"))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
			
		Assertions.assertEquals("select 10,'This is a string',dfltInteger," +
							"? from rundml.typetest",stmtText);

	}
	
	@Test
	void compatSelectTableDefinitionTest() {
				
		String stmtText =  RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(SQLTypeFactory.column(DFLTINTEGER),SQLTypeFactory.column(NOTNULLDEC72),SQLTypeFactory.column(NOTNULLDATE),
						SQLTypeFactory.column(NOTNULLCHAR),SQLTypeFactory.column("dfltSigned"),SQLTypeFactory.column("dfltTinyint"),
						SQLTypeFactory.column(NOTNULLVARCHAR))
				.from("rundml", "typetest")
				.getStatementText();

		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullDate," + 
						    "notNullChar,dfltSigned,dfltTinyint,notNullVarchar "	   + 
							FROM_CLAUSE,stmtText);		

	}
	
	@Test
	void comptibleSelectAllTest() {
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select().all()
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select all dfltInteger " + FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select()
				.all()
				.selectExpression(SQLTypeFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select all notNullVarchar " +
							FROM_CLAUSE,stmtText2);

	}
	
	@Test
	void compatibleSelectDistinctAllTest() {
		
		//This test should return no rows  (possibly throw an exception?)
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select()
				.distinct().all()
				.selectExpression(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all dfltInteger " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select()
				.distinct().all()
				.selectExpression(SQLTypeFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.getStatementText();
		
		Assertions.assertEquals("select distinct all notNullVarchar " +
							FROM_CLAUSE,stmtText2);

	}
	

	@Test
	void compatibleSelectWhereTest() {
		
		PredicatesList pred = PredicateBuilder.where(SQLTypeFactory.column(DFLTINTEGER))
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
					  
	
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(conn)
						.select(SQLTypeFactory.column(DFLTINTEGER),SQLTypeFactory.column(NOTNULLDEC72),
								SQLTypeFactory.column(NOTNULLDATE),SQLTypeFactory.column(NOTNULLVARCHAR))
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
	void compatibleSelectGroupByTest() {
		
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.constant(100000),
						 SQLTypeFactory.constant(ABCDEFG_LITERAL),
						 SQLTypeFactory.parm(Types.DECIMAL, 100000),
						 SQLTypeFactory.column(DFLTINTEGER))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "group by 100000,'Abcdefg',?,dfltInteger",stmtText);

	}
	
	@Test
	void compatibleSelectOrderByTest() {
		
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1),SQLTypeFactory.orderBy(2),SQLTypeFactory.orderBy(3))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,3",stmtText);
		
		String stmtText2 = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1),
						 SQLTypeFactory.orderBy(2),
						 SQLTypeFactory.orderBy(SQLTypeFactory.column(DFLTINTEGER)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,dfltInteger",stmtText2);		
		
		String stmtText3 = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.constant(ABCDEFG_LITERAL)))
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 'Abcdefg'",stmtText3);
		
		String stmtText4 = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1).asc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1 asc",stmtText4);
		
		String stmtText5 = RunDMLSQLFactory.createBaseSelectStatement(conn)
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.column(DFLTINTEGER)).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger desc",stmtText5);

		String stmtText6 = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1),SQLTypeFactory.orderBy(2).desc())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText6);		

		String stmtText7 = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(1),SQLTypeFactory.orderBy(2).desc().nullsLast())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc nulls last",stmtText7);		

		String stmtText8 = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.column(DFLTINTEGER)),
						 SQLTypeFactory.orderBy(SQLTypeFactory.column(NOTNULLVARCHAR)).desc()
						 			.nullsFirst())
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger,notNullVarchar desc nulls first",stmtText8);	

		String stmtText9 = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.column(DFLTINTEGER),SQLTypeFactory.column(NOTNULLDEC72),SQLTypeFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.column(NOTNULLVARCHAR)).desc()
								   .nullsLast(),
						 SQLTypeFactory.orderBy(1).asc().nullsFirst(),
						 SQLTypeFactory.orderBy(SQLTypeFactory.column(NOTNULLDEC72)))
				.getStatementText();

		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullVarchar " +
				     FROM_CLAUSE_SPACE +
				     "order by notNullVarchar desc nulls last,1 asc nulls first," +
				     "notNullDec72",stmtText9);
		
		String stmtText10 = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.constant(100000),SQLTypeFactory.constant(ABCDEFG_LITERAL),SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(SQLTypeFactory.orderBy(SQLTypeFactory.column(DFLTINTEGER)).asc()
								   .nullsFirst(),
						 SQLTypeFactory.orderBy(2).desc().nullsLast(),
						 SQLTypeFactory.orderBy(3).desc().nullsFirst(),
						 SQLTypeFactory.orderBy(SQLTypeFactory.parm(Types.TINYINT,10)).asc()
				 		   .nullsLast())				
				.getStatementText();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger asc nulls first,2 desc nulls last," +
				     "3 desc nulls first,? asc nulls last",stmtText10);

	}

	
	@Test
	void compatibleSelectHavingTest() {
		
		String stmtText = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.column(DFLTINTEGER))
				.having(PredicateBuilder.having(SQLTypeFactory.column(DFLTINTEGER)).isGreater(100000)
								 .build().toString())
				.getStatementText();

		Assertions.assertEquals("select dfltInteger " +
				     FROM_CLAUSE_SPACE +
				     "group by dfltInteger HAVING dfltInteger > 100000",stmtText);

		String stmtText2 = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.column(NOTNULLCHAR))
				.having(PredicateBuilder.having(SQLTypeFactory.column(NOTNULLCHAR)).isGreater(NUMERIC_LITERAL)
								 .build())
				.getStatementText();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by notNullChar HAVING notNullChar > '0123456789'",stmtText2);

		String stmtText3 = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.column(NOTNULLCHAR))
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

		String stmtText4 = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.column(NOTNULLCHAR))
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

		String stmtText5 = RunDMLSQLFactory.createBaseSelectStatement()
				.select(SQLTypeFactory.constant(10),SQLTypeFactory.constant(20),SQLTypeFactory.constant(30))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(SQLTypeFactory.constant(20),
						 SQLTypeFactory.constant(30),
						 SQLTypeFactory.constant(10))
					.having(PredicateBuilder.having(20)
								 .isEqual(20)
								 .and(20).isGreater(10)
								 .and(10).isLess(30)
								 .and(SQLTypeFactory.parm(Types.CHAR, ABCDEFG_LITERAL)).isEqual(ABCDEFG_LITERAL)
								 .build())
				.getStatementText();

		Assertions.assertEquals("select 10,20,30 " +
				     FROM_CLAUSE_SPACE +
				     "group by 20,30,10 HAVING 20 = 20 " +
				     "AND 20 > 10 AND 10 < 30 " + 
				     "AND ? = 'Abcdefg'",stmtText5);

	}

}
