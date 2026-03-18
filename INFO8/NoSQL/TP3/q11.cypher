MATCH (u:User {id: 20})-[r:RATED]->(m:Movie)
RETURN u AS user, r AS rating, m AS movie
