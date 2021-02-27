# Configuration

Application listens to 8085 port by default.

It requires PostgresSQL DB running on http://localhost:5432 with **_postgres_** as default DB name, **_postgres_** as username and password.

Hibernate's auto DDL generation is set to **validate** and DB will be initialized by FlyWay.
It's migration scripts can be found at default location: **resources/db/migration**

After the table creations 1 **user** will be created with id: -1

Security in application is Basic Authentication:

User(id: -1) has **username:** ahayk and **password:** 123456


## Test Environment
Integration Tests are using H2 in-memory DB, so no external config is needed for testing.  
FlyWay will be turned off on test profile and Hibernate's Auto DDL generation will be set on **update**

# API Documentation
Endpoint documentation can be found at **http://localhost:8085/swagger-ui/**
