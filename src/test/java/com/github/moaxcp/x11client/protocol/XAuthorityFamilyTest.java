package com.github.moaxcp.x11client.protocol;

import com.github.moaxcp.x11client.protocol.XAuthority.Family;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XAuthorityFamilyTest {
  @Test
  void internetCode() {
    AssertionsForClassTypes.assertThat(Family.INTERNET.getCode()).isEqualTo(0);
  }
  @Test
  void localCode() {
    AssertionsForClassTypes.assertThat(Family.LOCAL.getCode()).isEqualTo(256);
  }
  @Test
  void wildCode() {
    AssertionsForClassTypes.assertThat(Family.WILD.getCode()).isEqualTo(65535);
  }
  @Test
  void krb5principalCode() {
    AssertionsForClassTypes.assertThat(Family.KRB5PRINCIPAL.getCode()).isEqualTo(254);
  }
  @Test
  void localhostCode() {
    AssertionsForClassTypes.assertThat(Family.LOCALHOST.getCode()).isEqualTo(252);
  }

  @Test
  void internet_getByCode() {
    assertThat(Family.getByCode(0)).isEqualTo(Family.INTERNET);
  }

  @Test
  void local_getByCode() {
    assertThat(Family.getByCode(256)).isEqualTo(Family.LOCAL);
  }

  @Test
  void wild_getByCode() {
    assertThat(Family.getByCode(65535)).isEqualTo(Family.WILD);
  }

  @Test
  void krb_getByCode() {
    assertThat(Family.getByCode(254)).isEqualTo(Family.KRB5PRINCIPAL);
  }

  @Test
  void localhost_getByCode() {
    assertThat(Family.getByCode(252)).isEqualTo(Family.LOCALHOST);
  }

  @Test
  void fail_getByCode() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Family.getByCode(-1234));
    AssertionsForClassTypes.assertThat(exception).hasMessage("Unsupported code \"-1234\"");
  }
}
