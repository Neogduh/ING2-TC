MATCH (m:Movie)<-[r:RATED]-()
WITH m, avg(r.note) AS averageRating
WHERE averageRating > 4
RETURN m.title AS movieTitle, averageRating
ORDER BY averageRating DESC
