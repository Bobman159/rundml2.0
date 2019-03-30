DELETE FROM RUNDML.TYPETEST;
COMMIT;

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob
)
VALUES
(100000,100000,100000,50,
 32767,12345.10,12345.10,CURRENT_TIME,
 CURRENT_DATE,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Abcdefg',
 '0123456789',CAST ('0123456789' AS BLOB),CAST ('Abcdefghijklmnop' AS clob)
 );

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob
)
VALUES
(100001,100001,100001,51,
 32766,12345.11,12345.11,CURRENT_TIME,
 CURRENT_DATE,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Abcdefh',
 '1123456789',CAST ('1123456789' AS BLOB),CAST ('Abcdefghijklmnoq' AS clob)
 );

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob
)
VALUES
(100002,100002,100002,52,
 32765,12345.12,12345.12,CURRENT_TIME,
 CURRENT_DATE,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Abcdefi',
 '2123456789',CAST ('2123456789' AS BLOB),CAST ('Abcdefghijklmnor' AS clob)
 );

INSERT INTO RUNDML.TypeTest 
(DfltInteger, NotNullMediumInt, DfltSigned, DfltTinyInt,
 NotNullSmint, NotNullDec72, DfltNumber72, NotNullTime,
 NotNullDate,NotNullTimestamp,NotNullDateTime,NotNullVarchar,
 NotNullChar,DfltBlob,DfltClob
)
VALUES
(100000,100000,100000,50,
 32767,12345.10,12345.10,CURRENT_TIME,
 CURRENT_DATE,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'Abcdefg',
 '0123456789',CAST ('0123456789' AS BLOB),CAST ('Abcdefghijklmnop' AS clob)
 );

SELECT *
FROM rundml.TYPETEST;

SELECT TOP 5 DISTINCT NOTNULLSMINT
FROM RUNDML.TYPETEST;