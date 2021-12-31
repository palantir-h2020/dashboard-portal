package eu.ubitech.palantir.portal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
  public String access_token;
  public String expires_in;
  public String refresh_expires_in;
  public String refresh_token;
  public String token_type;
  @JsonProperty("not-before-policy")
  public String not_before_policy;
  public String session_state;
  public String scope;
}
