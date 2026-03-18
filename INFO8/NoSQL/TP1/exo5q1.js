db.movies.aggregate([
	{ $match: { year: { $gte: 1990, $lt: 2000 } } },
	{ $group: { _id: "$year", count: { $sum: 1 } } },
	{ $sort: { count: -1 } }
]).toArray()
