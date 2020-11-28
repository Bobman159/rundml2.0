package com.bobman159.rundml.common.serialize;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.common.model.ISQLStatement;
import com.bobman159.rundml.common.model.ISelectStatement;
import com.bobman159.rundml.common.model.IStatementSerializer;

/**
 * SQL Statement Serialization Factory class for SELECT, INSERT, UPDATE 
 * and DELETE statements.  
 *
 */
public class SQLStatementSerializerService implements ISerializationService {
	private static SQLStatementSerializerService self;
	private HashMap<String,AbstractStatementSerializer> factoryMap;
	private static Logger logger = LogManager.getLogger(SQLStatementSerializerService.class);
	
	/**
	 * 
	 * @return an instance of this factory
	 */
	public static SQLStatementSerializerService getInstance() {
		if (self == null) {
			self = new SQLStatementSerializerService();
		}
		return self;
	}
	
	private SQLStatementSerializerService() {
		factoryMap = new HashMap<>();
		//Register Default Serializer Implementations 
//		registerSerializer(AbstractStatementSerializer.GENERIC_SELECT,GenericSelectSerializer.class);
//		registerSerializer(GenericSelectSerializer.class);

	}
	
	/**
	 * Obtain a SQL Serializer for an SQL statement
	 * @param dialect the dialect or specific SQL syntax (H2, MySQL etc) to create
	 * @return the serializer to use for the statement
	 * 
	 * 
	 */
	/*
	public AbstractStatementSerializer getSerializer(ISQLStatement statement) {
		
		Class<? extends AbstractStatementSerializer> serializerClass = null;
		AbstractStatementSerializer serializer = null;
		String dialect = statement.getDialect();
		
		if (factoryMap.containsKey(dialect)) {
			serializerClass = factoryMap.get(dialect);
			if (AbstractStatementSerializer.class.isAssignableFrom(serializerClass)) {
				try {
						serializer = serializerClass.getDeclaredConstructor().newInstance();
						serializer.setStatement(statement);
				} catch (SecurityException | IllegalAccessException |
						IllegalArgumentException | InstantiationException ex) {	
					//TODO: log exception using a logger
					ex.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO log exception using a logger
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO log exception using a logger
					e.printStackTrace();
				}
			} else {
				throw new IllegalStateException("serializerClassName: " + serializerClass.getCanonicalName()
						+ " does not extend " + AbstractStatementSerializer.class.getName());
			}
			return serializer;
		} else {
			throw new UnsupportedOperationException("dialect: " + dialect + " has no registered factories");
		}
	}
	*/
	/**
	 * Register a Java class as the serializer for an SQL statement dialect
	 * @param sqlStatement the SQL statement syntax to be serialized 
	 * @see com.bobman159.rundml.common.serialize.AbstractStatementSerializer
	 * @param serializerClazz the java class to use for serialization 
	 * 
//	 * <b> serializerClazz is created using java reflection in the getSerializer method.  
//	 * The serializerClazz should implement a <code>serializerClazzName(</code>) constructor.</b>
	 * stmtSerializer an instance of <code>IStatementSerializer</code> to use for serialization 
	 * 
	 */
	@Override
//	public void registerSerializer(String sqlStatement,Class<? extends AbstractStatementSerializer> serializerClazz) {
	public void registerSerializer(ISQLStatement sqlStatement, IStatementSerializer stmtSerializer) {

//		Class<? extends AbstractStatementSerializer> serializerClass = null;
//		AbstractStatementSerializer serializer = null;
//		AbstractStatementSerializer serializer = serializerClazz.cast(serializerClazz);
		
//		String dialect = stmtSerializer.getDialect();
//		String sqlStatementName = sqlStatement.getClass().getName();
		
		if (factoryMap.containsKey(sqlStatement.getClass().getCanonicalName())) {
//			serializerClass = factoryMap.get(sqlStatementName);
//			if (AbstractStatementSerializer.class.isAssignableFrom(serializerClass)) {
			if (stmtSerializer instanceof AbstractStatementSerializer) {
//				try {
//						serializer = serializerClass.getDeclaredConstructor().newInstance();
////						serializer.setStatement(statement);
//						serializer.setStatement(sqlStatement);
//				} catch (SecurityException | IllegalAccessException |
//						IllegalArgumentException | InstantiationException |
//						InvocationTargetException | NoSuchMethodException ex) {
//					logger.error(ex.getMessage(), ex);
//					ex.printStackTrace();
//				} catch (InvocationTargetException e) {
//					logger.error(e.getMessage(), e);
//				} catch (NoSuchMethodException e) {
//					
//				}
			} else {
//				throw new IllegalStateException("serializerClassName: " + serializerClass.getCanonicalName()
//						+ " does not extend " + AbstractStatementSerializer.class.getName());
				throw new IllegalStateException("serializerClassName: " + stmtSerializer.getClass().getCanonicalName()
				+ " does not extend " + AbstractStatementSerializer.class.getName());
			}
//			return serializer;
		} else {
			throw new UnsupportedOperationException("SQL statemnt: " + sqlStatement.getClass().getCanonicalName() + 
													" has no registered factories");
		}
	}

	@Override
	public String serializeSelect(ISelectStatement selectStmt) //, AbstractStatementSerializer serializer) 
	{
		String sqlStmt = null;
		if (factoryMap.containsKey(selectStmt.getClass().getCanonicalName())) {
			IStatementSerializer serializer = factoryMap.get(selectStmt.getClass().getCanonicalName());
			sqlStmt = serializer.serialize();
		} else {
			throw new UnsupportedOperationException("select statement: " + selectStmt.getClass().getCanonicalName() +
					" has no registered serializer ");
		}
		return sqlStmt;
	}

}
