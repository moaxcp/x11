package com.github.moaxcp.x11client.protocol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PopcountTest {
  @Test
  void popcountTest() {
    assertThat(Popcount.popcount(0b10101010101)).isEqualTo(6);
  }
}
