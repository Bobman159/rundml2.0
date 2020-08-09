package com.bobman159.rundml.sql.base.builder.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.predicates.IPredicatesList;
import com.bobman159.rundml.core.predicates.impl.PredicateBuilder;
import com.bobman159.rundml.core.sql.ISQLTypeFactory;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;
import com.bobman159.rundml.sql.factory.SQLStatementBuilderFactory;
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
	
	private final ISQLTypeFactory typeFactory = SQLTypeFactory.getInstance();

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
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(TypeTest.class)
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.toSQL();
	
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
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(TypeTest.class)
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.toSQL();
	
		Assertions.assertEquals("select DfltInteger,NotNullMediumInt,DfltSigned,DfltTinyInt,NotNullSmint," + 
								"NotNullDec72,DfltNumber72,NotNullTime,NotNullDate,NotNullTimestamp," + 
								"NotNullDateTime,NotNullVarchar,NotNullChar,DfltBlob,DfltClob,NotNullBoolean," + 
								"NotNullBool,NotNullBit,DfltBigInt,DfltInt8,NotNullIdentity,NotNullBinary,DfltVarBinary " +
								"from rundml.typetest",
								stmtText);

	}
	
	@Test
	void compatibleSelectStringTest() {
		
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select("dfltinteger","notnullmediumint","dflttinyint")
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.toSQL();
	
		Assertions.assertEquals("select dfltinteger,notnullmediumint,dflttinyint from rundml.typetest",stmtText);

	}
	
	@Test
	void compatibleSelectExpressionTest() {
		/*
		 * Test each of the supported ISQLType types.
		 * The expression type combinations (math, concat, etc) are tested by 
		 * the com.bobman159.rundml.core.types.tests JUnits.
		 */
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(10),typeFactory.constant("This is a string"),typeFactory.column(DFLTINTEGER),
				typeFactory.parm(Types.VARCHAR,"This is a string too"))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.toSQL();
			
		Assertions.assertEquals("select 10,'This is a string',dfltInteger," +
							"? from rundml.typetest",stmtText);

	}
	
	@Test
	void compatSelectTableDefinitionTest() {
				
		String stmtText =  SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.column(DFLTINTEGER),typeFactory.column(NOTNULLDEC72),typeFactory.column(NOTNULLDATE),
						typeFactory.column(NOTNULLCHAR),typeFactory.column("dfltSigned"),typeFactory.column("dfltTinyint"),
						typeFactory.column(NOTNULLVARCHAR))
				.from("rundml", "typetest")
				.toSQL();

		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullDate," + 
						    "notNullChar,dfltSigned,dfltTinyint,notNullVarchar "	   + 
							FROM_CLAUSE,stmtText);		

	}
	
	@Test
	void comptibleSelectAllTest() {
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select().all()
				.selectExpression(typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.toSQL();
		
		Assertions.assertEquals("select all dfltInteger " + FROM_CLAUSE,stmtText);

		String stmtText2 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select()
				.all()
				.selectExpression(typeFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.toSQL();
		
		Assertions.assertEquals("select all notNullVarchar " +
							FROM_CLAUSE,stmtText2);

	}
	
	@Test
	void compatibleSelectDistinctAllTest() {
		
		//This test should return no rows  (possibly throw an exception?)
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select()
				.distinct().all()
				.selectExpression(typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.toSQL();
		
		Assertions.assertEquals("select distinct all dfltInteger " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select()
				.distinct().all()
				.selectExpression(typeFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.toSQL();
		
		Assertions.assertEquals("select distinct all notNullVarchar " +
							FROM_CLAUSE,stmtText2);

	}
	

	@Test
	void compatibleSelectWhereTest() {
		
		IPredicatesList pred = PredicateBuilder.where(typeFactory.column(DFLTINTEGER))
				  .isGreater(100000)
				  .and(typeFactory.column(NOTNULLDEC72))
				  .isGreaterOrEqual(12345.10)
				  .or(typeFactory.column(NOTNULLDATE))
				  .isEqual("2019-03-15")
				  .or(typeFactory.column(NOTNULLVARCHAR))
				  .isGreaterOrEqual(ABCDEFG_LITERAL)
				  .or(typeFactory.column(NOTNULLVARCHAR))
				  .isEqual(typeFactory.parm(Types.BIGINT, "This is a test"))
				  .build();
					  
	
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
						.select(typeFactory.column(DFLTINTEGER),typeFactory.column(NOTNULLDEC72),
								typeFactory.column(NOTNULLDATE),typeFactory.column(NOTNULLVARCHAR))
						.from(RUNDML_SCHEMA,RUNDML_TABLE)
						.where(pred)
						.toSQL();
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
		
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(typeFactory.constant(100000),
						 typeFactory.constant(ABCDEFG_LITERAL),
						 typeFactory.parm(Types.DECIMAL, 100000),
						 typeFactory.column(DFLTINTEGER))
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "group by 100000,'Abcdefg',?,dfltInteger",stmtText);

	}
	
	@Test
	void compatibleSelectOrderByTest() {
		
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(1),typeFactory.orderBy(2),typeFactory.orderBy(3))
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,3",stmtText);
		
		String stmtText2 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(1),
						 typeFactory.orderBy(2),
						 typeFactory.orderBy(typeFactory.column(DFLTINTEGER)))
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,dfltInteger",stmtText2);		
		
		String stmtText3 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(typeFactory.constant(ABCDEFG_LITERAL)))
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 'Abcdefg'",stmtText3);
		
		String stmtText4 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(1).asc())
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1 asc",stmtText4);
		
		String stmtText5 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(typeFactory.column(DFLTINTEGER)).desc())
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger desc",stmtText5);

		String stmtText6 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(1),typeFactory.orderBy(2).desc())
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText6);		

		String stmtText7 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(1),typeFactory.orderBy(2).desc().nullsLast())
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc nulls last",stmtText7);		

		String stmtText8 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(typeFactory.column(DFLTINTEGER)),
						 typeFactory.orderBy(typeFactory.column(NOTNULLVARCHAR)).desc()
						 			.nullsFirst())
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger,notNullVarchar desc nulls first",stmtText8);	

		String stmtText9 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.column(DFLTINTEGER),typeFactory.column(NOTNULLDEC72),typeFactory.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(typeFactory.column(NOTNULLVARCHAR)).desc()
								   .nullsLast(),
						 typeFactory.orderBy(1).asc().nullsFirst(),
						 typeFactory.orderBy(typeFactory.column(NOTNULLDEC72)))
				.toSQL();

		Assertions.assertEquals("select dfltInteger,notNullDec72,notNullVarchar " +
				     FROM_CLAUSE_SPACE +
				     "order by notNullVarchar desc nulls last,1 asc nulls first," +
				     "notNullDec72",stmtText9);
		
		String stmtText10 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(100000),typeFactory.constant(ABCDEFG_LITERAL),typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(typeFactory.orderBy(typeFactory.column(DFLTINTEGER)).asc()
								   .nullsFirst(),
						 typeFactory.orderBy(2).desc().nullsLast(),
						 typeFactory.orderBy(3).desc().nullsFirst(),
						 typeFactory.orderBy(typeFactory.parm(Types.TINYINT,10)).asc()
				 		   .nullsLast())				
				.toSQL();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by dfltInteger asc nulls first,2 desc nulls last," +
				     "3 desc nulls first,? asc nulls last",stmtText10);

	}

	
	@Test
	void compatibleSelectHavingTest() {
		
		String stmtText = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(typeFactory.column(DFLTINTEGER))
				.having(PredicateBuilder.having(typeFactory.column(DFLTINTEGER)).isGreater(100000)
								 .build())
				.toSQL();

		Assertions.assertEquals("select dfltInteger " +
				     FROM_CLAUSE_SPACE +
				     "group by dfltInteger HAVING dfltInteger > 100000",stmtText);

		String stmtText2 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(typeFactory.column(NOTNULLCHAR))
				.having(PredicateBuilder.having(typeFactory.column(NOTNULLCHAR)).isGreater(NUMERIC_LITERAL)
								 .build())
				.toSQL();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by notNullChar HAVING notNullChar > '0123456789'",stmtText2);

		String stmtText3 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(typeFactory.column(NOTNULLCHAR))
				.having(PredicateBuilder.having(typeFactory.column(NOTNULLCHAR))
								 .isGreaterOrEqual(NUMERIC_LITERAL)
								 .or(typeFactory.column(NOTNULLCHAR)).isEqual("223456789")
								 .and(typeFactory.column(NOTNULLCHAR)).isLess("1123456789")
								 .build())
				.toSQL();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by notNullChar HAVING notNullChar >= '0123456789' " +
				     "OR notNullChar = '223456789' AND notNullChar < " +
				     "'1123456789'",stmtText3);

		String stmtText4 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(typeFactory.constant("Abcdef"),
						 typeFactory.constant("Hiklmnop"))
				.having(PredicateBuilder.having("Abcdef")
								 .isEqual("Abcdef2")
								 .or("Hijklmnop").isGreater("Hijklmno")
								 .build())
				.toSQL();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by 'Abcdef','Hiklmnop' HAVING 'Abcdef' = 'Abcdef2' " +
				     "OR 'Hijklmnop' > 'Hijklmno'",stmtText4);

		String stmtText5 = SQLStatementBuilderFactory.createBaseSelectStatement()
				.select(typeFactory.constant(10),typeFactory.constant(20),typeFactory.constant(30))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(typeFactory.constant(20),
						 typeFactory.constant(30),
						 typeFactory.constant(10))
					.having(PredicateBuilder.having(20)
								 .isEqual(20)
								 .and(20).isGreater(10)
								 .and(10).isLess(30)
								 .and(typeFactory.parm(Types.CHAR, ABCDEFG_LITERAL)).isEqual(ABCDEFG_LITERAL)
								 .build())
				.toSQL();

		Assertions.assertEquals("select 10,20,30 " +
				     FROM_CLAUSE_SPACE +
				     "group by 20,30,10 HAVING 20 = 20 " +
				     "AND 20 > 10 AND 10 < 30 " + 
				     "AND ? = 'Abcdefg'",stmtText5);

	}

}
