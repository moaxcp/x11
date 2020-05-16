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
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
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
  void constructor() throws IOException {
    InputStream in = mock(InputStream.class);
    OutputStream out = mock(OutputStream.class);

    when(socket.getInputStream()).thenReturn(in);
    when(socket.getOutputStream()).thenReturn(out);

    when(in.read()).thenReturn(1);

    DisplayName name = new DisplayName(":0");
    X11Connection connection = new X11Connection(name, xAuthority, socket);

    then(out).should().write('B');
    then(out).should().write(0);
    then(out).should().write(0);
    then(out).should().write(11);

    assertThat(connection.getDisplayName()).isEqualTo(name);
    assertThat(connection.getXAuthority()).isEqualTo(xAuthority);
    assertThat(connection.getSocket()).isEqualTo(socket);
  }
}
