# User Service

A demo application using DropWizard that works with Java 11. This application uses

1. MySQL for storage
2. Flyway for migrations

Getting started
---

Create a database called users and run migrations

```sh
mvn flyway:migrate
```

Start the application with

```sh
./run.sh
```

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
