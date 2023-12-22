package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.XAuthority;
import com.github.moaxcp.x11.protocol.XAuthority.Family;
import com.github.moaxcp.x11.protocol.DisplayName;
import java.net.Socket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.moaxcp.x11.x11client.X11Connection.connect;
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
    NullPointerException exception = assertThrows(NullPointerException.class, () -> X11Connection.connect(null, xAuthority));
    assertThat(exception).hasMessage("displayName is marked non-null but is null");
  }

  @Test
  void connect_fails_on_null_xAuthority() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> X11Connection.connect(new DisplayName((":0")), null));
    assertThat(exception).hasMessage("xAuthority is marked non-null but is null");
  }
}
