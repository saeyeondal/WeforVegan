const mysql = require('mysql');

var connection = mysql.createConnection({
    host: "dbinstance.c4nf0uecennm.us-east-2.rds.amazonaws.com",
    user: "weforvegan",
    database: "weforvegan",
    password: "sungshin18",
    port: 3306
});

module.exports=connection;