package com.github.moaxcp.x11client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class X11ClientExceptionTest {
  @Test
  void constructor_nullMessage() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11ClientException(null));
    assertThat(exception).hasMessage("message is marked non-null but is null");
  }

  @Test
  void constructor_message() {
    X11ClientException exception = new X11ClientException("message");
    assertThat(exception).hasMessage("message");
  }
}
