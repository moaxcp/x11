package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.DisplayName;
import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.XAuthority;
import com.github.moaxcp.x11.protocol.XAuthority.Family;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class X11ConnectionTest {
  @Mock
  private Socket socket;

  private XAuthority xAuthority = new XAuthority(Family.LOCAL, Utilities.toList("host".getBytes()), 0, Utilities.toList("MIT-MAGIC-COOKIE-1".getBytes()), Utilities.toList(new byte[] { 1, 2, 3 }));

  @Test
  void constructor_fails_on_null_displayName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11Connection(null, xAuthority, socket));
    assertThat(exception).hasMessage("displayName");
  }

  @Test
  void constructor_fails_on_null_xAuthority() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11Connection(new DisplayName(":0"), null, socket));
    assertThat(exception).hasMessage("xAuthority");
  }

  @Test
  void constructor_fails_on_null_socket() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11Connection(new DisplayName(":0"), xAuthority, null));
    assertThat(exception).hasMessage("socket");
  }

  @Test
  void connect_fails_on_null_displayName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> X11Connection.connect(null, xAuthority));
    assertThat(exception).hasMessage("Cannot invoke \"com.github.moaxcp.x11.protocol.DisplayName.isForUnixSocket()\" because \"displayName\" is null");
  }

  @Test
  void connect_fails_on_null_xAuthority() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> X11Connection.connect(new DisplayName((":0")), null));
    assertThat(exception).hasMessage("xAuthority");
  }
}
