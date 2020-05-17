package com.github.moaxcp.x11client;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ConnectionFailureTest {
  @Test
  void readFailure() throws IOException {
    X11InputStream in = mock(X11InputStream.class);
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
