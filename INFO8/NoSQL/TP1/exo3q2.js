db.users.updateOne(
	{ name: "Aurelien Barrau" },
	{ $set: { movies: [
		{
			movieid: 2628,
			rating: 5,
			timestamp: 1705269118
		},
		{
			movieid: 1210,
			rating: 4,
			timestamp: 1705269118
		},
		{
			movieid: 260,
			rating: 5,
			timestamp: 1705269118
		},
		{
			movieid: 1196,
			rating: 4,
			timestamp: 1705269118
		}
	] } }
)
