-- This Script should be executed by the Administrator of the Database
CREATE USER IF NOT EXISTS DmlTestUser Password 'Tgbn6929';

CREATE SCHEMA IF NOT EXISTS RUNDML;

CREATE TABLE IF NOT EXISTS RUNDML.TypeTest 
(
	DfltInteger		integer		DEFAULT	100000,
	NotNullMediumInt	MediumInt	NOT NULL,
	DfltSigned			signed		DEFAULT 100000,
	DfltTinyInt			tinyint		DEFAULT 50,
	NotNullSmint			SMALLINT	NOT NULL,
	NotNullDec72			decimal(7,2),	
	DfltNumber72			number(7,2)		DEFAULT 12345.12,
	NotNullTime				time			NOT NULL,
	NotNullDate				date			NOT NULL,
	NotNullTimestamp		timestamp		NOT NULL,
	NotNullDateTime			datetime		NOT NULL,
	NotNullVarchar			Varchar(256)	NOT NULL,
	NotNullChar				char(10)		NOT NULL,
	DfltBlob				blob(100)		NOT NULL,
	DfltClob				clob(100)		NOT NULL
);


GRANT ALL ON RUNDML.TypeTest TO DmlTestUser;