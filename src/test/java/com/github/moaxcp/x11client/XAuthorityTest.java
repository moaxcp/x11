package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.XAuthority.Family;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XAuthorityTest {

  @Test
  void constructor_fails_on_null_family() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(null, "hostName".getBytes(), 0, "magic", new byte[1]));
    assertThat(exception).hasMessage("family is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_null_address() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(Family.LOCAL, null, 0, "magic", new byte[1]));
    assertThat(exception).hasMessage("address is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_empty_address() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(Family.LOCAL, new byte[]{}, 0, "magic", new byte[1]));
    assertThat(exception).hasMessage("address must not be empty");
  }

  @Test
  void constructor_fails_on_negative_displayNumber() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(Family.LOCAL, "host".getBytes(), -1, "magic", new byte[1]));
    assertThat(exception).hasMessage("displayNumber was \"-1\" expected >= 0.");
  }

  @Test
  void constructor_fails_on_null_protocolName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(Family.LOCAL, "host".getBytes(), 0, null, new byte[1]));
    assertThat(exception).hasMessage("protocolName is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_blank_protocolName() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(Family.LOCAL, "host".getBytes(), 0, " ", new byte[1]));
    assertThat(exception).hasMessage("protocolName must not be blank");
  }

  @Test
  void constructor_fails_on_null_protocolData() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new XAuthority(Family.LOCAL, "host".getBytes(), 0, "magic", null));
    assertThat(exception).hasMessage("protocolData is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_empty_protocolData() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new XAuthority(Family.LOCAL, new byte[]{1}, 0, "magic", new byte[0]));
    assertThat(exception).hasMessage("protocolData must not be empty");
  }

  @Test
  void constructor() {
    XAuthority xAuthority = new XAuthority(Family.LOCAL, "host".getBytes(), 0, "magic", new byte[]{1, 2, 3});
    assertThat(xAuthority.getFamily()).isEqualTo(Family.LOCAL);
    assertThat(xAuthority.getAddress()).isEqualTo("host".getBytes());
    assertThat(xAuthority.getDisplayNumber()).isEqualTo(0);
    assertThat(xAuthority.getProtocolName()).isEqualTo("magic");
    assertThat(xAuthority.getProtocolData()).isEqualTo(new byte[]{1, 2, 3});
  }

}
