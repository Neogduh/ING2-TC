MATCH ()-[r:RATED]->()
RETURN count(r) AS numberOfRatings
