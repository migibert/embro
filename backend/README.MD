# Requirements

* Java
* Maven
* A `postgres` database


# Features

* Organization management
* Team management
* Skill management
* Collaborator management

# Configuration

The application can be configured throug environment variables

* DATABASE_URL
* DATABASE_USER
* DATABASE_PASSWORD

# Running locally

Start a postgres container

```
docker run -p5432:5432 --name embro-test -e POSTGRES_PASSWORD=pwd -e POSTGRES_USER=sa -e POSTGRES_DB=embro postgres
```

Package the application

```
export DATABASE_URL=jdbc:postgresql://localhost:5432/embro
export DATABASE_USER=sa
export DATABASE_PASSWORD=pwd
mvn clean package
```

Run tests

```
docker run -p5433:5432 --name embro-test -e POSTGRES_PASSWORD=pwd -e POSTGRES_USER=sa -e POSTGRES_DB=embro-test postgres
mvn test
```

Start the application
```
export DATABASE_URL=jdbc:postgresql://localhost:5432/embro
export DATABASE_USER=sa
export DATABASE_PASSWORD=pwd
java -jar target/embro-0.0.1-SNAPSHOT.jar
```
