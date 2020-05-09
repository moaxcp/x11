package com.github.moaxcp.x11client;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Strings {
  /**
   * Checks that a variable is not blank and returns its value.
   * @param name of variable to check
   * @param value of variable to check
   * @return the value of the variable
   * @throws IllegalArgumentException if name or value is blank
   * @throws NullPointerException if name or value is null
   */
  public String requireNonBlank(@NonNull String name, @NonNull String value) {
    if(name.trim().isEmpty()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    if(value.trim().isEmpty()) {
      throw new IllegalArgumentException(String.format("%s must not be blank", name));
    }
    return value;
  }
}
