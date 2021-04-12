package com.github.moaxcp.x11client.protocol;

import lombok.Value;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntValueTest {
  @Value
  private class TestValue implements IntValue {
    int value;
  }

  @Test
  void isEnabled_true() {
    TestValue value = new TestValue(0b1);
    assertThat(value.isEnabled(0b1)).isTrue();
  }

  @Test
  void isEnabled_false() {
    TestValue value = new TestValue(0b1);
    assertThat(value.isEnabled(0b10)).isFalse();
  }

  @Test
  void enableFor() {
    TestValue value = new TestValue(0b01000);
    assertThat(value.enableFor(0b00001)).isEqualTo(0b01001);
  }

  @Test
  void disableFor() {
    TestValue value = new TestValue(0b01000);
    assertThat(value.disableFor(0b01111)).isEqualTo(0b00111);
  }
}
