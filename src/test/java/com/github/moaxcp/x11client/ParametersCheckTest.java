package com.github.moaxcp.x11client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.github.moaxcp.x11client.ParametersCheck.requireNonBlank;
import static com.github.moaxcp.x11client.ParametersCheck.requireNonEmpty;

public class ParametersCheckTest {
  @Test
  void requireNonBlank_fails_with_null_name() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonBlank(null, "A"));
    assertThat(exception).hasMessage("name is marked non-null but is null");
  }

  @Test
  void requreNonBlank_fails_with_null_value() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonBlank("value", null));
    assertThat(exception).hasMessage("value is marked non-null but is null");
  }

  @Test
  void requireNonBlank_fails_with_blank_name() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requireNonBlank(" ", "A"));
    assertThat(exception).hasMessage("name must not be blank");
  }

  @Test
  void requireNonBlank_fails_with_blank_value() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requireNonBlank("value", " "));
    assertThat(exception).hasMessage("value must not be blank");
  }

  @Test
  void requireNonBlank_returns_value() {
    String value = requireNonBlank("value", "A");
    assertThat(value).isEqualTo("A");
  }






  @Test
  void requireNonEmpty_bytes_fails_with_null_name() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonEmpty(null, new byte[]{1}));
    assertThat(exception).hasMessage("name is marked non-null but is null");
  }

  @Test
  void requireNonEmpty_bytes_fails_with_null_value() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonEmpty("value", null));
    assertThat(exception).hasMessage("value is marked non-null but is null");
  }

  @Test
  void requireNonEmpty_bytes_fails_with_blank_name() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requireNonEmpty(" ", new byte[]{1}));
    assertThat(exception).hasMessage("name must not be blank");
  }

  @Test
  void requireNonEmpty_bytes_fails_with_empty_value() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requireNonEmpty("value", new byte[]{}));
    assertThat(exception).hasMessage("value must not be empty");
  }

  @Test
  void requireNonEmpty_bytes_returns_value() {
    byte[] value = requireNonEmpty("value", new byte[]{1});
    assertThat(value).isEqualTo(new byte[]{1});
  }
}
