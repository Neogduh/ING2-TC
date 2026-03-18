MATCH (u:User)
RETURN u.occupation AS occupation, count(u) AS count
