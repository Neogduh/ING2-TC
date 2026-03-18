MATCH (u:User)
WHERE u.sex = "F" AND (u.occupation = "artist" OR u.occupation = "writer")
RETURN u AS womenArtistOrWriter
