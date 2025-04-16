package com.everyplaceinkorea.epik_boot3_api.auth.oauth2.dto;

import java.util.Map;

public class GoogleResponse implements OAuth2Response {

  private final Map<String, Object> attributes;

  public GoogleResponse(Map<String, Object> attributes) {
    this.attributes = attributes;
    System.out.println("Google attributes: " + attributes);
  }

  @Override
  public String getProvider() {
    return "google";
  }

  @Override
  public String getProviderId() {
    return attributes.get("sub").toString();
  }

  @Override
  public String getEmail() {
    return attributes.get("email") != null ? attributes.get("email").toString() : "";
  }

  @Override
  public String getName() {
    return attributes.get("name") != null ? attributes.get("name").toString() : "";
  }

  @Override
  public String getProfileImage() {
    return attributes.get("picture") != null ? attributes.get("picture").toString() : getDefaultProfileImage();
  }

  private String getDefaultProfileImage() {
    return "/images/basic.png";
  }
}