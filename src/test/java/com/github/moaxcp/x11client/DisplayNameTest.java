package com.github.moaxcp.x11client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DisplayNameTest {
  @Test
  void constructor_fails_on_null_displayName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new DisplayName(null));
    assertThat(exception).hasMessage("displayName is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_blank_displayName() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new DisplayName(" "));
    assertThat(exception).hasMessage("displayName must not be blank");
  }

  @Test
  void constructor_hostName_only() {
    DisplayName name = new DisplayName("hostName");
    assertThat(name.getHostName()).isEqualTo("hostName");
  }

  @Test
  void constructor_displayNumber_not_a_number() {
    NumberFormatException exception = assertThrows(NumberFormatException.class, () -> new DisplayName(":asdb"));
    assertThat(exception).hasMessage("For input string: \"asdb\"");
  }

  @Test
  void constructor_displayNumber_only() {
    DisplayName name = new DisplayName(":12");
    assertThat(name.getHostName()).isNull();
    assertThat(name.getDisplayNumber()).isEqualTo(12);
    assertThat(name.getScreenNumber()).isEqualTo(0);
  }

  @Test
  void constructor_displayNumber_withScreenNumber() {
    DisplayName name = new DisplayName(":12.2");
    assertThat(name.getHostName()).isNull();
    assertThat(name.getDisplayNumber()).isEqualTo(12);
    assertThat(name.getScreenNumber()).isEqualTo(2);
  }

  @Test
  void constructor_all() {
    DisplayName name = new DisplayName("hostName:12.2");
    assertThat(name.getHostName()).isEqualTo("hostName");
    assertThat(name.getDisplayNumber()).isEqualTo(12);
    assertThat(name.getScreenNumber()).isEqualTo(2);
  }
}
