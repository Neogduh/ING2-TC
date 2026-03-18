db.users.updateMany(
	{},
	[
		{
			$set: {
				total_ratings: { $size: "$movies" }
			}
		}
	]
)
