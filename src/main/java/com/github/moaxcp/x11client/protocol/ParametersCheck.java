package com.github.moaxcp.x11client.protocol;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
class ParametersCheck {
  /**
   * Checks that a variable is not blank and returns its value.
   * @param name of variable to check
   * @param value of variable to check
   * @return the value of the variable
   * @throws IllegalArgumentException if name or value is blank
   * @throws NullPointerException if name or value is null
   */
  public static String requireNonBlank(@NonNull String name, @NonNull String value) {
    if(name.trim().isEmpty()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    if(value.trim().isEmpty()) {
      throw new IllegalArgumentException(String.format("%s must not be blank", name));
    }
    return value;
  }
  /**
   * Checks that a variable is not empty and returns its value.
   * @param name of variable to check
   * @param value of variable to check
   * @return the value of the variable
   * @throws IllegalArgumentException if name or value is blank
   * @throws NullPointerException if name or value is null
   */
  public static byte[] requireNonEmpty(@NonNull String name, @NonNull byte[] value) {
    if(name.trim().isEmpty()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    if(value.length == 0) {
      throw new IllegalArgumentException(String.format("%s must not be empty", name));
    }
    return value;
  }

  /**
   * Checks that a variable is not empty and returns its value.
   * @param name of variable to check
   * @param value of variable to check
   * @return the value of the variable
   * @throws IllegalArgumentException if name is blank or if value is empty
   * @throws NullPointerException if name or value is null
   */
  public static List<Byte> requireNonEmpty(@NonNull String name, @NonNull List<Byte> value) {
    if(name.trim().isEmpty()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    if(value.isEmpty()) {
      throw new IllegalArgumentException(String.format("%s must not be empty", name));
    }
    return value;
  }
}
