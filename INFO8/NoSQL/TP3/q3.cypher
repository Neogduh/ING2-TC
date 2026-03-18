MATCH (u:User)
WHERE u.sex = "F" AND u.occupation = "artist"
RETURN u AS womenArtists
