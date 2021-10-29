package com.alkemy.ong.config;

public enum ApplicationRole {
  USER("USER"),
  ADMIN("ADMIN");

  private final String name;
  private static final String ROLE_PREFIX = "ROLE_";

  ApplicationRole(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getFullRoleName() {
    return ROLE_PREFIX + name;
  }
}
