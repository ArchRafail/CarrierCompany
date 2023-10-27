# Carrier Company

## Development guide

### Docker
1. Download Docker Desktop installation file from url `https://www.docker.com/products/docker-desktop` and install it on your local environment.
2. Run Docker Desktop application.
3. Run `docker-compose --project-name="transport-company" up --detach` from `provisioning\dev` folder.
* If you already have installed Docker Desktop with `transport-company` cluster, just launch it.

### Backend
Backend is presented by a Spring Boot Java application located in the `transport-company-backend` folder.
1. Run application from `src\main\java\com\example\transportcompanybackend\TransportCompanyBackendApplication.java` with your IDE.
2. Check `http://localhost:8080`.
3. API Swagger documentation at `http://localhost:8080/swagger`.

### Frontend
Frontend is presented by an Angular application located in the `transport-company-frontend` folder.
1. Run `npm install`.
2. Run `start` script from `package.json` with IDE or with the following command `npm run start`.
3. Check `http://localhost:4200`.

### Tools
* I recommend to use `Intellij IDEA` for full-stack development.
* For DB view and modification you can use integrated into the Intellij IDEA DB viewer, pgAdmin application or any other tool.

### Hints
* All scripts and tools must be launched with local administrator rights. 