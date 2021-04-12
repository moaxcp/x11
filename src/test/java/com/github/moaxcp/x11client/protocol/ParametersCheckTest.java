package com.github.moaxcp.x11client.protocol;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.moaxcp.x11client.protocol.ParametersCheck.requireNonBlank;
import static com.github.moaxcp.x11client.protocol.ParametersCheck.requireNonEmpty;
import static com.github.moaxcp.x11client.protocol.Utilities.toList;
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
    NullPointerException exception = assertThrows(NullPointerException.class, () -> ParametersCheck.requireNonBlank("value", (String) null));
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
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonEmpty("value", (byte[])null));
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



  @Test
  void requireNonEmpty_list_bytes_fails_with_null_name() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonEmpty(null, toList(new byte[]{1})));
    assertThat(exception).hasMessage("name is marked non-null but is null");
  }

  @Test
  void requireNonEmpty_list_bytes_fails_with_null_value() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> requireNonEmpty("value", (List<Byte>)null));
    assertThat(exception).hasMessage("value is marked non-null but is null");
  }

  @Test
  void requireNonEmpty_list_bytes_fails_with_blank_name() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requireNonEmpty(" ", toList(new byte[]{1})));
    assertThat(exception).hasMessage("name must not be blank");
  }

  @Test
  void requireNonEmpty_list_bytes_fails_with_empty_value() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> requireNonEmpty("value", toList(new byte[]{})));
    assertThat(exception).hasMessage("value must not be empty");
  }

  @Test
  void requireNonEmpty_list_bytes_returns_value() {
    List<Byte> value = requireNonEmpty("value", toList(new byte[]{1}));
    assertThat(value).isEqualTo(toList(new byte[]{1}));
  }
}
