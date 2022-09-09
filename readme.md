## Instruction

Make sure you have docker and docker-compose installed on your computer before starting the app.
Everything in this app including backend api, frontend site and database server are built on docker for convenience.

1. To start the app, please run:

```
docker-compose up
```

After frontend container started, you can access app via `localhost:3000`

2. To start tests for backend run:

```
docker-compose exec backend-api ./mvnw test
```
