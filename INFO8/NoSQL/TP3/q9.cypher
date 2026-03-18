MATCH (f:Movie)
WHERE f.title =~ ".*\\(1994\\)$"
RETURN f AS moviesIn1994
