MATCH (u:User)-[r:RATED]->(f:Movie {title: "Jurassic Park (1993)"})
RETURN count(u) AS numberOfUsers
