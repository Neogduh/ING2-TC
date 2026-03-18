MATCH (u:User)-[r:RATED {note: 1}]->(m:Movie)
WITH m, u
ORDER BY u.id
WITH m, collect(u.id) AS userIds, count(u) AS numberOfTimesRatedOne
RETURN m.title AS movieTitle, numberOfTimesRatedOne, userIds
ORDER BY movieTitle ASC
