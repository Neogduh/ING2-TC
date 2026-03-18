db.users.countDocuments({ movies: { $elemMatch: { movieid: 111,rating: 5}} })
