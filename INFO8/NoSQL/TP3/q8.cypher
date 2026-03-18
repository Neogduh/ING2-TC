MATCH (u:User)
WITH u.occupation AS occupation, avg(u.age) AS avgAge
WHERE avgAge > 42
RETURN count(occupation) AS numberOfOccupations
