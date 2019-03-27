CREATE TABLE SERVERTABLE1 (
	FirstName NVARCHAR(100),
	LastName NVARCHAR(100),
	Gender NVARCHAR(15),
	Age INT,
	Address NVARCHAR(100),
	City NVARCHAR(100),
	ZipCode BIGINT
);


-- This is a test comment
SELECT
	T1.Field1,
	T1.Field2,
	T1.Field3,
	T1.Field4,
	T1.Field5,
	T1.Field6,
	T1.Field7
INTO ##Table1
FROM SERVERTABLE1 AS T1;

-- This is a test comment
SELECT
	T1.Field1,
	T1.Field2,
	T1.Field3,
	T1.Field4,
	T1.Field5,
	T1.Field6,
	T1.Field7,
	-- This is a test comment

	'Hello World' AS RandomField
INTO ##Table3
FROM ##Table1 AS T1;


-- This is a test comment
SELECT
	T1.Field1,
	T1.Field2,
	T1.Field3,
	T1.Field4,
	T1.Field5,
	T1.Field6,
	T1.Field7,
	-- This is a test comment

	'Hello World' AS RandomField
INTO ##Table10
FROM ##Table1 AS T1;


-- This is a test comment
SELECT
	T1.Field1,
	T1.Field2,
	T1.Field3,
	T1.Field4,
	T1.Field5,
	T1.Field6,
	T1.Field7,
	-- This is a test comment

	'Hello World' AS RandomField
INTO ##Table11
FROM ##Table10 AS T1;



SELECT
	T1.Field1,
	T1.Field2,
	T1.Field3,
	T1.Field4,
	T1.Field5,
	T1.Field6,
	T1.Field7,
	-- This is a test comment

	'Hello World' AS RandomField
INTO ##Table12
FROM ##Table10 AS T1;



SELECT
	T1.Field1,
	T1.Field2,
	T1.Field3,
	T1.Field4,
	T1.Field5,
	T1.Field6,
	T1.Field7,
	'Hello World' AS RandomField
INTO ##Table4
FROM ##Table1 AS T1;


SELECT
	T1.Field1,
	T1.Field2,
	T1.Field3,
	T1.Field4,
	T1.Field5,
	T1.Field6,
	T1.Field7,
	'Hello World' AS RandomField
INTO ##Table5
FROM ##Table4 AS T1;
