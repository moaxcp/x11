package com.github.moaxcp.x11.x11client;

import java.io.IOException;
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

  @Test
  void constructor2_nullMessage() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11ClientException(null, null));
    assertThat(exception).hasMessage("message is marked non-null but is null");
  }

  @Test
  void constructor2_nullCause() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11ClientException("message", null));
    assertThat(exception).hasMessage("cause is marked non-null but is null");
  }

  @Test
  void constructor_message_and_cause() {
    Throwable cause = new IOException();
    X11ClientException exception = new X11ClientException("message", cause);
    assertThat(exception).hasMessage("message");
    assertThat(exception).hasCause(cause);
  }
}
