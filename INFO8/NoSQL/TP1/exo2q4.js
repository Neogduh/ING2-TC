db.movies.find({$and: [{genres: { $regex: /Action/ }}, {genres: { $regex: /Comedy/ }}]}).toArray()
