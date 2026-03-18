db.movies.updateMany(
	{},
	[
		{
			$set: {
        		genres: {
          			$split: ["$genres", "|"]
        		}
      		}
    	}
  	]
)
