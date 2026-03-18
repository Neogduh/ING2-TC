MATCH (u:User {id: 33})-[f:FRIEND_OF]->(c:User)<-[f2:FRIEND_OF]-(o:User)
WHERE o.id <> 33
RETURN o AS userWithMostCommonFriends, count(c) AS commonFriendsCount
ORDER BY commonFriendsCount DESC
LIMIT 1
