package com.bobman159.rundml.h2.select.builder.tests;

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
import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.h2.H2SQLStatement;
import com.bobman159.rundml.h2.select.builder.H2SelectStatement;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;

/* 
 *	*	Unless noted otherwise, the SQL syntax generated and checked 
 *		was verified as executable in H2
 *
 */
class H2SelectSyntaxStatementTests {

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
		tbDef.addColumn("dfltSigned", Types.INTEGER);
		tbDef.addColumn("dfltTinyInt", Types.TINYINT);
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
	void h2SelectStarTest() {
		
		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				  .selectStar()
				  .from(RUNDML_SCHEMA,RUNDML_TABLE))
				  .toStmt();
	
		Assertions.assertEquals("select * from rundml.typetest",stmtText);		

	}
	
	@Test
	void mySQLSelectProviderTest() {
		
		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(provider)
				  .selectStar()
				  .from(RUNDML_SCHEMA,RUNDML_TABLE))
				  .toStmt();
	
		Assertions.assertEquals("select * from rundml.typetest",stmtText);		

	}
	
	@Test
	void h2SelectTableDefinitionTest() {
		String stmtText =  ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				  .select(tbDef))
				  .toStmt();
		
		Assertions.assertEquals("select DFLTINTEGER,NOTNULLDEC72,NOTNULLDATE," + 
						    "NOTNULLCHAR,DFLTSIGNED,DFLTTINYINT,NOTNULLVARCHAR "	   + 
							FROM_CLAUSE,stmtText);		
	}
	
	@Test
	void h2SelectExpressionTest() {
		/*
		 * Test each of the supported IExpression types.
		 * The expression type combinations (math, concat, etc) are tested by 
		 * the com.bobman159.rundml.core.exprtypes.tests JUnits.
		 */
		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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
	
	@Test
	void h2SelectTopTest() {

		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.top(Expression.number(5))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select top 5 DFLTINTEGER " +
							FROM_CLAUSE,stmtText);
		
		String stmtText2 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.top(Expression.string(NUMERIC_LITERAL))
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select top '0123456789' NOTNULLVARCHAR " +
							FROM_CLAUSE,stmtText2);
		
	}

	@Test
	void h2SelectTopDistinctTest() {

		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.top(Expression.number(5)).distinct()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select top 5 distinct DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.top(Expression.string(NUMERIC_LITERAL)).distinct()
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select top '0123456789' distinct NOTNULLVARCHAR " +
							FROM_CLAUSE,stmtText2);

	}
	
	@Test
	void h2SelectTopAllTest() {

		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.top(Expression.number(5)).all()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select top 5 all DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.top(Expression.string("5")).all()
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select top '5' all NOTNULLVARCHAR " +
							FROM_CLAUSE,stmtText2);

	}
	
	@Test
	void h2SelectDistinctAllTest() {
		
		//SELECT DISTINCT ALL is not valid in H2
		//This test should return no rows  (possibly throw an exception?)
		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.distinct().all()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select distinct all DFLTINTEGER " +
							FROM_CLAUSE,stmtText);

		String stmtText2 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.distinct().all()
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE))
				.toStmt();
		
		Assertions.assertEquals("select distinct all NOTNULLVARCHAR " +
							FROM_CLAUSE,stmtText2);

	}
	
	@Test
	void h2SelectWhereTest() {
		
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
								  
		
		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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
	void h2SelectGroupByTest() {
		
		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.groupBy(Expression.number(100000),
						 Expression.string(ABCDEFG_LITERAL),
						 Expression.parm(Types.DECIMAL, 100000),
						 tbDef.column(DFLTINTEGER)))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "group by 100000,'Abcdefg',?,DFLTINTEGER",stmtText);

	}
	
	@Test
	void h2SelectOrderByTest() {

		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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
		
		String stmtText2 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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
		
		String stmtText3 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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
		
		String stmtText4 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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
		
		String stmtText5 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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

		String stmtText6 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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

		String stmtText7 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(1),Expression.orderBy(2).desc().nullsLast()))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by 1,2 desc nulls last",stmtText7);		

		String stmtText8 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.selectExpression(Expression.number(100000))
				.selectExpression(Expression.string(ABCDEFG_LITERAL))
				.selectExpression(tbDef.column(DFLTINTEGER))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(DFLTINTEGER)),
						 Expression.orderBy(tbDef.column(NOTNULLVARCHAR)).desc()
						 			.nullsFirst()))
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER,NOTNULLVARCHAR desc nulls first",stmtText8);	

		String stmtText9 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.select()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.selectExpression(tbDef.column(NOTNULLDEC72))
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.orderBy(Expression.orderBy(tbDef.column(NOTNULLVARCHAR)).desc()
								   .nullsLast(),
						 Expression.orderBy(1).asc().nullsFirst(),
						 Expression.orderBy(tbDef.column(NOTNULLDEC72))))
				.toStmt();

		Assertions.assertEquals("select DFLTINTEGER,NOTNULLDEC72,NOTNULLVARCHAR " +
				     FROM_CLAUSE_SPACE +
				     "order by NOTNULLVARCHAR desc nulls last,1 asc nulls first," +
				     "NOTNULLDEC72",stmtText9);
		
		String stmtText10 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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
						 		   .nullsLast()))				
				.toStmt();

		Assertions.assertEquals(SELECT_1000 +
				     FROM_CLAUSE_SPACE +
				     "order by DFLTINTEGER asc nulls first,2 desc nulls last," +
				     "3 desc nulls first,? asc nulls last",stmtText10);

	}
	
	@Test
	void h2SelectHavingTest() {
				
		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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

		String stmtText2 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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

		String stmtText3 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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

		String stmtText4 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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

		String stmtText5 = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
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
								 .and(Expression.parm(Types.CHAR, ABCDEFG_LITERAL)).isEqual("Abcdefg")
								 .build()))
				.toStmt();

		Assertions.assertEquals("select 10,20,30 " +
				     FROM_CLAUSE_SPACE +
				     "group by 20,30,10 HAVING 20 = 20 " +
				     "AND 20 > 10 AND 10 < 30 " + 
				     "AND ? = 'Abcdefg'",stmtText5);		

	}
	
	@Test
	void h2SelectLimitTest() {

		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.selectStar()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(Expression.number(5)))
				.toStmt();

		Assertions.assertEquals("select * " + FROM_CLAUSE_SPACE + "limit 5",stmtText);
		
	}
	
	@Test
	void h2SelectLimitOffsetTest() {

		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				.selectStar()
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.limit(Expression.number(5)).offset(Expression.number(1)))
				.toStmt();

		Assertions.assertEquals("select * " + FROM_CLAUSE_SPACE + "limit 5 offset 1",stmtText);

	}
	
	
	@Test
	void h2SelectLimitSampleSizeTest() {
		/* SELECT * FROM RUNDML.TYPETEST
			LIMIT 5 sample_size 3;
		*/

		//Sample size is not implemented at this time
	}
	
	
	@Test
	void h2SelectLimitOffsetSampleSizeTest() {
//		SELECT * 
//		FROM RUNDML.TYPETEST
//		LIMIT 5 OFFSET 1 sample_size 3;

		//Sample size is not implemented at this time

	}


	
}
