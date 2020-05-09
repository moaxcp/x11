package com.github.moaxcp.x11client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DisplayNameTest {
  @Test
  void constructor() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new DisplayName(null));
    assertThat(exception).hasMessage("displayName is marked non-null but is null");
  }
}
