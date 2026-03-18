MATCH (u:User {occupation: "student"})
RETURN avg(u.age) AS averageAge
