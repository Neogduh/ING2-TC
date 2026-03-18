db.users.aggregate([
	{ $project: {
		_id: 0,
		maxRating: { $max: "$movies.rating" },
		minRating: { $min: "$movies.rating" },
		avgRating: { $avg: "$movies.rating" },
		userId: "$_id",
		userName: "$name"
	} },
	{ $sort: { avgRating: 1 } }
]).toArray()
