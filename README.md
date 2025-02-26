Exemplo with Spring Boot and Mult Datasource.

|           Framework |
|--------------------:|
|         Spring Boot |
|                 JPA |
| DataSource with SQL |
|Validation|
|              Lombok |
|           MapStruct |


1 - Create network to communication among docks
```
docker network create guests-network
```
2 - Dock run to make download of Database Postgres and run
```
docker run --name postgresdb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES=postgres -e POSTGRES_DB=guests --network guests-network -d postgres:16.3
```
3 - Dock run to make download of client PGAdmin to communicate with Data and create table.
```
docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin --network guests-network -d dpage/pgadmin4:8.9
```
4 - Access URL to create table [PGAdmin](http://localhost:15432/browser/)
Click on Register
On General write a name
Select Connection
|Field|Value|
|-----:|-----------|
|Host name/address:| postgresdb|
|Port:| 5432|
|Maintenance database:| guests|
|Username:| postgres|
|Password:| postgres|

Those values was created in docker commands

5 - Create table in guests database
5.1 - Create table
```
create table guest(
legal_entity_number char(11) not null primary key,
name char(100) not null,
birthday date not null,
last_update_date timestamp not null,
creation_date timestamp not null
)
5.2 - Create second Database guestsdr and create table guest (step 5.1) in this databse
```
6 - Create image of GuestApp
```
Docker build --tag guestsjpadsapp:1.0.0 .
```
7 - Run container guestsjpadsapp
```
docker run --name guestsjpadsapp -p 8080:8080 --network guests-network -e DATASOURCE_URL=jdbc:postgresql://postgresdb:5432/guests -e DATASOURCE_USER=postgres -e DATASOURCE_PASSWORD=postgres guestsapp:1.0.0
```
8 - Stop docker container
```
docker stop postgresdb
docker stop pgadmin4
docker stop guestsjpadsapp
```
9 - Start docker container
```
docker start postgresdb
docker start pgadmin4
docker start guestsjpadsapp
```