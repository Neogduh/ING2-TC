db.movies.countDocuments({ title: { $regex: /\(19(8[2-9])|(9[0-2])\)$/ } })
