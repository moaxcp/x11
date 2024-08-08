package com.github.moaxcp.x11.protocol;

import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.factory.primitive.ByteLists;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class UtilitesTest {
  @Test
  void toList_byte_fails_on_null() {
    Assertions.assertThatThrownBy(() -> Utilities.toList(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("bytes is marked non-null but is null");
  }

  @Test
  void toList_byte() {
    Assertions.assertThat(Utilities.toList(new byte[]{0, 1, 2, 3})).isEqualTo(ByteLists.immutable.of((byte) 0, (byte) 1, (byte) 2, (byte) 3));
  }

  @Test
  void toString_fails_on_null_byteList() {
    Assertions.assertThatThrownBy(() -> Utilities.toString((ByteList) null, Charset.defaultCharset()))
    .isInstanceOf(NullPointerException.class)
    .hasMessage("byteList is marked non-null but is null");
  }

  @Test
  void toString_fails_on_null_charSet() {
    Assertions.assertThatThrownBy(() -> Utilities.toString(new ArrayList<>(), null))
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
    Assertions.assertThat(result).isEqualTo("hello");
  }

  @Test
  void toInteger_fails_on_null() {
    Assertions.assertThatThrownBy(() -> Utilities.toIntegers(null))
      .isInstanceOf(NullPointerException.class)
      .hasMessage("bytes is marked non-null but is null");
  }

  @Test
  void toInteger_fails_on_size() {
    var input = ByteLists.immutable.of((byte) 1);
    Assertions.assertThatThrownBy(() -> Utilities.toIntegers(input))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("bytes must have size divisible by 4 to be converted to integers got: 1");
  }

  @Test
  void toInteger_success() {
    var bytes = ByteLists.immutable.of((byte) 0b11111110, (byte) 0b11111101, (byte) 0b11111011, (byte) 0b11110111);

    Assertions.assertThat(Utilities.toIntegers(bytes)).isEqualTo(IntLists.immutable.of(0b11111110_11111101_11111011_11110111));
  }
}
