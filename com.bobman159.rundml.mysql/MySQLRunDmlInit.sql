-- =============================================================================
-- = Create Test object(s) used by the RunDML JUnit tests                      =
-- = This script should be executed on a MySQL database, once the script has   =
-- = executed successfully, the JUnit Tests in com.bobman159.rundml.jdbc.tests =
-- = should be updated.                                                        =
-- =============================================================================

-- =============================================================================
-- = Define a user id and password for testing                                 =
-- = Once these SQL statements have executed successfully, they can be         =
-- = commented out.                                                            =
-- =============================================================================
-- create user 'RunDmlUser'@'localhost' indentified by 'Tgbn6929';
-- grant select,insert, update, delete on rundml.typetest to 'RunDmlUser'@'localhost';

-- =============================================================================
-- = Delete then create the rundml.typetest table for testing                  =
-- =============================================================================
drop table rundml.typetest;
create table rundml.typetest
(
	DfltInteger		integer		DEFAULT	100000,
	NotNullMediumInt	MediumInt	NOT null,
	DfltIntUnsigned		integer unsigned		DEFAULT 100000,
	DfltTinyInt			tinyint		DEFAULT 50,
	NotNullSmint			SMALLINT	NOT NULL,
	NotNullDec72			decimal(7,2),	
	NotNullTime				time			NOT NULL,
	NotNullDate				date			NOT NULL,
	NotNullTimestamp		timestamp		NOT NULL,
	NotNullDateTime			datetime		NOT NULL,
	NotNullVarchar			Varchar(256)	NOT NULL,
	NotNullChar				char(10)		NOT NULL,
	DfltBlob				blob(100)		NOT NULL,
	DfltText				text(100)		NOT NULL
);

-- =============================================================================
-- = Delete the data from the rundml.typetest table                            =
-- =============================================================================
DELETE FROM RUNDML.TYPETEST;
COMMIT;

-- =============================================================================
-- = Add test data to the rundml.typetest table                                =
-- =============================================================================

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 DfltBlob,DfltText
)
VALUES
(100000,1000000,1000000,50,
 32760,12345.10,CURRENT_TIME,CURRENT_DATE,
 CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Abcdefg','0123456789',
 CAST('0123456789' AS binary), 'Abcdefghijklmnop'
 )
;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 DfltBlob,DfltText
)
VALUES
(100001,1000001,1000001,51,
 32761,12345.11,CURRENT_TIME,CURRENT_DATE,
 CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Bbcdefg','1123456789',
 CAST('1123456789' AS binary), 'Abcdefghijklmnoq'
 )
;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 DfltBlob,DfltText
)
VALUES
(100002,1000002,1000002,52,
 32762,12345.12,CURRENT_TIME,CURRENT_DATE,
 CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Cbcdefg','2123456789',
 CAST('2123456789' AS binary), 'Abcdefghijklmnor'
 )
;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 DfltBlob,DfltText
)
VALUES
(100003,1000003,1000003,53,
 32763,12345.13,CURRENT_TIME,CURRENT_DATE,
 CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Dbcdefg','3123456789',
 CAST('3123456789' AS binary), 'Abcdefghijklmnos'
 )
;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 DfltBlob,DfltText
)
VALUES
(100004,1000004,1000004,54,
 32764,12345.14,CURRENT_TIME,CURRENT_DATE,
 CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Dbcdefg','4123456789',
 CAST('4123456789' AS binary), 'Abcdefghijklmnot'
 )
;
