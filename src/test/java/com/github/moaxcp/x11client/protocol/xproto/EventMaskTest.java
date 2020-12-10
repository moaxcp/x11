package com.github.moaxcp.x11client.protocol.xproto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventMaskTest {
  @Test
  void enableNoEvent() {
    assertThat(EventMask.NO_EVENT.enableFor(0)).isEqualTo(0b0);
  }

  @Test
  void enableKeyPress() {
    assertThat(EventMask.KEY_PRESS.enableFor(0)).isEqualTo(0b1);
  }

  @Test
  void enableKeyRelease() {
    assertThat(EventMask.KEY_RELEASE.enableFor(0)).isEqualTo(0b10);
  }

  @Test
  void isEnabledKeyPress() {
    assertThat(EventMask.KEY_PRESS.isEnabled(0b1)).isTrue();
  }

  @Test
  void isNotEnabledKeyPress() {
    assertThat(EventMask.KEY_PRESS.isEnabled(0b0)).isFalse();
  }
}
