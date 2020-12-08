package com.github.moaxcp.x11client;

import java.io.DataInput;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11client.XAuthority.Family.LOCAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class XAuthorityTest {

  @Test
  void constructor_fails_on_null_family() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(null, "hostName".getBytes(), 0, "magic", new byte[] {1}));
    assertThat(exception).hasMessage("family is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_null_address() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(LOCAL, null, 0, "magic", new byte[] {1}));
    assertThat(exception).hasMessage("address is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_empty_address() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(LOCAL, new byte[]{}, 0, "magic", new byte[] {1}));
    assertThat(exception).hasMessage("address must not be empty");
  }

  @Test
  void constructor_fails_on_negative_displayNumber() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(LOCAL, "host".getBytes(), -1, "magic", new byte[] {1}));
    assertThat(exception).hasMessage("displayNumber was \"-1\" expected >= 0.");
  }

  @Test
  void constructor_fails_on_null_protocolName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(LOCAL, "host".getBytes(), 0, null, new byte[] {1}));
    assertThat(exception).hasMessage("protocolName is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_blank_protocolName() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(LOCAL, "host".getBytes(), 0, " ", new byte[] {1}));
    assertThat(exception).hasMessage("protocolName must not be blank");
  }

  @Test
  void constructor_fails_on_null_protocolData() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(LOCAL, "host".getBytes(), 0, "MIT-MAGIC-COOKIE-1", null));
    assertThat(exception).hasMessage("protocolData is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_empty_protocolData() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(LOCAL, new byte[]{1}, 0, "MIT-MAGIC-COOKIE-1", new byte[] {}));
    assertThat(exception).hasMessage("protocolData must not be blank");
  }

  @Test
  void constructor() {
    XAuthority xAuthority = new XAuthority(LOCAL, "host".getBytes(), 0, "MIT-MAGIC-COOKIE-1", new byte[] {1, 2, 3});
    assertThat(xAuthority.getFamily()).isEqualTo(LOCAL);
    assertThat(xAuthority.getAddress()).isEqualTo("host".getBytes());
    assertThat(xAuthority.getDisplayNumber()).isEqualTo(0);
    assertThat(xAuthority.getProtocolName()).isEqualTo("MIT-MAGIC-COOKIE-1");
    assertThat(xAuthority.getProtocolData()).isEqualTo("123");
  }

  private static void fillBytes(byte[] bytes) {
    for(int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) i;
    }
  }

  @Test
  void read_IOException() throws IOException {
    DataInput in = mock(DataInput.class);
    IOException cause = new IOException("expected cause");
    when(in.readUnsignedShort()).thenThrow(cause);
    Optional<XAuthority> read = XAuthority.read(in);
    assertThat(read).isEmpty();
  }
}
