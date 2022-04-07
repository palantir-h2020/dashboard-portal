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

Once the docker compose services are up, go to <http://localhost:8090/auth/> and login in the Administration Console using the credentials defined in the `.env` file (i.e. admin/palantir).

The realm to select by default is `palantir`. In order to import the default settings of the palantir realm, use the `realm/realm.json` file.

Then go to *Clients* and open the `backend-service` client ID. On the *Credentials* tab generate a new secret and update the value of `KEYCLOAK_CLIENT_SECRET` in `.env` with it.

Last but not least, go to *`Users`* and add a user with a *non-temporary* password. Once you save the user, go to *`Credentials`* tab for the user, to set a password, and *de-select the temporary* switch.
The go to `Role Mappings` and add role to the user.
Each user must have ONE role! By default the mapping is default-roles-quarkus. REMOVE that from the assigned roles, and add another one. The supported ones are:
- `network_operator`
- `sme_manager`
- `sc_developer`

This way the users are appropriately set up.

### Set hosts appropriately

Make sure that either `/etc/hosts` file on the host, *or the dns* service, have the following domain names as valid:

- `sco-scc` &rarr; valid **SCC** IP
- `sco-so` &rarr; valid **SO** IP

This is required in order for APIs of other PALANTIR components to be reachable by the Frontend.

Similarly, **do the same for kafka**! Make sure that the following domain name mapping is in place:

- `kafka` &rarr; valid **Kafka broker** IP

The above step is necessary for proper operation of the backend.

### Portal Backend

You can compile and run the **backend** service in dev mode with a profile using:

```sh
./mvnw clean compile quarkus:dev
```

### Portal Dashboard Web App / Frontend

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
