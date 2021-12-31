package eu.ubitech.palantir.portal.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jose4j.json.internal.json_simple.JSONObject;

import eu.ubitech.palantir.portal.dto.Token;
import eu.ubitech.palantir.portal.exceptions.AccountNotFullySetUpException;
import eu.ubitech.palantir.portal.exceptions.EmailAlreadyExistsException;
import eu.ubitech.palantir.portal.exceptions.InternalServerErrorException;
import eu.ubitech.palantir.portal.exceptions.InvalidUserCredentialsException;
import eu.ubitech.palantir.portal.exceptions.TooGenericQueryException;
import eu.ubitech.palantir.portal.exceptions.UsernameAlreadyExistsException;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  private static final Logger logger = Logger.getLogger(AuthService.class.getName());

  @Transactional
  public Token login(String username, String password) throws IOException {
    logger.info("Fetching token for: " + username);
    String tokenEndpoint = keycloakBaseUrl + "/auth/realms/" + keycloakBaseRealm + "/protocol/openid-connect/token";
    final HttpClient client = new DefaultHttpClient();
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
    logger.info("response.getStatusLine().getStatusCode(): " + response.getStatusLine().getStatusCode());
    if (response.getStatusLine().getStatusCode() != 200) {
      if (response.getStatusLine().getStatusCode() == 401) {
        throw new InvalidUserCredentialsException();
      } else if (response.getStatusLine().getStatusCode() == 400) {
        ErrorWrapper errorWrapper = (new ObjectMapper()).readValue(jsonString, ErrorWrapper.class);
        throw new AccountNotFullySetUpException(errorWrapper.getErrorDescription(), errorWrapper.getError());
      } else {
        logger.info(jsonString);
        throw new InternalServerErrorException();
      }
    }
    ObjectMapper mapper = new ObjectMapper();
    // JSON from file to Object
    return mapper.readValue(jsonString, Token.class);
  }

  // Sliding session implementation
  // https://auth0.com/blog/refresh-tokens-what-are-they-and-when-to-use-them/
  @Transactional
  public Token refresh(String refreshToken) {
    // logger.info("Fetching token with refresh token: " + refreshtoken);
    Token token = new Token();
    String tokenEndpoint = keycloakBaseUrl + "/auth/realms/" + keycloakBaseRealm + "/protocol/openid-connect/token";
    try {
      final HttpClient client = new DefaultHttpClient();
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
      token = mapper.readValue(jsonString, Token.class);

    } catch (Exception ex) {
      logger.severe("Error during refresh token fetching" + ex.getMessage());
      token = null;
    }
    return token;
  }

  @Transactional
  public Token fetchAccessToken() {
    Token token = new Token();
    String tokenEndpoint = keycloakBaseUrl + "/auth/realms/master/protocol/openid-connect/token";
    try {
      // ---Step 1---------------------------------------
      logger.info("Fetching access token first ");
      final HttpClient client = new DefaultHttpClient();
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
        token = mapper.readValue(jsonString, Token.class);
      } else {
        logger.severe("Error during token fetching");
        token = null;
      }
    } catch (Exception ex) {
      logger.severe("Error during token fetching" + ex.getMessage());
      token = null;
    }
    return token;
  }

  public void deleteUser(Token token, String userId) {
    try {
      logger.info("Request to delete user ");
      String userEndpoint = keycloakBaseUrl + "/auth/admin/realms/" + keycloakBaseRealm + "/users/" + userId;
      final HttpClient client = new DefaultHttpClient();
      HttpDelete delete = new HttpDelete(userEndpoint);
      delete.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.access_token);
      // delete.setHeader("content-type", "application/json; charset=utf-8");
      // get the response code
      HttpResponse response;
      response = client.execute(delete);
      logger.info("response: " + response);
      if (response.getStatusLine().getStatusCode() == 201) {
        logger.info("Successfull Delete ");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void requestToAddUser(Token token, KeycloakUser user)
      throws EmailAlreadyExistsException, IOException, TooGenericQueryException {
    logger.info("Request to add user ");
    String userEndpoint = keycloakBaseUrl + "/auth/admin/realms/" + keycloakBaseRealm + "/users";
    final HttpClient client = new DefaultHttpClient();
    HttpPost post = new HttpPost(userEndpoint);
    post.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.access_token);
    post.setHeader("content-type", "application/json; charset=utf-8");
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(user);
    logger.info("UserDetails: " + json);
    post.setEntity(new StringEntity(json, "UTF-8"));
    // get the response code
    HttpResponse response;
    response = client.execute(post);
    String jsonString = EntityUtils.toString(response.getEntity());
    // logger.info("response: " + response);
    // logger.info("response: " + jsonString);
    if (response.getStatusLine().getStatusCode() == 201) {
      logger.info("Successfull creation ");
    } else if (response.getStatusLine().getStatusCode() == 409) {
      throw new EmailAlreadyExistsException();
    } else {
      throw new TooGenericQueryException("Keycloak exception");
    }
  }

  public void updateUser(KeycloakUser user) throws IOException {
    logger.info("Request to update user ");
    Token token = new Token();
    token = fetchAccessToken();
    String userId = fetchUserIdByUsername(token, user.username).id;
    String userEndpoint = keycloakBaseUrl + "/auth/admin/realms/" + keycloakBaseRealm + "/users/" + userId;
    final HttpClient client = new DefaultHttpClient();
    HttpPut post = new HttpPut(userEndpoint);
    post.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.access_token);
    post.setHeader("content-type", "application/json; charset=utf-8");
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(user);
    logger.info("UserDetails: " + json);
    post.setEntity(new StringEntity(json, "UTF-8"));
    // get the response code
    HttpResponse response;
    response = client.execute(post);
  }

  public void updateUserAttribute(String username, String attributeName, String attributeValue) throws IOException {
    logger.info("Request to update user ");
    Token token = new Token();
    token = fetchAccessToken();
    KeycloakUser user = fetchUserIdByUsername(token, username);
    String userEndpoint = keycloakBaseUrl + "/auth/admin/realms/" + keycloakBaseRealm + "/users/" + user.id;
    final HttpClient client = new DefaultHttpClient();
    HttpPut post = new HttpPut(userEndpoint);
    post.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.access_token);
    post.setHeader("content-type", "application/json; charset=utf-8");
    user.attributes.put(attributeName, attributeValue);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("attributes", user.attributes);
    logger.info("Attributes: " + jsonObject);
    post.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
    // get the response code
    HttpResponse response;
    response = client.execute(post);
    logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
  }

  public KeycloakUser fetchUserIdByUsername(Token token, String username) {
    try {
      logger.info("Request to fetch userId ");
      String usersearchendpoint = keycloakBaseUrl + "/auth/admin/realms/" + keycloakBaseRealm + "/users?username="
          + username;
      final HttpClient client = new DefaultHttpClient();
      HttpGet get = new HttpGet(usersearchendpoint);
      get.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.access_token);
      get.setHeader("content-type", "application/json");

      // get the response code
      HttpResponse response;
      response = client.execute(get);
      // logger.info("response: " + response);
      if (response.getStatusLine().getStatusCode() == 200) {
        logger.info("user-id fetched successfully");
        String jsonString = EntityUtils.toString(response.getEntity());
        // logger.info("response: " + jsonString);
        JsonArray jsonret = new JsonParser().parse(jsonString).getAsJsonArray();
        if (jsonret.size() > 0) {
          JsonElement element = jsonret.get(0);
          JsonObject jsonbObject = element.getAsJsonObject();
          logger.info("user: " + jsonbObject.toString());
          logger.info("user id: " + jsonbObject.get("id").getAsString());
          return (new Gson()).fromJson(jsonbObject.toString(), KeycloakUser.class);
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
    return null;
  }

  public Integer fetchUsersCount(Token token) {
    int count = 0;
    try {
      logger.info("Request to fetch user count ");
      String roleEndpoint = keycloakBaseUrl + "/auth/admin/realms/" + keycloakBaseRealm + "/users/count";
      final HttpClient client = new DefaultHttpClient();
      HttpGet get = new HttpGet(roleEndpoint);
      get.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.access_token);
      get.setHeader("content-type", "application/json");

      // get the response code
      HttpResponse response;
      response = client.execute(get);
      // logger.info("response: " + response);
      // Key = rolename , Value = ID
      if (response.getStatusLine().getStatusCode() == 200) {
        logger.info("Users count fetched sucessfully");
        String jsonString = EntityUtils.toString(response.getEntity());
        count = new Integer(jsonString);
        // logger.info("response: " + jsonString);
      } else {
        logger.severe("Error during fetchUsersCount");
        count = -1;
      }
    } catch (IOException ex) {
      logger.severe("Error during fetchUsersCount" + ex.getMessage());
      count = -1;
    }
    return count;
  }

  public void resetUsersPassword(Token token, String password, String userId) {
    try {
      logger.info("Request to reset user's password ");
      String userPasswordEndpoint = keycloakBaseUrl + "/auth/admin/realms/" + keycloakBaseRealm + "/users/" + userId
          + "/reset-password";
      final HttpClient client = new DefaultHttpClient();
      HttpPut put = new HttpPut(userPasswordEndpoint);
      put.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.access_token);
      put.setHeader("content-type", "application/json");
      KeycloakCredentials credentials = new KeycloakCredentials("password", password, false);
      ObjectMapper mapper = new ObjectMapper();
      String json = mapper.writeValueAsString(credentials);
      // logger.info("json: " + json);
      put.setEntity(new StringEntity(json));
      // get the response code
      HttpResponse response;
      response = client.execute(put);
      // logger.info("response: " + response);
      if (response.getStatusLine().getStatusCode() == 204) {
        logger.info("Password reset successfully");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public Map<String, String> fetchRoles(Token token, String userId) {
    Map<String, String> roleMap = new HashMap<>();
    try {
      logger.info("Request to fetch roles for userId ");
      String roleEndpoint = keycloakBaseUrl + "/auth/admin/realms/" + keycloakBaseRealm + "/users/" + userId
          + "/role-mappings/realm/available";
      final HttpClient client = new DefaultHttpClient();
      HttpGet get = new HttpGet(roleEndpoint);
      get.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.access_token);
      get.setHeader("content-type", "application/json");

      // get the response code
      HttpResponse response;
      response = client.execute(get);
      // logger.info("response: " + response);
      // Key = rolename , Value = ID
      if (response.getStatusLine().getStatusCode() == 200) {
        logger.info("roles fetched sucessfully");
        String jsonString = EntityUtils.toString(response.getEntity());
        // logger.info("response: " + jsonString);
        JsonArray jsonret = new JsonParser().parse(jsonString).getAsJsonArray();
        for (int i = 0; i < jsonret.size(); i++) {
          JsonElement roleelement = jsonret.get(i);
          JsonObject roleobject = roleelement.getAsJsonObject();
          String roleid = roleobject.get("id").getAsString();
          // String rolename = "axip";
          String roleName = roleobject.get("name").getAsString();
          // logger.info("Role id: " + roleid + " " + roleName);
          roleMap.put(roleName, roleid);
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return roleMap;
  }

  public void assignRolesToUserID(Token token, KeycloakUser user, String userId, Map<String, String> roleMap) {
    try {
      logger.info("Request to modify roles ");
      String roleEndpoint = keycloakBaseUrl + "/auth/admin/realms/" + keycloakBaseRealm + "/users/" + userId
          + "/role-mappings/realm";
      final HttpClient client = new DefaultHttpClient();
      HttpPost post = new HttpPost(roleEndpoint);
      post.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token.access_token);
      post.setHeader("content-type", "application/json; charset=utf-8");

      // String[] roleNames = user.clientRoles.get("backend-service");
      String[] roleNames = user.realmRoles;
      KeycloakRole[] roles = new KeycloakRole[roleNames.length];
      for (int i = 0; i < roleNames.length; i++) {
        KeycloakRole role = new KeycloakRole();
        role.composite = false;
        role.clientRole = false;
        role.name = roleNames[i];
        role.containerId = keycloakBaseRealm;
        role.id = roleMap.get(role.name);
        roles[i] = role;
      }
      ObjectMapper mapper = new ObjectMapper();
      String json = mapper.writeValueAsString(roles);
      // logger.info("json: " + json);
      post.setEntity(new StringEntity(json, "UTF-8"));
      // get the response code
      HttpResponse response;
      response = client.execute(post);
      // logger.info("response: " + response);
      if (response.getStatusLine().getStatusCode() == 204) {
        logger.info("Roles Assigned Successfully ");
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @Transactional
  public KeycloakUser addUser(KeycloakUser user, String password)
      throws UsernameAlreadyExistsException, EmailAlreadyExistsException, TooGenericQueryException, IOException {
    // ---Step 1 Fetch Access Token -----------------
    Token token = new Token();
    token = fetchAccessToken();
    // check if user exists already
    KeycloakUser keycloakUser = fetchUserIdByUsername(token, user.username);
    if (keycloakUser != null) {
      logger.info("User exists already: " + user.username);
      throw new UsernameAlreadyExistsException();
      // deleteUser(token, existingUserId);
    }
    // ---Step 2 Request to add User -----------------
    requestToAddUser(token, user);
    // ---Step 3 Fetch UserID-------------------------
    String userId = fetchUserIdByUsername(token, user.username).id;
    // ---Step 4 Reset User's Password-----------------
    resetUsersPassword(token, password, userId);
    // ---Step 5 Fetch roles for userId ---------------
    Map<String, String> roleMap = new HashMap<>();
    roleMap = fetchRoles(token, userId);
    // ---STep 6 Assign Roles -------------------------
    assignRolesToUserID(token, user, userId, roleMap);
    return user;
  }

}
