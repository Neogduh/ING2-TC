db.users.aggregate([
	{ $unwind: "$movies" },
	{ $match: { "movies.movieid": 296 } },
	{ $group: { _id: "$movies.movieid", moyenne: { $avg: "$movies.rating" } } }
]).toArray()
