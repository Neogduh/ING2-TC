MATCH (u:User)-[r:RATED]->(m:Movie)<-[r2:RATED]-(u42:User {id: 42})
WHERE u.id <> 42
RETURN u.id AS userId, count(m) AS numberOfMoviesRated
ORDER BY numberOfMoviesRated DESC
LIMIT 1
