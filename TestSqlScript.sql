IF OBJECT_ID('tempdb..##Table1') ID NOT NULL
	DROP TABLE ##Table1
SELECT 
	T1.Field1,
	T1.Field1,
	T1.Field1,
	T1.Field1,
	T1.Field1,
	T1.Field1,
	T1.Field1
INTO ##Table1
FROM ONYX_REPORTING.dbo.csuMktHmCodes AS T1




IF OBJECT_ID('tempdb..##Table2') ID NOT NULL
	DROP TABLE ##Table2
SELECT 
	T1.Field1,
	T1.Field1,
	T1.Field1,
	T1.Field1,
	T1.Field1,
	T1.Field1,
	T1.Field1,
	'Hello World' AS RandomField
INTO ##Table2
FROM ##Table1 AS T1