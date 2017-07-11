# wsl-java-sql-example
Example Java app to troubleshoot WSL SQL Server connectivity issues

## DEPENDENCIES

* WSL w/ Ubuntu 16.04 
* sudo apt-get install openjdk-8-jdk maven
* SQL Server 
... The code assumes an AlwaysON AG with multisubnetfailover=true. You can change this by editing the connectString in 
... src/main/java/wsl_java_sql_example/App.java:58

* SQL Login
```
USE [master]
GO
CREATE LOGIN [sqllogin] WITH PASSWORD=N'password', DEFAULT_DATABASE=[master], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO
```

## BUILD

cd wsl-java-sql-example
mvn install

## RUN

```
cd wsl-java-sql-example
java -jar wsl_java_sql_example-1.0-SNAPSHOT-jar-with-dependencies.jar -H fqdn.of.sqlserver -u sqllogin -p password
```

should return server\instancename


