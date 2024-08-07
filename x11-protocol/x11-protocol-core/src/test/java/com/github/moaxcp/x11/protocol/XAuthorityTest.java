package com.github.moaxcp.x11.protocol;

import com.github.moaxcp.x11.protocol.XAuthority.Family;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.DataInput;
import java.io.IOException;
import java.util.Optional;

import static com.github.moaxcp.x11.protocol.Utilities.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XAuthorityTest {

  @Test
  void constructor_fails_on_null_family() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(null, toList("hostName".getBytes()), 0, toList("magic".getBytes()), toList(new byte[] {1})));
    assertThat(exception).hasMessage("family is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_null_address() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(Family.LOCAL, null, 0, toList("magic".getBytes()), toList(new byte[] {1})));
    assertThat(exception).hasMessage("address is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_negative_displayNumber() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(Family.LOCAL, toList("host".getBytes()), -1, toList("magic".getBytes()), toList(new byte[] {1})));
    assertThat(exception).hasMessage("displayNumber was \"-1\" expected >= 0.");
  }

  @Test
  void constructor_fails_on_null_protocolName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(Family.LOCAL, toList("host".getBytes()), 0, null, toList(new byte[] {1})));
    assertThat(exception).hasMessage("protocolName is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_blank_protocolName() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(Family.LOCAL, toList("host".getBytes()), 0, toList(new byte[]{}), toList(new byte[] {1})));
    assertThat(exception).hasMessage("protocolName must not be empty");
  }

  @Test
  void constructor_fails_on_null_protocolData() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(Family.LOCAL, toList("host".getBytes()), 0, toList("MIT-MAGIC-COOKIE-1".getBytes()), null));
    assertThat(exception).hasMessage("protocolData is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_empty_protocolData() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(Family.LOCAL, toList(new byte[]{1}), 0, toList("MIT-MAGIC-COOKIE-1".getBytes()), toList(new byte[] {})));
    assertThat(exception).hasMessage("protocolData must not be empty");
  }

  @Test
  void constructor() {
    XAuthority xAuthority = new XAuthority(Family.LOCAL, toList("host".getBytes()), 0, toList("MIT-MAGIC-COOKIE-1".getBytes()), toList(new byte[] {1, 2, 3}));
    assertThat(xAuthority.getFamily()).isEqualTo(Family.LOCAL);
    assertThat(xAuthority.getAddress()).isEqualTo(toList("host".getBytes()));
    assertThat(xAuthority.getDisplayNumber()).isEqualTo(0);
    assertThat(xAuthority.getProtocolName()).isEqualTo(toList("MIT-MAGIC-COOKIE-1".getBytes()));
    assertThat(xAuthority.getProtocolData()).isEqualTo(toList(new byte[] {1, 2, 3}));
  }

  private static void fillBytes(byte[] bytes) {
    for(int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) i;
    }
  }

  @Test
  void read_IOException() throws IOException {
    DataInput in = Mockito.mock(DataInput.class);
    IOException cause = new IOException("expected cause");
    Mockito.when(in.readUnsignedShort()).thenThrow(cause);
    Optional<XAuthority> read = XAuthority.read(in);
    assertThat(read).isEmpty();
  }
}
