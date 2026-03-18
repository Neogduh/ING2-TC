db.movies.updateMany(
	{},
	[
		{
			$set: {
				year: {
        			$toInt: {
            			$substrCP: ["$title", { $subtract: [{ $strLenCP: "$title" }, 5] }, 4]
        			}
        		},
        		title: {
          			$trim: {
            			input: {
        					$substrCP: ["$title", 0, { $subtract: [{ $strLenCP: "$title" }, 7] }]
            			}
          			}
        		}
      		}
    	}
  	]
)
