package com.github.moaxcp.x11.protocol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtomValueTest {
  @Test
  void null_name_fails() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new AtomValue(1, null));
    assertThat(exception).hasMessage("name is marked non-null but is null");
  }
}
