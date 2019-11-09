package com.bobman159.rundml.sql.h2.mocktables;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.jdbc.mapping.CaseInsensitiveFieldsMap;
import com.bobman159.rundml.jdbc.mapping.IFieldMap;

public class H2MockStringTypeTest implements IFieldMap {
	
	private String	intDflt;
	private String	medIntNotNull;
	private String	signedDflt;
	private String	tinyIntDflt;
	private String	smintNotNull;
	private String	dec72NotNull;	
	private String	num72Dflt;
	private String 	timeNotNull;
	private String	dateNotNull;
	private String	tstampNotNull;
	private String 	dateTstampNotNull;
	private String	varcharNotNull;
	private String	charNotNull;
	private String	blobCol;
	private String	clobCol;
	private String	booleanNotNull;
	private String  boolNotNull;
	private String	bitNotNull;
	private String	bigIntDflt;
	private String	int8Dflt;
	private String	identityNotNull;
	
	public String getIntDflt() {
		return intDflt;
	}
	public String getMedIntNotNull() {
		return medIntNotNull;
	}
	public String getSignedDflt() {
		return signedDflt;
	}
	public String getTinyIntDflt() {
		return tinyIntDflt;
	}
	public String getSmintNotNull() {
		return smintNotNull;
	}
	public String getDec72NotNull() {
		return dec72NotNull;
	}
	public String getNum72Dflt() {
		return num72Dflt;
	}
	public String getTimeNotNull() {
		return timeNotNull;
	}
	public String getDateNotNull() {
		return dateNotNull;
	}
	public String getTstampNotNull() {
		return tstampNotNull;
	}
	public String getDateTstampNotNull() {
		return dateTstampNotNull;
	}
	public String getVarcharNotNull() {
		return varcharNotNull;
	}
	public String getCharNotNull() {
		return charNotNull;
	}
	public String getBlobCol() {
		return blobCol;
	}
	public String getClobCol() {
		return clobCol;
	}
	public String getBooleanNotNull() {
		return booleanNotNull;
	}
	public String getBoolNotNull() {
		return boolNotNull;
	}
	public String getBitNotNull() {
		return bitNotNull;
	}
	public String getBigIntDflt() {
		return bigIntDflt;
	}
	public String getInt8Dflt() {
		return int8Dflt;
	}
	public String getIdentityNotNull() {
		return identityNotNull;
	}
	@Override
	public CaseInsensitiveFieldsMap<String, String> getFieldMappings() {
			
		CaseInsensitiveFieldsMap<String,String> map = new CaseInsensitiveFieldsMap<String,String>();
		map.put("DfltInteger","intDflt");
		map.put("NotNullMediumInt","medIntNotNull");
		map.put("DfltSigned","signedDflt");
		map.put("DfltTinyInt","tinyIntDflt");
		map.put("NotNullSmint","smintNotNull");
		map.put("NotNullDec72","dec72NotNull");
		map.put("DfltNumber72","num72Dflt");
		map.put("NotNullTime","timeNotNull");
		map.put("NotNullDate","dateNotNull");
		map.put("NotNullTimestamp","tstampNotNull");
		map.put("NotNullDateTime","dateTstampNotNull");
		map.put("NotNullVarchar","varcharNotNull");
		map.put("NotNullChar","charNotNull");
		map.put("DfltBlob","blobCol");
		map.put("DfltClob","clobCol");
		map.put("NotNullBoolean","booleanNotNull");
		map.put("NotNullBool","boolNotNull");
		map.put("NotNullBit","bitNotNull");
		map.put("DfltBigInt","bigIntDflt");
		map.put("DfltInt8","int8Dflt");
		map.put("NotNullIdentity","identityNotNull");
		
		return map;
	}

	
	
}
