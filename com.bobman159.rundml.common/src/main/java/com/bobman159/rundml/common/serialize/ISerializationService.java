package com.bobman159.rundml.common.serialize;

import com.bobman159.rundml.common.model.ISQLStatement;
import com.bobman159.rundml.common.model.ISelectStatement;
import com.bobman159.rundml.common.model.IStatementSerializer;

/**
 * Define a service for the serialization of RunDML statements to SQL text.
 */
public interface ISerializationService {

	/**
	 * Register a serializer for a SQL statement.  If the SQL statement has already been registered 
	 * with another serializer, registerSerializer will replace the original registration.
	 * @param sqlStatement the SQL statement desctription
	 * @param serializer the 
	 */
//	public void     registerSerializer(Class<?> stmtClazz, Class<?> serializerClazz);
	public void     registerSerializer(ISQLStatement sqlStatement, IStatementSerializer serializer);	

	/*
	Will model.getClass() return the class name of the super class or the type being used?
	IE IF I code model.getClass() does it return ISQLStatement as the class name OR H2SelectStatement?
	Based on quick test it looks like it will return H2SelectStatement
	*/
	public String serializeSelect(ISelectStatement model);
//	public String serializeInsert(IInsertStatement model, AbstractStatementSerializer serializer);
	
}
