-- This Script should be executed by the Administrator of the Database
--CREATE USER IF NOT EXISTS DmlTestUser Password 'Tgbn6929';

CREATE SCHEMA IF NOT EXISTS TEST2;

drop table TEST2.TypeTest;
commit;

CREATE TABLE IF NOT EXISTS TEST2.TypeTest 
(
	DfltInteger			integer		DEFAULT	100000,
	DFLTInteger         integer     DEFAULT 100001

);