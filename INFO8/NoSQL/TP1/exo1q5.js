db.users.countDocuments({$or: [{occupation: "artist"}, {occupation: "writer"}]})
