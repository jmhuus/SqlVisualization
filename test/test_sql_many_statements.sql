SELECT name, age, gender, sex, address, phone FROM test, test_2;
SELECT name, age, gender, sex, address, phone FROM (SELECT * FROM table2) as T1;
INSERT INTO test (column_1, column_2) VALUES (1, 2);
SELECT name, age, gender, sex, address, phone 
FROM test LEFT JOIN test_2 ON test.id = test_2.id;
SELECT name, age, gender, sex, address, phone FROM test;
SELECT name, age, gender, sex, address, phone FROM test; SELECT name, age, gender, sex, address, phone FROM test;
DROP TABLE test;
