db.users.find({gender: "F", occupation: "programmer"}, {_id:0, movies: 0}).sort({age: -1}).limit(10).toArray()
