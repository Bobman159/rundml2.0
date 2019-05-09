-- This Script should be executed by the Administrator of the Database
CREATE USER IF NOT EXISTS DmlTestUser Password 'Tgbn6929';

CREATE SCHEMA IF NOT EXISTS RUNDML;

drop table rundml.TypeTest;
commit;

CREATE TABLE IF NOT EXISTS RUNDML.TypeTest 
(
	DfltInteger			integer		DEFAULT	100000,
	NotNullMediumInt	MediumInt	NOT NULL,
	DfltSigned			signed		DEFAULT 100000,
	DfltTinyInt			tinyint		DEFAULT 50,
	NotNullSmint		SMALLINT	NOT NULL,
	NotNullDec72		decimal(7,2),	
	DfltNumber72		number(7,2)		DEFAULT 12345.12,
	NotNullTime			time			NOT NULL,
	NotNullDate			date			NOT NULL,
	NotNullTimestamp	timestamp		NOT NULL,
	NotNullDateTime		datetime		NOT NULL,
	NotNullVarchar		Varchar(256)	NOT NULL,
	NotNullChar			char(10)		NOT NULL,
	DfltBlob			blob(100)		NOT NULL,
	DfltClob			clob(100)		NOT NULL,
	NotNullBoolean		boolean			NOT NULL,
	NotNullBool			bool			NOT NULL,
	NotNullBit			bit				NOT NULL,
	DfltBigInt			bigint			DEFAULT 2147483648,
	DfltInt8			int8			DEFAULT 2147483648,
	NotNullIdentity		IDENTITY		NOT NULL	
);


GRANT ALL ON RUNDML.TypeTest TO DmlTestUser;