package com.github.moaxcp.x11client;

import java.util.List;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11client.ParametersCheck.requireNonBlank;
import static com.github.moaxcp.x11client.ParametersCheck.requireNonEmpty;
import static com.github.moaxcp.x11client.Utilities.byteArrayToList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParametersCheckTest {
  @Test
  void requireNonBlank_fails_with_null_name() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonBlank(null, "A"));
    assertThat(exception).hasMessage("name is marked non-null but is null");
  }

  @Test
  void requreNonBlank_fails_with_null_value() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonBlank("value", (byte[]) null));
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
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonEmpty(null, byteArrayToList(new byte[]{1})));
    assertThat(exception).hasMessage("name is marked non-null but is null");
  }

  @Test
  void requireNonEmpty_bytes_fails_with_null_value() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonEmpty("value", (List<Byte>)null));
    assertThat(exception).hasMessage("value is marked non-null but is null");
  }

  @Test
  void requireNonEmpty_bytes_fails_with_blank_name() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requireNonEmpty(" ", byteArrayToList(new byte[]{1})));
    assertThat(exception).hasMessage("name must not be blank");
  }

  @Test
  void requireNonEmpty_bytes_fails_with_empty_value() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requireNonEmpty("value", byteArrayToList(new byte[]{})));
    assertThat(exception).hasMessage("value must not be empty");
  }

  @Test
  void requireNonEmpty_bytes_returns_value() {
    List<Byte> value = requireNonEmpty("value", byteArrayToList(new byte[]{1}));
    assertThat(value).isEqualTo(byteArrayToList(new byte[]{1}));
  }
}
