db.users.updateMany(
	{occupation: "programmer"},
	{ $set: { occupation: "developer" } }
)
