package com.bobman159.rundml.sql.factory;

import java.util.HashMap;

import com.bobman159.rundml.core.model.ISQLStatement;
import com.bobman159.rundml.core.sql.impl.AbstractStatementSerializer;
import com.bobman159.rundml.sql.serialization.GenericSelectSerializer;

/**
 * SQL Statement Serialization Factory class for SELECT, INSERT, UPDATE 
 * and DELETE statements.  
 *
 */
public class SQLStatementSerializerFactory {
	private static SQLStatementSerializerFactory self;
	private HashMap<String,Class<? extends AbstractStatementSerializer>> factoryMap;
	
	/**
	 * 
	 * @return an instance of this factory
	 */
	public static SQLStatementSerializerFactory getInstance() {
		if (self == null) {
			self = new SQLStatementSerializerFactory();
		}
		return self;
	}
	
	private SQLStatementSerializerFactory() {
		factoryMap = new HashMap<>();
		//Register Default Serializer Implementations 
		registerSerializer(AbstractStatementSerializer.GENERIC_SELECT,GenericSelectSerializer.class);
	}
	
	/**
	 * 
	 * @param dialect the dialect or specific SQL syntax (H2, MySQL etc) to create
	 * @param statement the statement to use for serialization
	 * @return the seralizer to use for the statement
	 */
	public AbstractStatementSerializer getSerializer(String dialect,ISQLStatement statement) {
		
		Class<? extends AbstractStatementSerializer> serializerClass = null;
		AbstractStatementSerializer serializer = null;
		
		if (factoryMap.containsKey(dialect)) {
			serializerClass = factoryMap.get(dialect);
			if (AbstractStatementSerializer.class.isAssignableFrom(serializerClass)) {
				try {
						serializer = serializerClass.newInstance();
						serializer.setStatement(statement);
				} catch (SecurityException | IllegalAccessException |
						IllegalArgumentException | InstantiationException ex) {	
					//TODO: log exception using a logger
					ex.printStackTrace();
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
	
	/**
	 * Register a Java class as the serializer for an SQL statement dialect
	 * @param sqlStatement the SQL statement syntax to be serialized 
	 * @see com.bobman159.rundml.core.sql.impl.AbstractStatementSerializer
	 * @param clazz the java class to use for serialization 
	 */
	public void registerSerializer(String sqlStatement,Class<? extends AbstractStatementSerializer> clazz) {
		
		if (factoryMap.containsKey(sqlStatement)) {
			factoryMap.replace(sqlStatement, clazz);
		} else {
			factoryMap.put(sqlStatement, clazz);
		}
	}

}
