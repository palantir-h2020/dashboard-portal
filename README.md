# [**PALANTIR**](../) **Portal and Dashboard**

**Note: This is WIP.*

## Running the application in dev mode

### Prerequisites

Before moving on, make sure you have the following software installed:

1. Git
2. Docker
3. Docker Compose (1.28.0 profiles support)
4. Java SE JDK 11

### Services

During development time you can use Docker and Docker Compose to deploy locally all required services like postgres, pgAdmin, etc.

> **Notice:** Before moving on, make sure you have set the required [environment variables](https://docs.docker.com/compose/environment-variables/) which are needed for running Docker. You can do this by creating a `.env` file and export the environment variables. There is an [**example** environment file named `.env.example` under the parent project's root dir](../.env.example) for the **backend**, and [one more under `src/main/dashboard-webapp` for the **frontend**](./src/main/dashboard-webapp/.env.example). The **.env** for this project is to be put in the **current** directory.

```$shell
docker-compose up
```

For production

```$shell
docker-compose --profile monitoring up
```

### Keycloak

Once the docker compose services are up, go to <http://localhost:8090/auth/> and login in the Administration Console using the credentials defined in the `.env` file (i.e. admin/admin).

From the realm selector, add a new realm by selecting the `realm-export.json` file in `realm/` folder.

Then go to *Clients* and open the `backend-service` client ID. On the *Credentials* tab generate a new secret and update the value of `KEYCLOAK_CLIENT_SECRET` in `.env` with it.

Last but not least, go to *Users* and add a user with a non-temporary password. Once you save the user, go to *Credentials* to set a password. You can also go to *Attributes* and add the following key-values:

| Key          | Value   |
| ------------ | ------- |
| userid       | 1       |
| userfullname | manager |

The go to Roles and add role SME_MANAGER to the user.

### Backend

You can compile and run the **backend** service in dev mode with a profile using:

```sh
./mvnw clean compile quarkus:dev
```

### Frontend

Before moving on, make sure you've installed the required dependencies needed to watch the frontend app:

```sh
cd src/main/dashboard-webapp
npm install
```

Additionally, you can use the following commands for the frontend app:

#### Compiles and hot-reloads for development

```sh
npm run serve
```

#### Lints and fixes files

```sh
npm run lint-fix
```

#### Test

(dev mode : dev , prod mode: prod)

```sh
npm run cypress:dev
```

## Packaging and running the application

By default, Quarkus has three profiles, although it is possible to use as many as you like. The default profiles are:

- dev - Activated when in development mode (i.e. quarkus:dev)
- test - Activated when running tests
- prod - The default profile when not running in development or test mode

> **Notice:** The above commands are for a bundled back-end and front-end application

### Creating a jar executable

Check pom.xml for ${release}

```$shell
./mvnw clean package -Pvue
java -jar target/portal-${release}-runner.jar
```

#### Create docker container

```$shell
./mvnw clean package -Dquarkus.container-image.build=true -Pvue
docker run --env-file .env --network=host palantir/dashboard-portal:${release}
```

## Extras

### Backend Development Tool: Migrating database changes, after changing the Model, semi-automatically

>Note: Proper usage of this development tool **requires knowledge of liquibase**. If changes are to be made in the model, then the changelog has to be generated, based on the difference from the current database. Instructions on how to do this are included in a later section. In order to make this possible **create a `src/main/resources/liquibase.properties` file** based on the `src/main/resources/liquibase.properties.example` file that exists. Make sure the same credentials are used as in the `.env` file created.

In case changes are done to `src/main/java/eu/palantir/portal/model`, the changes can be migrated to the database easily by automatically generating a liquibase changelog. **Make sure the liquibase.properties file has already been properly configured** before continuing. The only changes from the example should be url, username and password.

In order to find the difference between the current DB and the current JPA model in the project:

```sh
./mvnw liquibase:diff
```

The generated changelog can be found as `src/main/resources/liquibase-diffChangeLog.xml`.
After this the generated changelog can be added to the overall changelogs as expected by liquibase.
Inspect the diff changelog, verify it has the changes you want in it.
To include the changelog in the migration to be executed:

1. Rename the diff file into a name of your choosing, for example `my_changes.xml`, and move it into the `src/main/resources/db/changelog` directory.
2. Add an entry to the `src/main/resources/db/changelog.xml` file that points to the file, as a last entry of the `</databaseChangeLog>` element. **Notice**: Make sure the `<include>` elements are in the proper sequence of changes in the file. The entry for the example above would be `<include file="db/changelog/my_changes.xml" relativeToChangelogFile="false"/>`
