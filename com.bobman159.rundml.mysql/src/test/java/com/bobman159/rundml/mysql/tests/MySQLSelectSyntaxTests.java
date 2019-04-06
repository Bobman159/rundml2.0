package com.bobman159.rundml.mysql.tests;

import java.sql.Connection;
import java.sql.Types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.mysql.MySQLSQLStatement;
import com.bobman159.rundml.mysql.select.builder.MySQLSelectStatement;

class MySQLSelectSyntaxTests {
	
	private static TableDefinition tbDef;
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
	private static final String SELECT_1000 = "select 100000,'Abcdefg',DFLTINTEGER ";
	private static final String SELECT_NOTNULLCHAR = "select NOTNULLCHAR ";
	private static final String NUMERIC_LITERAL = "0123456789";
	
	@BeforeAll
	static void setUpBeforeClass() {

		tbDef = new TableDefinition(RUNDML_SCHEMA,RUNDML_TABLE);
		tbDef.addColumn(DFLTINTEGER, Types.INTEGER);
		tbDef.addColumn(NOTNULLDEC72, Types.DECIMAL);
		tbDef.addColumn(NOTNULLDATE, Types.INTEGER);
		tbDef.addColumn(NOTNULLCHAR, Types.CHAR);
		tbDef.addColumn("dfltText", Types.CHAR);
		tbDef.addColumn("dfltBlob", Types.BLOB);
		tbDef.addColumn(NOTNULLVARCHAR, Types.VARCHAR);
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
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(tbDef.column(DFLTINTEGER))
				.having(Predicate.having(tbDef.column(DFLTINTEGER)).isGreater(100000)
								 .build()))
				.toStmt();

		Assertions.assertEquals("select DFLTINTEGER " +
				     FROM_CLAUSE_SPACE +
				     "group by DFLTINTEGER HAVING DFLTINTEGER > 100000",stmtText);

		String stmtText2 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(tbDef.column(NOTNULLCHAR))
				.having(Predicate.having(tbDef.column(NOTNULLCHAR)).isGreater(NUMERIC_LITERAL)
								 .build()))
				.toStmt();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by NOTNULLCHAR HAVING NOTNULLCHAR > '0123456789'",stmtText2);

		String stmtText3 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(tbDef.column(NOTNULLCHAR))
				.having(Predicate.having(tbDef.column(NOTNULLCHAR))
								 .isGreaterOrEqual(NUMERIC_LITERAL)
								 .or(tbDef.column(NOTNULLCHAR)).isEqual("223456789")
								 .and(tbDef.column(NOTNULLCHAR)).isLess("1123456789")
								 .build()))
				.toStmt();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by NOTNULLCHAR HAVING NOTNULLCHAR >= '0123456789' " +
				     "OR NOTNULLCHAR = '223456789' AND NOTNULLCHAR < " +
				     "'1123456789'",stmtText3);

		String stmtText4 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.string("Abcdef"),
						 Expression.string("Hiklmnop"))
				.having(Predicate.having("Abcdef")
								 .isEqual("Abcdef2")
								 .or("Hijklmnop").isGreater("Hijklmno")
								 .build()))
				.toStmt();

		Assertions.assertEquals(SELECT_NOTNULLCHAR +
				     FROM_CLAUSE_SPACE +
				     "group by 'Abcdef','Hiklmnop' HAVING 'Abcdef' = 'Abcdef2' " +
				     "OR 'Hijklmnop' > 'Hijklmno'",stmtText4);

		String stmtText5 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
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
								 .build()))
				.toStmt();

		Assertions.assertEquals("select 10,20,30 " +
				     FROM_CLAUSE_SPACE +
				     "group by 2,3,1 HAVING 20 = 20 " +
				     "AND 20 > 10 AND 10 < 30 " + 
				     "AND ? = 'Abcdefg'",stmtText5);

	}

	@Test
	void mySQLOffsetTest() {
		

		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.selectStar()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(Expression.number(5)).offset(Expression.number(1)))
				.toStmt();

		Assertions.assertEquals("select * " + FROM_CLAUSE_SPACE + "limit 5 offset 1",stmtText);

	}


	@Test
	void mySQLGroupByTest() {
		
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.number(1),
						 Expression.string(ABCDEFG_LITERAL),
						 Expression.parm(Types.DECIMAL, 100000),
						 tbDef.column(DFLTINTEGER)))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "group by 1,'Abcdefg',?,DFLTINTEGER",stmtText);

	}

	@Test
	void mySQLLimitTest() {
		
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.selectStar()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(Expression.number(5)))
				.toStmt();

		Assertions.assertEquals("select * " + FROM_CLAUSE_SPACE + "limit 5",stmtText);

	}

	@Test
	void mySQLOrderByTest() {
		
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2),Expression.orderBy(3)))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,3",stmtText);
		
		String stmtText2 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),
						 Expression.orderBy(2),
						 Expression.orderBy(tbDef.column(DFLTINTEGER))))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2,DFLTINTEGER",stmtText2);		
		
		String stmtText3 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(Expression.string(ABCDEFG_LITERAL))))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 'Abcdefg'",stmtText3);
		
		String stmtText4 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1).asc()))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1 asc",stmtText4);
		
		String stmtText5 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(DFLTINTEGER)).desc()))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER desc",stmtText5);

		String stmtText6 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2).desc()))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText6);		

		
		String stmtText7 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2).desc()))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc",stmtText7);		


		String stmtText8 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(DFLTINTEGER)),
						 Expression.orderBy(tbDef.column(NOTNULLVARCHAR)).desc()))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER,NOTNULLVARCHAR desc",stmtText8);	

		String stmtText9 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.selectExpression(tbDef.column(NOTNULLDEC72))
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(NOTNULLVARCHAR)).desc(),
						 Expression.orderBy(1).asc(),
						 Expression.orderBy(tbDef.column(NOTNULLDEC72))))
				.toStmt();

		Assertions.assertEquals("select DFLTINTEGER,NOTNULLDEC72,NOTNULLVARCHAR " +
				     FROM_CLAUSE_SPACE +
				     "order by NOTNULLVARCHAR desc,1 asc," +
				     "NOTNULLDEC72",stmtText9);
		
		String stmtText10 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(DFLTINTEGER)).asc(),
						 Expression.orderBy(2).desc(),
						 Expression.orderBy(3).desc(),
						 Expression.orderBy(Expression.parm(Types.TINYINT,10)).asc()))				
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER asc,2 desc," +
				     "3 desc,? asc",stmtText10);

	}

	@Test
	void mySQLWhereTest() {
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
				  

		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
						.select()
						.selectExpression(tbDef.column(DFLTINTEGER))
						.selectExpression(tbDef.column(NOTNULLDEC72))
						.selectExpression(tbDef.column(NOTNULLDATE))
						.selectExpression(tbDef.column(NOTNULLVARCHAR))
						.from(RUNDML_SCHEMA,RUNDML_TABLE)
						.where(pred))
						.toStmt();
		
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
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				  .selectStar()
				  .from(RUNDML_SCHEMA,RUNDML_TABLE))
				  .toStmt();
	
		Assertions.assertEquals("select * from rundml.typetest",stmtText);		

	}
	
	@Test
	void mySQLSelectProviderTest() {
		
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(provider)
				  .selectStar()
				  .from(RUNDML_SCHEMA,RUNDML_TABLE))
				  .toStmt();
	
		Assertions.assertEquals("select * from rundml.typetest",stmtText);		

	}

	
	@Test
	void mySQLSelectTableDefinitionTest() {
		
		String stmtText =  ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				  .select(tbDef))
				  .toStmt();
		
		Assertions.assertEquals("select DFLTINTEGER,NOTNULLDEC72,NOTNULLDATE," + 
						    "NOTNULLCHAR,DFLTTEXT,DFLTBLOB,NOTNULLVARCHAR "	   + 
							FROM_CLAUSE,stmtText);

	}


	@Test
	void mySQLAllTest() {
		
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.all()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select all DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

	}


	@Test
	void mySQLDistinctTest() {
		
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select().distinct()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();

		Assertions.assertEquals("select distinct DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

	}
	
	@Test
	void mySQLAllDistinctTest() {
		
		//SELECT DISTINCT ALL is not valid in MySQL
		//This test should return no rows  (possibly throw an exception?)
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.distinct().all()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select distinct all DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.distinct().all()
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select distinct all NOTNULLVARCHAR " +
							FROM_CLAUSE,stmtText2);
		
	}


	@Test
	void mySQLsmallResultTest() {
		
//		VALID
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.smallResult()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select sql_small_result DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

//		INVALID 	
		String stmtText2 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.smallResult()
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select NOTNULLVARCHAR sql_small_result " +
							FROM_CLAUSE,stmtText2);

	}

	@Test
	void mySQLbigResultTest() {
		
//		VALID
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.bigResult()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select sql_big_result DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

//		INVALID
		String stmtText2 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.bigResult()
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select NOTNULLVARCHAR sql_big_result " +
							FROM_CLAUSE,stmtText2);

	}

	@Test
	void mySQLbufferResultTest() {
		
//		VALID
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.bufferResult()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select sql_buffer_result DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

//		VALID? - Executed ok in rundml db
		String stmtText2 = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.bufferResult()
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select NOTNULLVARCHAR sql_buffer_result " +
							FROM_CLAUSE,stmtText2);

	}

	@Test
	void mySQLselectExpressionTest() {
		
		String stmtText = ((MySQLSelectStatement) MySQLSQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(10))
				.selectExpression(Expression.string("This is a string"))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.selectExpression(new ParmMarker(Types.VARCHAR,"This is a string too"))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select 10,'This is a string',DFLTINTEGER," +
							"? from rundml.typetest",stmtText);

	}

}
