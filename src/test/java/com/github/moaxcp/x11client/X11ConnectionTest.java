package com.github.moaxcp.x11client;

import java.io.*;
import java.net.Socket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.moaxcp.x11client.X11Connection.connect;
import static com.github.moaxcp.x11client.XAuthority.Family.LOCAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class X11ConnectionTest {
  @Mock
  private Socket socket;

  private XAuthority xAuthority = new XAuthority(LOCAL, "host".getBytes(), 0, "MIT-MAGIC-COOKIE-1", new byte[]{1, 2, 3});

  @Test
  void constructor_fails_on_null_displayName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11Connection(null, xAuthority, socket));
    assertThat(exception).hasMessage("displayName is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_null_xAuthority() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11Connection(new DisplayName(":0"), null, socket));
    assertThat(exception).hasMessage("xAuthority is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_null_socket() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11Connection(new DisplayName(":0"), xAuthority, null));
    assertThat(exception).hasMessage("socket is marked non-null but is null");
  }

  @Test
  void connect_fails_on_null_displayName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> connect(null, xAuthority));
    assertThat(exception).hasMessage("displayName is marked non-null but is null");
  }

  @Test
  void connect_fails_on_null_xAuthority() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> connect(new DisplayName((":0")), null));
    assertThat(exception).hasMessage("xAuthority is marked non-null but is null");
  }

  @Test
  void constructor_ConnectionFailure() throws IOException {
    byte[] inBytes = new byte[]{0, 5, 0, 11, 0, 0, 0, 0, 'H', 'e', 'l', 'l', 'o', 0, 0, 0};
    ByteArrayInputStream in = new ByteArrayInputStream(inBytes);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    when(socket.getInputStream()).thenReturn(in);
    when(socket.getOutputStream()).thenReturn(out);

    DisplayName name = new DisplayName(":0");
    ConnectionFailureException exception = assertThrows(ConnectionFailureException.class, () -> new X11Connection(name, xAuthority, socket));

    assertThat(exception.getFailure()).isEqualTo(new ConnectionFailure(11, 0, "Hello"));
    assertThat(out.toByteArray()).containsExactly(
        'B',
        0,
        0, 11,
        0, 0,
        0, xAuthority.getProtocolName().length(),
        0, xAuthority.getProtocolData().length,
        0, 0,
        'M', 'I', 'T', '-', 'M', 'A', 'G', 'I', 'C', '-', 'C', 'O', 'O', 'K', 'I', 'E', '-', '1',
        0, 0,
        1, 2, 3,
        0
    );
  }

  @Test
  void constructor_Authenticate() throws IOException {
    byte[] inBytes = new byte[] {2};
    ByteArrayInputStream in = new ByteArrayInputStream(inBytes);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    when(socket.getInputStream()).thenReturn(in);
    when(socket.getOutputStream()).thenReturn(out);

    DisplayName name = new DisplayName(":0");
    UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> new X11Connection(name, xAuthority, socket));

    assertThat(exception).hasMessage("authenticate not supported");
    assertThat(out.toByteArray()).containsExactly(
        'B',
        0,
        0, 11,
        0, 0,
        0, xAuthority.getProtocolName().length(),
        0, xAuthority.getProtocolData().length,
        0, 0,
        'M', 'I', 'T', '-', 'M', 'A', 'G', 'I', 'C', '-', 'C', 'O', 'O', 'K', 'I', 'E', '-', '1',
        0, 0,
        1, 2, 3,
        0
    );
  }

  @Test
  void constructor_Success() throws IOException {
    byte[] inBytes = new byte[] {1};
    ByteArrayInputStream in = new ByteArrayInputStream(inBytes);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    when(socket.getInputStream()).thenReturn(in);
    when(socket.getOutputStream()).thenReturn(out);

    ConnectionSuccess success = ConnectionSuccess.builder()
        .protocolMajorVersion(11)
        .protocolMinorVersion(0)
        .releaseNumber(7)
        .resourceIdBase(10)
        .resourceIdMask(1111)
        .motionBufferSize(12)
        .build();

    DisplayName name = new DisplayName(":0");
    X11Connection connection = new X11Connection(name, xAuthority, socket);

    assertThat(connection.getDisplayName()).isEqualTo(name);
    assertThat(connection.getXAuthority()).isEqualTo(xAuthority);
    assertThat(connection.getConnectionSuccess()).isEqualTo(success);

    assertThat(out.toByteArray()).containsExactly(
        'B',
        0,
        0, 11,
        0, 0,
        0, xAuthority.getProtocolName().length(),
        0, xAuthority.getProtocolData().length,
        0, 0,
        'M', 'I', 'T', '-', 'M', 'A', 'G', 'I', 'C', '-', 'C', 'O', 'O', 'K', 'I', 'E', '-', '1',
        0, 0,
        1, 2, 3,
        0
    );
  }
}
