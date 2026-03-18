db.createUser(
    {
        user: "admin",
        pwd: "admin",
        roles: [
            {
                role: "readWrite",
                db: "Sundaland"
            }
        ]
    }
);
db.createCollection("Products");
db.createCollection("Purchases");
