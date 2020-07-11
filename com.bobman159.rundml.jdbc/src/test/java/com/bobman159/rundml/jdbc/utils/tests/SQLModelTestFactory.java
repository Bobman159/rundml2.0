package com.bobman159.rundml.jdbc.utils.tests;

import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.core.sql.impl.SQLClauses.SQLClause;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * A singleton factory class that creates <code>SQLModelFactory</code> instances for testing
 *
 */
public class SQLModelTestFactory {
	
	private static SQLModelTestFactory self = null;
	private static final String DFLTINTEGER = "DfltInteger";
	private static final String NOTNULLMEDINT = "NotNullMediumInt";
	private static final String DFLTSIGNED = "DfltSigned";
	private static final String DFLTINTUNSIGNED = "DfltIntUnsigned";	//MySQL TypeTest
	private static final String DFLTTINYINT = "DfltTinyInt";
	private static final String NOTNULLSMINT = "NotNullSmint";
	private static final String NOTNULLDEC72 = "NotNullDec72";	
	private static final String DFLTNUM72 = "DfltNumber72";

	private static final String NOTNULLTIME = "NotNullTime";
	private static final String NOTNULLDATE = "NotNullDate";
	private static final String NOTNULLTSTAMP = "NotNullTimestamp";
	private static final String NOTNULLDATETIME = "NotNullDateTime";
	private static final String NOTNULLVARCHAR = "NotNullVarchar";
	private static final String NOTNULLCHAR = "NotNullChar";
	private static final String DFLTBLOB = "DfltBlob";
	private static final String NOTNULLBLOB = "NotNullBlob";		//MySQL rundml.typetest
	private static final String NOTNULLTEXT = "NotNullText";		//MySQL rundml.typetest

	private static final String DFLTCLOB = "DfltClob";
	private static final String NOTNULLBOOLEAN = "NotNullBoolean";
	private static final String NOTNULLBOOL = "NotNullBool";
	private static final String NOTNULLBIT = "NotNullBit";
	private static final String NOTNULLBIGINT = "NotNullBigInt";	//MySQL rundml.typetest
	private static final String DFLTBIGINT = "DfltBigInt";
	private static final String DFLTINT8 = "DfltInt8";
	private static final String NOTNULLIDENTITY = "NotNullIdentity";
	private static final String NOTNULLBINARY = "NotNullBinary";	//MySQL rundml.typetest
	private static final String NOTNULLVARBINARY = "NotNullVarBinary";	//MySQL rundml.typetest
	private static final String DFLTVARBINARY = "DfltVarBinary";
	
	private static final String RUNDML_SCHEMA = "rundml";
	private static final String TYPETEST_TABLE = "typetest";
	
	/**
	 * Obtain the instance of this factory class
	 * @return
	 */
	public static SQLModelTestFactory getInstance() {
		if (self == null) {
			self = new SQLModelTestFactory();
		}
		return self;
	}
	
	/**
	 * Creates a basic SELECT statement model for the rundml.TypeTest in the H2 database.
	 * Only column names are used for the SELECT expression list.
	 * @return a SELECT statement SQL model
	 */
	public SQLStatementModel createH2SelectTypeTestModel() {

		SQLStatementModel model = new SQLStatementModel();
		model.addClause(SQLClause.SELECT);
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTINTEGER));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLMEDINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTSIGNED));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTTINYINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLSMINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLDEC72));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTNUM72));

		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLTIME));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLDATE));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLTSTAMP));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLDATETIME));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLVARCHAR));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLCHAR));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTBLOB));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTCLOB));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLBOOLEAN));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLBOOL));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLBIT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTBIGINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTINT8));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLBINARY));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTVARBINARY));
		model.addClause(SQLClause.FROM, CoreUtils.qualifiedTbName(RUNDML_SCHEMA, TYPETEST_TABLE));

		return model;
	}
	
	/**
	 * Creates a basic SELECT statement model for the rundml.TypeTest in the MySQL database.
	 * Only column names are used for the SELECT expression list.
	 * @return a SELECT statement SQL model
	 */
	public SQLStatementModel createMySQLSelectTypeTestModel() {
				
		SQLStatementModel model = new SQLStatementModel();
		model.addClause(SQLClause.SELECT);
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTINTEGER));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLMEDINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTINTUNSIGNED));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(DFLTTINYINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLSMINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLDEC72));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLTIME));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLDATE));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLTSTAMP));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLDATETIME));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLVARCHAR));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLCHAR));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLBLOB));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLTEXT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLBOOLEAN));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLBIT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLBIGINT));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLBINARY));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column(NOTNULLVARBINARY));
		model.addClause(SQLClause.FROM, CoreUtils.qualifiedTbName(RUNDML_SCHEMA, TYPETEST_TABLE));

		return model;
	}
	
	/**
	 * Creates a basic SELECT statement model for the rundml.TypeTest table., 
	 * An undefined column name is used for the SELECT expression list.
	 * @return a SELECT statement SQL model
	 */
	public SQLStatementModel createUndefinedColumnSelectModel() {
		
		SQLStatementModel model = new SQLStatementModel();
		model.addClause(SQLClause.SELECT);
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column("Col01"));
		model.addClause(SQLClause.FROM, CoreUtils.qualifiedTbName(RUNDML_SCHEMA, TYPETEST_TABLE));

		return model;
	}
 	
}
