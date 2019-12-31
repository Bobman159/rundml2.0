--=============================================================================
--= Creates the RUNDML database objects used for testing.  This script should =
--= be executed by a database administrator prior to testing.                 =
--=============================================================================
-- This Script should be executed by the Administrator of the Database
CREATE USER IF NOT EXISTS DmlTestUser Password 'Tgbn6929';

CREATE SCHEMA IF NOT EXISTS RUNDML;

GRANT SELECT,INSERT,UPDATE,DELETE ON RUNDML.TYPETEST TO DmlTestUser;

-- Drop the Table 
drop table RUNDML.TypeTest;
commit;

-- Create the table for testing
CREATE TABLE RUNDML.TYPETEST (
    DFLTINTEGER INTEGER DEFAULT 100000,
    NOTNULLMEDIUMINT INTEGER NOT NULL,
    DFLTSIGNED INTEGER DEFAULT 100000,
    DFLTTINYINT TINYINT DEFAULT 50,
    NOTNULLSMINT SMALLINT NOT NULL,
    NOTNULLDEC72 DECIMAL(7,2),
    DFLTNUMBER72 DECIMAL(7,2) DEFAULT 12345.12,
    NOTNULLTIME TIME NOT NULL,
    NOTNULLDATE DATE NOT NULL,
    NOTNULLTIMESTAMP TIMESTAMP NOT NULL,
    NOTNULLDATETIME TIMESTAMP NOT NULL,
    NOTNULLVARCHAR VARCHAR(256) NOT NULL,
    NOTNULLCHAR CHAR(10) NOT NULL,
    DFLTBLOB BLOB NOT NULL,
    DFLTCLOB CLOB NOT NULL,
    NOTNULLBOOLEAN BOOLEAN NOT NULL,
    NOTNULLBOOL BOOLEAN NOT NULL,
    NOTNULLBIT BOOLEAN NOT NULL,
    NOTNULLBINARY BINARY NOT NULL,
    DFLTVARBINARY VARBINARY DEFAULT 2147483647,
    DFLTBIGINT BIGINT DEFAULT 2147483648,
    DFLTINT8 BIGINT DEFAULT 2147483648, 
    NOTNULLIDENTITY IDENTITY (0,1)
--    BIGINT DEFAULT (NEXT VALUE FOR RUNDML.SYSTEM_SEQUENCE_B5B58FEB_10A8_4F5D_9FF8_3E4C9E3ABE04) NOT NULL AUTO_INCREMENT,
--    CONSTRAINT CONSTRAINT_6 PRIMARY KEY (NOTNULLIDENTITY)
);

--CREATE UNIQUE INDEX PRIMARY_KEY_6 ON RUNDML.TYPETEST (NOTNULLIDENTITY);

DELETE FROM RUNDML.TYPETEST;
COMMIT;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob,
 NotNullBoolean, NotNullBool, NotNullBit,
 DfltBigInt, DfltInt8, NotNullBinary, DfltVarBinary   

)
VALUES
(100000,100000,100000,50,
 32767,12345.10,12345.10,TIME '1:1:1',
 DATE '1970-01-01',TIMESTAMP '2000-01-01 12:00:00.000000001',TIMESTAMP '2000-01-01 12:00:00.000000001','Abcdefg',
 '0123456789',CAST ('0123456789' AS BLOB),CAST ('Abcdefghijklmnop' AS clob),
 FALSE, FALSE, FALSE,
 2147483650, 2147483650, 100000, 100000 
 );

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob,
 NotNullBoolean, NotNullBool, NotNullBit,
 DfltBigInt, DfltInt8, NotNullBinary, DfltVarBinary
)
VALUES
(100001,100001,100001,51,
 32766,12345.11,12345.11,TIME '11:11:11',
 DATE '2019-01-01',TIMESTAMP '2019-01-01 12:00:00.000000000',TIMESTAMP '2019-01-01 12:00:00.000000000','Abcdefh',
 '1123456789',CAST ('1123456789' AS BLOB),CAST ('Abcdefghijklmnoq' AS clob),
 FALSE, FALSE, TRUE,
 2147483651, 2147483651, 100001, 100001
 );

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob,
 NotNullBoolean, NotNullBool, NotNullBit,
 DfltBigInt, DfltInt8, NotNullBinary, DfltVarBinary
)
VALUES
(100002,100002,100002,52,
 32765,12345.12,12345.12,TIME '12:12:12',
 DATE '1970-02-02',TIMESTAMP '2000-02-02 12:00:00.000000002',TIMESTAMP '2000-02-02 12:00:00.000000002','Abcdefi',
 '2123456789',CAST ('2123456789' AS BLOB),CAST ('Abcdefghijklmnor' AS clob),
 FALSE, True, FALSE,
 2147483652, 2147483652, 100002, 100002
 );

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob,
 NotNullBoolean, NotNullBool, NotNullBit,
 DfltBigInt, DfltInt8, NotNullBinary, DfltVarBinary
)
VALUES
(100003,100003,100003,53,
 32766,12345.13,12345.13,TIME '13:13:13',
 DATE '1970-03-03',TIMESTAMP '2000-03-03 12:00:00.000000003',TIMESTAMP '2000-03-03 12:00:00.000000003','Abcdefj',
 '3123456789',CAST ('3123456789' AS BLOB),CAST ('Abcdefghijklmnos' AS clob),
 TRUE, FALSE, FALSE,
 2147483653, 2147483653, 100003, 100003
 );

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob,
 NotNullBoolean, NotNullBool, NotNullBit,
 DfltBigInt, DfltInt8, NotNullBinary, DfltVarBinary
)
VALUES
(100004,100004,100004,54,
 32767,12345.14,12345.14,TIME '14:14:14',
 DATE '1970-04-04',TIMESTAMP '2000-04-04 12:00:00.000000004',TIMESTAMP '2000-04-04 12:00:00.000000004','Abcdefk',
 '4123456789',CAST ('4123456789' AS BLOB),CAST ('Abcdefghijklmnos' AS clob),
 TRUE, TRUE, FALSE,
 2147483654, 2147483654, 100004, 100004
 );

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob,
 NotNullBoolean, NotNullBool, NotNullBit,
 DfltBigInt, DfltInt8, NotNullBinary, DfltVarBinary
)
VALUES
(NULL,100005,NULL,NULL,
 32767,12345.15,NULL,TIME '15:15:15',
 DATE '1970-12-12',TIMESTAMP '2000-12-12 12:12:12.000000012',TIMESTAMP '2000-12-12 12:12:12.000000012','Abcdefl',
 '5123456789',CAST ('5123456789' AS BLOB),CAST ('Abcdefghijklmnot' AS clob),
 TRUE, TRUE, FALSE,
 NULL, NULL, 100005, NULL
 );

SELECT *
FROM rundml.TYPETEST;

SELECT TOP 5 DISTINCT NOTNULLSMINT
FROM RUNDML.TYPETEST;