SELECT
    T1.FirstName,
    T1.LastName,
    T1.Age,
    T2.Street,
    T2.City,
    T2.Zip,
    T2.State
FROM Prospects AS T1
JOIN Addresses AS T2
    ON T1.ID = T2.ID;


DROP TABLE NewTable;