const databaseName = process.env.MONGO_INITDB_DATABASE || "Sundaland";
const appUser = process.env.SUNDALAND_MONGODB_USER || "admin";
const appPassword = process.env.SUNDALAND_MONGODB_PASSWORD || "admin";

db.createUser({
    user: appUser,
    pwd: appPassword,
    roles: [
        {
            role: "readWrite",
            db: databaseName,
        },
    ],
});

db.createCollection("Products");
db.createCollection("Purchases");
