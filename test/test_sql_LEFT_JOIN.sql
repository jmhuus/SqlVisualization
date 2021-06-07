SELECT name, age, gender, sex, address, phone
FROM test LEFT JOIN test_2 ON test.id = test_2.id;
