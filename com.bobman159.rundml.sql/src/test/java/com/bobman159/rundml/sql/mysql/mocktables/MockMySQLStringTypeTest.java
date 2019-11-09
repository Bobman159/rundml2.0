package com.bobman159.rundml.sql.mysql.mocktables;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.jdbc.mapping.CaseInsensitiveFieldsMap;
import com.bobman159.rundml.jdbc.mapping.IFieldMap;

public class MockMySQLStringTypeTest implements IFieldMap {


	private String 	DfltInteger;
	private String 	medIntNotNull;
	private String	unsignedDflt;
	private String	tinyIntDflt;
	private String	NotNullSmint;
	private String	NotNullDec72;	
	private String	timeNotNull;
	private String	NotNullDate;
	private String	NotNullTimestamp;
	private String	NotNullDateTime;
	private String	NotNullVarchar;
	private String	charNotNull;
	private String	NotNullBlob;
	private String	lobCharCol;
	private String	booleanNotNull;
	private String	bitNotNull;
	private String	NotNullBigInt;
	private String	binaryNotNull;
	private String	NotNullVarBinary;
	
	public String getDfltInteger() {
		return DfltInteger;
	}
	public String getMedIntNotNull() {
		return medIntNotNull;
	}
	public String getUnsignedDflt() {
		return unsignedDflt;
	}
	public String getTinyIntDflt() {
		return tinyIntDflt;
	}
	public String getNotNullSmint() {
		return NotNullSmint;
	}
	public String getNotNullDec72() {
		return NotNullDec72;
	}
	public String getTimeNotNull() {
		return timeNotNull;
	}
	public String getNotNullDate() {
		return NotNullDate;
	}
	public String getNotNullTimestamp() {
		return NotNullTimestamp;
	}
	public String getNotNullDateTime() {
		return NotNullDateTime;
	}
	public String getNotNullVarchar() {
		return NotNullVarchar;
	}
	public String getCharNotNull() {
		return charNotNull;
	}
	public String getNotNullBlob() {
		return NotNullBlob;
	}
	public String getLobCharCol() {
		return lobCharCol;
	}
	public String getBooleanNotNull() {
		return booleanNotNull;
	}
	public String getBitNotNull() {
		return bitNotNull;
	}
	public String getNotNullBigInt() {
		return NotNullBigInt;
	}
	public String getBinaryNotNull() {
		return binaryNotNull;
	}
	public String getNotNullVarBinary() {
		return NotNullVarBinary;
	}
	@Override
	public CaseInsensitiveFieldsMap<String, String> getFieldMappings() {
		
		CaseInsensitiveFieldsMap<String,String> map = new CaseInsensitiveFieldsMap<String,String>();
		map.put("NotNullMediumInt","medIntNotNull");
		map.put("DfltIntUnsigned","unsignedDflt");
		map.put("DfltTinyInt","tinyIntDflt");
		map.put("NotNullTime","timeNotNull");
		map.put("NotNullChar","charNotNull");
		map.put("NotNullText","lobCharCol");
		map.put("NotNullBoolean","booleanNotNull");
		map.put("NotNullBit","bitNotNull");
		map.put("NotNullBinary","binaryNotNull");
		
		return map;
	}
	
	
	
}
