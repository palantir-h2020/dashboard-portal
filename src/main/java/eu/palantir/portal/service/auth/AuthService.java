package eu.palantir.portal.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.palantir.portal.dto.AuthToken;
import eu.palantir.portal.dto.ResetPasswordDto;
import eu.palantir.portal.dto.SignUpDto;
import eu.palantir.portal.dto.mappers.UserMapper;
import eu.palantir.portal.exceptions.*;
import eu.palantir.portal.model.Token;
import eu.palantir.portal.model.Token_;
import eu.palantir.portal.model.User;
import eu.palantir.portal.model.User_;
import eu.palantir.portal.util.JwtClaim;
import eu.palantir.portal.mail.Templates;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.logging.Logger;

@ApplicationScoped
public class AuthService {

    @ConfigProperty(name = "keycloak.baseurl")
    String keycloakBaseUrl;
    @ConfigProperty(name = "keycloak.realm")
    String keycloakBaseRealm;
    @ConfigProperty(name = "quarkus.oidc.client-id")
    String clientId;
    @ConfigProperty(name = "quarkus.oidc.credentials.secret")
    String secret;
    @ConfigProperty(name = "keycloak.admin.username")
    String adminUsername;
    @ConfigProperty(name = "keycloak.admin.password")
    String adminPassword;

    @Inject
    UserMapper userMapper;

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    @Transactional
    public AuthToken login(String username, String password) {
        logger.info("Fetching token for: " + username);
        String tokenEndpoint = keycloakBaseUrl + "/auth/realms/" + keycloakBaseRealm + "/protocol/openid-connect/token";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost post = new HttpPost(tokenEndpoint);
            String encoding = Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes());
            post.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
            post.setHeader("content-type", "application/x-www-form-urlencoded");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("grant_type", "password"));
            post.setEntity(new UrlEncodedFormEntity(params));
            // get the response code
            HttpResponse response;
            response = client.execute(post);
            String jsonString = EntityUtils.toString(response.getEntity());
            logger.info("Login attempt response code: " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != 200) {
                if (response.getStatusLine().getStatusCode() == 401) {
                    throw new InvalidUserCredentialsException();
                } else if (response.getStatusLine().getStatusCode() == 400) {
                    UserRepresentation keycloakUser = getUserByUsernameKeycloak(username);
                    ErrorWrapper errorWrapper = (new ObjectMapper()).readValue(jsonString, ErrorWrapper.class);
                    String requiredAction = "";
                    if (keycloakUser.getRequiredActions() != null && !keycloakUser.getRequiredActions().isEmpty()) {
                        requiredAction = "." + keycloakUser.getRequiredActions().get(0).toLowerCase();
                    } else if (!keycloakUser.isEnabled()) {
                        requiredAction = "." + "user_disabled";
                    }
                    throw new AccountNotFullySetUpException(
                            errorWrapper.getErrorDescription() + ": "
                                    + String.join(", ", keycloakUser.getRequiredActions()),
                            errorWrapper.getError() + requiredAction);
                } else {
                    logger.info(jsonString);
                    throw new InternalServerErrorException();
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            // JSON from file to Object
            return mapper.readValue(jsonString, AuthToken.class);
        } catch (IOException e) {
            throw new InternalServerErrorException();
        }
    }

    // LATER: check keycloak java admin client api for refresh token support
    // (currently not supported)

    @Transactional
    public AuthToken refresh(String refreshToken) {
        // logger.info("Fetching token with refresh token: " + refreshtoken);
        String tokenEndpoint = keycloakBaseUrl + "/auth/realms/" + keycloakBaseRealm + "/protocol/openid-connect/token";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost post = new HttpPost(tokenEndpoint);
            String encoding = Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes());
            post.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
            post.setHeader("content-type", "application/x-www-form-urlencoded");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("client_id", clientId));
            params.add(new BasicNameValuePair("refresh_token", refreshToken));
            params.add(new BasicNameValuePair("grant_type", "refresh_token"));
            post.setEntity(new UrlEncodedFormEntity(params));
            // get the response code
            HttpResponse response;
            response = client.execute(post);
            // logger.info("Fetching token with refresh token: " + refreshToken + " ,
            // response " + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.info("Fetching token with refresh token: " + refreshToken + " , response "
                        + response.getStatusLine().getStatusCode());
                return null;
            }
            String jsonString = EntityUtils.toString(response.getEntity());
            // logger.info("token: " + jsonString);
            ObjectMapper mapper = new ObjectMapper();
            // JSON from file to Object
            return mapper.readValue(jsonString, AuthToken.class);
        } catch (Exception ex) {
            logger.severe("Error during refresh token fetching" + ex.getMessage());
            throw new InternalServerErrorException();
        }
    }

    @Transactional
    public void deleteUserKeycloak(String username) {
        logger.info("Delete user with username: " + username);
        UserRepresentation userRepresentation = getUserByUsernameKeycloak(username);
        Keycloak keycloak = keycloak();
        RealmResource realmResource = keycloak.realm(keycloakBaseRealm);
        UsersResource userResource = realmResource.users();
        userResource.delete(userRepresentation.getId());
        keycloak.close();
    }

    @Transactional
    public void updateUserAttribute(String username, String attributeName, String attributeValue) throws IOException {
        UserRepresentation keycloakUser = getUserByUsernameKeycloak(username);
        keycloakUser.getAttributes().put(attributeName, Arrays.asList(attributeValue));
        Keycloak keycloak = keycloak();
        RealmResource realmResource = keycloak.realm(keycloakBaseRealm);
        UsersResource usersResource = realmResource.users();
        usersResource.get(keycloakUser.getId()).update(keycloakUser);
        keycloak.close();
    }

    @Transactional
    public UserRepresentation updateUser(UserRepresentation keycloakUser) {
        Keycloak keycloak = keycloak();
        RealmResource realmResource = keycloak.realm(keycloakBaseRealm);
        UsersResource usersResource = realmResource.users();
        usersResource.get(keycloakUser.getId()).update(keycloakUser);
        keycloak.close();
        return keycloakUser;
    }

    @Transactional
    public UserRepresentation getUserByUsernameKeycloak(String username) {
        Keycloak keycloak = keycloak();
        RealmResource realmResource = keycloak.realm(keycloakBaseRealm);
        UsersResource userResource = realmResource.users();
        List<UserRepresentation> users = userResource.search(username);
        keycloak.close();
        if (users == null || users.isEmpty()) {
            logger.warning("User with username: " + username + " does not exist in keycloak");
            throw new NotFoundAlertException("user");
        }
        return users.get(0);
    }

    @Transactional
    public Integer getUsersCount() {
        logger.info("Request to fetch keycloak user count");
        Keycloak keycloak = keycloak();
        RealmResource realmResource = keycloak.realm(keycloakBaseRealm);
        UsersResource userResource = realmResource.users();
        Integer count = userResource.count();
        keycloak.close();
        return count;
    }

    @Transactional
    public String addUserKeycloak(UserRepresentation keycloakUser, List<String> roles, String password)
            throws UsernameAlreadyExistsException {
        Keycloak keycloak = keycloak();
        RealmResource realmResource = keycloak.realm(keycloakBaseRealm);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> users = usersResource.search(keycloakUser.getUsername());
        if (users != null && !users.isEmpty()) {
            keycloak.close();
            throw new UsernameAlreadyExistsException();
        }
        // ---Step 2 Request to add User -----------------
        Response response = usersResource.create(keycloakUser);
        logger.info("Response: " + response.getStatus() + " " + response.getStatusInfo());
        if (response.getStatus() != 201) {
            throw new InternalServerErrorException();
        }
        String userId = CreatedResponseUtil.getCreatedId(response);
        // ---Step 4 Assign roles for userId ---------------
        UserResource userResource = usersResource.get(userId);
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();
        for (String roleName : roles) {
            RoleRepresentation realmRole = realmResource.roles().get(roleName).toRepresentation();
            roleRepresentations.add(realmRole);
        }
        userResource.roles().realmLevel().add(roleRepresentations);
        // remove default role
        roleRepresentations.clear();
        roleRepresentations.add(realmResource.roles().get("default-roles-quarkus").toRepresentation());
        userResource.roles().realmLevel().remove(roleRepresentations);
        keycloak.close();
        // ---Step 4 Reset User's Password-----------------
        resetPasswordKeycloak(userId, password);
        return userId;
    }

    @Transactional
    public UserRepresentation addUser(SignUpDto signUpDto)
            throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            throw new BadRequestAlertException("Passwords do not match", "authorization", "passwordsNotMatch");
        }
        User user = userMapper.toUser(signUpDto);
        User tempUser = (User) User.find(User_.USERNAME, user.getUsername()).firstResult().await().atMost(
                Duration.ofSeconds(5));
        if (tempUser != null) {
            throw new UsernameAlreadyExistsException();
        }
        tempUser = (User) User.find(User_.EMAIL, user.getEmail()).firstResult().await().atMost(
                Duration.ofSeconds(5));
        if (tempUser != null) {
            throw new EmailAlreadyExistsException();
        }
        user.persist().onItem();
        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(user.getUsername());
        keycloakUser.setEmail(user.getEmail());
        // Add switch/case based on role if you want to have users disabled by default
        // e.g. an admin user
        keycloakUser.setEnabled(true);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put(JwtClaim.USER_ID.getDescription(), Arrays.asList(user.getId().toString()));
        attributes.put(JwtClaim.USER_FULL_NAME.getDescription(), Arrays.asList(user.getUsername()));
        keycloakUser.setAttributes(attributes);
        String userId = addUserKeycloak(keycloakUser, user.realmRoles, user.password);
        keycloakUser.setId(userId);
        return keycloakUser;
    }

    private Keycloak keycloak() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakBaseUrl + "/auth")
                .realm("master")
                .username(adminUsername)
                .password(adminPassword)
                .clientId("admin-cli")
                .clientSecret(secret)
                .build();
        return keycloak;
    }

    @Transactional
    public AuthToken getAdminAccessToken() {
        AuthToken authToken;
        String tokenEndpoint = keycloakBaseUrl + "/auth/realms/master/protocol/openid-connect/token";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("client_id", "admin-cli"));
            params.add(new BasicNameValuePair("username", adminUsername));
            params.add(new BasicNameValuePair("password", adminPassword));
            params.add(new BasicNameValuePair("grant_type", "password"));
            HttpPost post = new HttpPost(tokenEndpoint);
            post.setEntity(new UrlEncodedFormEntity(params));
            // get the response code
            HttpResponse response;
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                logger.info("Token received successfully");
                String jsonString = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                // JSON from file to Object
                authToken = mapper.readValue(jsonString, AuthToken.class);
            } else {
                logger.severe("Error during token fetching");
                authToken = null;
            }
        } catch (Exception ex) {
            logger.severe("Error during token fetching" + ex.getMessage());
            authToken = null;
        }
        return authToken;
    }

    @Transactional
    public void resetPasswordFromEmail(ResetPasswordDto resetPasswordDto, String uuid) {
        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            throw new BadRequestAlertException("Passwords do not match", "authorization", "passwordsNotMatch");
        }
        Token token = (Token) Token.find(Token_.UUID, uuid).firstResult();
        if (token == null || token.hasExpired()) {
            throw new BadRequestAlertException("Token not valid or have expired", "authorization", "notValid");
        }
        resetPassword(token.getUser().getUsername(), resetPasswordDto.getPassword());
        token.delete();
    }

    @Transactional
    public void resetPasswordFromProfile(Long id, ResetPasswordDto resetPasswordDto) {
        User user = null;
        user = (User) User.findById(id).await().atMost(Duration.ofSeconds(5));
        if (user == null) {
            throw new NotFoundAlertException(User.class.getName());
        }
        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            throw new BadRequestAlertException("Passwords do not match", "authorization", "passwordsNotMatch");
        }
        if (login(user.getUsername(), resetPasswordDto.getOldPassword()) == null) {
            throw new BadRequestAlertException("Old password do not match", "authorization", "oldPasswordNotMatch");
        }
        resetPassword(user.getUsername(), resetPasswordDto.getPassword());
    }

    @Transactional
    public void resetPassword(String username, String newPassword) {
        UserRepresentation keycloakUser = getUserByUsernameKeycloak(username);
        resetPasswordKeycloak(keycloakUser.getId(), newPassword);
    }

    @Transactional
    public void verifyEmail(String uuid) {
        Token token = (Token) Token.find(Token_.UUID, uuid).firstResult().await().atMost(Duration.ofSeconds(5));
        if (token == null || token.hasExpired()) {
            throw new BadRequestAlertException("Token not valid or have expired", "authorization", "notValid");
        }
        UserRepresentation user = this.getUserByUsernameKeycloak(token.getUser().getUsername());
        user.setRequiredActions(new ArrayList<>());
        user.setEmailVerified(true);
        this.updateUser(user);
        token.delete();
    }

    @Transactional
    public void resetPasswordKeycloak(String keycloakUserId, String newPassword) {
        logger.info("Request to reset user password");
        // Set password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(newPassword);

        Keycloak keycloak = keycloak();
        RealmResource realmResource = keycloak.realm(keycloakBaseRealm);
        UsersResource usersResource = realmResource.users();
        usersResource.get(keycloakUserId).resetPassword(passwordCred);
        keycloak.close();
    }

    @Transactional
    public void sendResetPasswordEmail(String username) {
        User user = (User) User.find(User_.USERNAME, username).firstResult().await().atMost(Duration.ofSeconds(5));
        if (user == null) {
            throw new NotFoundAlertException("user");
        }
        Token token = new Token(user, Token.Type.RESET_PASSWORD);
        token.persistAndFlush();
    }

    @Transactional
    public void sendVerifyEmail(String username) {
        User user = (User) User.find(User_.USERNAME, username).firstResult().await().atMost(Duration.ofSeconds(5));
        if (user == null) {
            throw new NotFoundAlertException("user");
        }
        Token token = new Token(user, Token.Type.VERIFY_EMAIL);
        token.persistAndFlush();
    }

}
