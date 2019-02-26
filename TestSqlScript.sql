SELECT 
	T1.Field1,
	T1.Field2,
	T1.Field3,
	T1.Field4,
	T1.Field5,
	T1.Field6,
	T1.Field7
INTO ##Table1
FROM Server1.dbo.SererTable1 AS T1




SELECT 
	T1.Field1,
	T1.Field2,
	T1.Field3,
	T1.Field4,
	T1.Field5,
	T1.Field6,
	T1.Field7,
	'Hello World' AS RandomField
INTO ##Table2
FROM ##Table1 AS T1