#!/bin/bash
# indique au système que l'argument qui suit est le programme utilisé pour exécuter ce fichier.
# En cas général les "#" servent à faire des commentaires comme ici

DB_NAME=AMTDatabaseREST
DB_TECHNICAL_USER=AMTTechnicalUser
DB_TECHNICAL_USER_PASSWORD=dUke!1400$

#THESE ARE THE VARIABLES YOU HAVE TO CHANGE

#Your MySQL bin directory
MYSQL_BIN_DIR=/cygdrive/c/wamp/bin/mysql/mysql5.6.12/bin

#Your Glassfish bin directory
GLASSFISH_BIN_DIR=/cygdrive/c/Users/brito_000/GlassFish_Server/glassfish/bin

MYSQL_CONN_JAR=/cygdrive/c/Users/brito_000/GlassFish_Server/mysql-connector-java-5.1.33/mysql-connector-java-5.1.33/mysql-connector-java-5.1.33-bin.jar

# Partie MySQL

# Chemin vers l'exécutable de MySQL

cd $MYSQL_BIN_DIR

./mysql -u root << EOF
DROP DATABASE IF EXISTS $DB_NAME;
CREATE DATABASE $DB_NAME;

GRANT USAGE ON *.* TO '$DB_TECHNICAL_USER'@'localhost';
GRANT USAGE ON *.* TO '$DB_TECHNICAL_USER'@'%';

DROP USER '$DB_TECHNICAL_USER'@'localhost';
DROP USER '$DB_TECHNICAL_USER'@'%';

CREATE USER '$DB_TECHNICAL_USER'@'localhost' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';
CREATE USER '$DB_TECHNICAL_USER'@'%' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';
 
GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'localhost';
GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'%'; 
 
EOF

# Partie Glassfish

# Chemin vers Glassfish 
cd $GLASSFISH_BIN_DIR

DOMAIN_NAME=domainAMTREST

./asadmin stop-domain $DOMAIN_NAME
./asadmin delete-domain $DOMAIN_NAME
./asadmin create-domain --nopassword=true $DOMAIN_NAME

JDBC_JNDI_NAME=JNDI/$DOMAIN_NAME
JDBC_CONNECTION_POOL_NAME="$DOMAIN_NAME"_POOL

cp $MYSQL_CONN_JAR ../domains/$DOMAIN_NAME/lib

./asadmin start-domain $DOMAIN_NAME

./asadmin create-jdbc-connection-pool \
	--restype=javax.sql.XADataSource \
	--datasourceclassname=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource \
	--property user=$DB_TECHNICAL_USER:password=$DB_TECHNICAL_USER_PASSWORD:serverName=localhost:portNumber=3306:databaseName=$DB_NAME $JDBC_CONNECTION_POOL_NAME
	
./asadmin create-jdbc-resource --connectionpoolid $JDBC_CONNECTION_POOL_NAME $JDBC_JNDI_NAME

./asadmin ping-connection-pool $JDBC_CONNECTION_POOL_NAME

# Chemin vers l'exécutable de MySQL
cd $MYSQL_BIN_DIR

exit 0