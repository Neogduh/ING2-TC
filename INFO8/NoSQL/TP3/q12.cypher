MATCH (u:User {id: 20})-[r:RATED]->()
RETURN avg(r.note) AS averageRating
