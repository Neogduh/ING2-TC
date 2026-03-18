MATCH (f:Movie)<-[r:RATED {note: 1}]-()
RETURN count(DISTINCT f) AS numberOfMoviesWithRatingOne
