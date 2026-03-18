db.movies.countDocuments({ genres: { $regex: /^Thriller$/ } })
