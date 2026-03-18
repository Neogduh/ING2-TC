MATCH (u:User)
RETURN u.occupation AS occupation, avg(u.age) AS averageAge
ORDER BY averageAge DESC
