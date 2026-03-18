MATCH (f:Movie)<-[r:RATED]-()
RETURN f.title AS movieTitle, count(r) AS numberOfRatings
ORDER BY numberOfRatings DESC
LIMIT 10
