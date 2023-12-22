package com.github.moaxcp.x11.protocol;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UtilitesTest {
  @Test
  void toList_byte_fails_on_null() {
    assertThatThrownBy(() -> Utilities.toList(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("bytes is marked non-null but is null");
  }

  @Test
  void toList_byte() {
    Assertions.assertThat(Utilities.toList(new byte[]{0, 1, 2, 3})).containsExactly((byte) 0, (byte) 1, (byte) 2, (byte) 3);
  }

  @Test
  void toString_fails_on_null_byteList() {
    assertThatThrownBy(() -> Utilities.toString(null, Charset.defaultCharset()))
    .isInstanceOf(NullPointerException.class)
    .hasMessage("byteList is marked non-null but is null");
  }

  @Test
  void toString_fails_on_null_charSet() {
    assertThatThrownBy(() -> Utilities.toString(new ArrayList<>(), null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("charset is marked non-null but is null");
  }

  @Test
  void toString_test() {
    List<Byte> bytes = new ArrayList<>();
    bytes.add((byte) 'h');
    bytes.add((byte) 'e');
    bytes.add((byte) 'l');
    bytes.add((byte) 'l');
    bytes.add((byte) 'o');
    String result = Utilities.toString(bytes, Charset.defaultCharset());
    assertThat(result).isEqualTo("hello");
  }

  @Test
  void toInteger_fails_on_null() {
    assertThatThrownBy(() -> Utilities.toIntegers(null))
      .isInstanceOf(NullPointerException.class)
      .hasMessage("bytes is marked non-null but is null");
  }

  @Test
  void toInteger_fails_on_size() {
    List<Byte> input = new ArrayList<>();
    input.add((byte) 1);
    assertThatThrownBy(() -> Utilities.toIntegers(input))
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

    Assertions.assertThat(Utilities.toIntegers(bytes)).containsExactly(0b11111110_11111101_11111011_11110111);
  }
}
