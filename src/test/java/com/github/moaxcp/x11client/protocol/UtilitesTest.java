package com.github.moaxcp.x11client.protocol;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.moaxcp.x11client.protocol.Utilities.toList;
import static com.github.moaxcp.x11client.protocol.Utilities.toIntegers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UtilitesTest {
  @Test
  void toList_byte_fails_on_null() {
    assertThatThrownBy(() -> toList(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("bytes is marked non-null but is null");
  }

  @Test
  void toList_byte() {
    assertThat(toList(new byte[]{0, 1, 2, 3})).containsExactly((byte) 0, (byte) 1, (byte) 2, (byte) 3);
  }

  @Test
  void toInteger_fails_on_null() {
    assertThatThrownBy(() -> toIntegers(null))
      .isInstanceOf(NullPointerException.class)
      .hasMessage("bytes is marked non-null but is null");
  }

  @Test
  void toInteger_fails_on_size() {
    List<Byte> input = new ArrayList<>();
    input.add((byte) 1);
    assertThatThrownBy(() -> toIntegers(input))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("bytes must have size divisible by 4 to be converted to integers got: 1");
  }

  @Test
  void toInteger_success() {
    List<Byte> bytes = new ArrayList<>();
    bytes.add((byte) 0b11111110);
    bytes.add((byte) 0b11111101);
    bytes.add((byte) 0b11111011);
    bytes.add((byte) 0b11110111);

    assertThat(toIntegers(bytes)).containsExactly(0b11111110_11111101_11111011_11110111);
  }
}
