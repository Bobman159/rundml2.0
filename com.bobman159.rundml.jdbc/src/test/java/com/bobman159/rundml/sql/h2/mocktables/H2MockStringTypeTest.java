package com.bobman159.rundml.sql.h2.mocktables;

import com.bobman159.rundml.core.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.mapping.exceptions.IFieldMap;

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
	private String	binaryNotNull;
	private String	varBinaryDflt;
	
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
	public String getBinaryNotNull() {
		return binaryNotNull;
	}
	public String getVarBinaryDflt() {
		return varBinaryDflt;
	}
	
	@Override
	public FieldMapDefinitionList getFieldMappings(FieldMapDefinitionList fieldList) {
		
		fieldList.addDefinition("DfltInteger","intDflt");
		fieldList.addDefinition("NotNullMediumInt","medIntNotNull");
		fieldList.addDefinition("DfltSigned","signedDflt");
		fieldList.addDefinition("DfltTinyInt","tinyIntDflt");
		fieldList.addDefinition("NotNullSmint","smintNotNull");
		fieldList.addDefinition("NotNullDec72","dec72NotNull");
		fieldList.addDefinition("DfltNumber72","num72Dflt");
		fieldList.addDefinition("NotNullTime","timeNotNull");
		fieldList.addDefinition("NotNullDate","dateNotNull");
		fieldList.addDefinition("NotNullTimestamp","tstampNotNull");
		fieldList.addDefinition("NotNullDateTime","dateTstampNotNull");
		fieldList.addDefinition("NotNullVarchar","varcharNotNull");
		fieldList.addDefinition("NotNullChar","charNotNull");
		fieldList.addDefinition("DfltBlob","blobCol");
		fieldList.addDefinition("DfltClob","clobCol");
		fieldList.addDefinition("NotNullBoolean","booleanNotNull");
		fieldList.addDefinition("NotNullBool","boolNotNull");
		fieldList.addDefinition("NotNullBit","bitNotNull");
		fieldList.addDefinition("DfltBigInt","bigIntDflt");
		fieldList.addDefinition("DfltInt8","int8Dflt");
		fieldList.addDefinition("NotNullIdentity","identityNotNull");
		fieldList.addDefinition("NotNullBinary","binaryNotNull");
		fieldList.addDefinition("DfltVarBinary","varBinaryDflt");
		
		return fieldList;

	}
	
}
