package com.github.moaxcp.x11client;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ConnectionFailureTest {

  @Test
  void constructor_nullReason() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new ConnectionFailure(11, 0, null));
    assertThat(exception).hasMessage("reason is marked non-null but is null");
  }
  @Test
  void readFailure() throws IOException {
    X11Input in = mock(X11Input.class);
    when(in.readInt8()).thenReturn(5);
    when(in.readCard16()).thenReturn(11, 0, 0);
    when(in.readString8(5)).thenReturn("Hello");
    doNothing().when(in).readPad(5);
    ConnectionFailure failure = ConnectionFailure.readFailure(in);
    assertThat(failure.getProtocolMajorVersion()).isEqualTo(11);
    assertThat(failure.getProtocolMinorVersion()).isEqualTo(0);
    assertThat(failure.getReason()).isEqualTo("Hello");
  }
}
