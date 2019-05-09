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
	DfltInteger			integer		DEFAULT	100000,
	NotNullMediumInt	MediumInt	NOT null,
	DfltIntUnsigned		integer unsigned		DEFAULT 100000,
	DfltTinyInt			tinyint		DEFAULT 50,
	NotNullSmint		SMALLINT	NOT NULL,
	NotNullDec72		decimal(7,2),	
	NotNullTime			time			NOT NULL,
	NotNullDate			date			NOT NULL,
	NotNullTimestamp	timestamp		NOT NULL,
	NotNullDateTime		datetime		NOT NULL,
	NotNullVarchar		Varchar(256)	NOT NULL,
	NotNullChar			char(10)		NOT NULL,
	NotNullBlob			blob(100)		NOT NULL,
	NotNullText			text(100)		NOT null,
	NotNullBit			bit(4)			not null,
	NotNullBoolean		boolean			not null,
	NotNullBigInt		bigint			not null,
	NotNullBinary		binary			not null,
	NotNullVarBinary	varbinary(8)	not null
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
 NotNullBlob,NotNullText,NotNullBit,NotNullBoolean,
 NotNullBigInt,NotNullBinary,NotNullVarBinary
)
VALUES
(100000,100000,100000,50,
 32760,12345.10,'1:1:1','1970-01-01',
 '2000-01-01 12:00:00.000000001','2000-01-01 12:00:00.000000001','Abcdefg','0123456789',
 CAST('0123456789' AS binary), 'Abcdefghijklmnop',b'00',true,
 2147483650, 0b00, 0b00000000
 )
;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 NotNullBlob,NotNullText,NotNullBit,NotNullBoolean,
 NotNullBigInt,NotNullBinary,NotNullVarBinary
)
VALUES
(100001,100001,100001,51,
 32761,12345.11,'11:11:11','2019-01-01',
 '2019-01-01 12:00:00.000000000','2019-01-01 12:00:00.000000000','Bbcdefg','1123456789',
 CAST('1123456789' AS binary), 'Abcdefghijklmnoq',b'00',false,
 2147483651, 0b01, 0b00000001
 )
;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 NotNullBlob,NotNullText,NotNullBit,NotNullBoolean,
 NotNullBigInt,NotNullBinary,NotNullVarBinary
)
VALUES
(100002,100002,100002,52,
 32762,12345.12,'12:12:12','1970-02-02',
  '2000-02-02 12:00:00.000000002','2000-02-02 12:00:00.000000002','Cbcdefg','2123456789',
 CAST('2123456789' AS binary), 'Abcdefghijklmnor',b'00',true,
 2147483652, 0b01, 0b00000011
 )
;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 NotNullBlob,NotNullText,NotNullBit,NotNullBoolean,
 NotNullBigInt,NotNullBinary,NotNullVarBinary
)
VALUES
(100003,100003,100003,53,
 32763,12345.13,'13:13:13','1970-03-03',
 '2000-03-03 12:00:00.000000003','2000-03-03 12:00:00.000000003','Dbcdefg','3123456789',
 CAST('3123456789' AS binary), 'Abcdefghijklmnos',b'00',true,
 2147483653, 0b11, 0b00000111
 )
;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 NotNullBlob,NotNullText,NotNullBit,NotNullBoolean,
 NotNullBigInt,NotNullBinary,NotNullVarBinary 
)
VALUES
(100004,100004,100004,54,
 32764,12345.14,'14:14:14','1970-04-04',
 '2000-04-04 12:00:00.000000004','2000-04-04 12:00:00.000000004','Dbcdefg','4123456789',
 CAST('4123456789' AS binary), 'Abcdefghijklmnot',b'00',true,
 2147483654, 0b10, 0b00001111
 )
;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltIntUnsigned, DfltTinyInt,
 NotNullSmint,NotNullDec72,NotNullTime,NotNullDate,
 NotNullTimestamp,NotNullDateTime,NotNullVarchar,NotNullChar,
 NotNullBlob,NotNullText,NotNullBit,NotNullBoolean,
 NotNullBigInt,NotNullBinary,NotNullVarBinary 
)
VALUES
(NULL,100005,NULL,NULL,
 32765,12345.15,'15:15:15','1970-05-05',
 '2000-04-04 12:00:00.000000005','2000-04-04 12:00:00.000000005','Dbcdefh','5123456789',
 CAST('5123456789' AS binary), 'Abcdefghijklmnou',b'01',false,
 2147483655, 0b11, 0b00001111
 )
;

select * 
from rundml.typetest;
