SELECT
    T1.FirstName,
    T1.LastName,
    T1.Age,
    T2.Street,
    T2.City,
    T2.Zip,
    T2.State,
    T3.FamilySize
FROM Prospects AS T1
JOIN Addresses AS T2
    ON T1.ID = T2.ID
JOIN (
    SELECT
        LastName,
        COUNT(ID) AS FamilySize
    FROM Prospects
    GROUP BY LastName
) AS T3
    ON T1.LastName = T3.LastName;
