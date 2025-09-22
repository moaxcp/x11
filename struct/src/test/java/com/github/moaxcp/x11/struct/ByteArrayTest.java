package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayTest {
  @Test
  void constructor() {
    var bytes = new ByteArray();
    assertThat(bytes.getBytes()).hasSize(0);
  }

  @Test
  void constructorSize() {
    var bytes = new ByteArray(10);
    assertThat(bytes.getBytes()).hasSize(10);
  }

  @Test
  void constructorBytes() {
    var bytes = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
  }

  @Test
  void copy() {
    var bytes = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    var copy = bytes.copy();
    assertThat(copy.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(copy).isNotSameAs(bytes);
  }

  @Test
  void int8() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.int8(i, (byte) -i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, -1, -2, -3, -4, -5, -6, -7, -8, -9});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.int8(i)).isEqualTo((byte) -i);
    }
  }

  @Test
  void addInt8() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.addInt8(0, (byte) i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0});
  }

  @Test
  void addInt8Events() {
    var bytes = new ByteArray();
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addInt8(0, (byte) i);
      byte[] newBytes = new byte[bytes.getBytes().length];
      for (int j = 0; j <= i; j++) {
        newBytes[j] = (byte) (newBytes.length - 1 - j);
      }
      assertThat(bytes.getBytes()).isEqualTo(newBytes);
    }
    assertThat(events).containsExactly(shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1));
  }

  @Test
  void removeInt8Events() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.addInt8(0, (byte) i);
    }
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeInt8(0);
      byte[] newBytes = new byte[bytes.getBytes().length];
      for (int j = 0; j < newBytes.length; j++) {
        newBytes[j] = (byte) (newBytes.length - 1 - j);
      }
      assertThat(bytes.getBytes()).isEqualTo(newBytes);
    }
    assertThat(events).containsExactly(shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1));
  }

  @Test
  void uint8() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.uint8(i, (short) (i + 128));
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {-128, -127, -126, -125, -124, -123, -122, -121, -120, -119});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.uint8(i)).isEqualTo((short) (i + 128));
    }
  }

  @Test
  void addUint8() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.addUint8(0, (byte) (i + 128));
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {-119, -120, -121, -122, -123, -124, -125, -126, -127, -128});
  }

  @Test
  void addUint8Events() {
    var bytes = new ByteArray();
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addUint8(0, (byte) i);
      byte[] newBytes = new byte[bytes.getBytes().length];
      for (int j = 0; j <= i; j++) {
        newBytes[j] = (byte) (newBytes.length - 1 - j);
      }
      assertThat(bytes.getBytes()).isEqualTo(newBytes);
    }
    assertThat(events).containsExactly(shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1), shiftBytes(0, 1));
  }

  @Test
  void removeUint8Events() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.addUint8(0, (byte) i);
    }
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeUint8(0);
      byte[] newBytes = new byte[bytes.getBytes().length];
      for (int j = 0; j < newBytes.length; j++) {
        newBytes[j] = (byte) (newBytes.length - 1 - j);
      }
      assertThat(bytes.getBytes()).isEqualTo(newBytes);
    }
    assertThat(events).containsExactly(shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1), shiftBytes(0, -1));
  }

  @Test
  void int16() {
    var bytes = new ByteArray(0);
    for (int i = 0; i < 10; i++) {
      bytes.int16(i * 2, (short) -i);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, -1, -1, -1, -2, -1, -3, -1, -4, -1, -5, -1, -6, -1, -7, -1, -8, -1, -9});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.int16(i * 2)).isEqualTo((short) -i);
    }
  }

  @Test
  void addInt16() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.addInt16(0, (short) i);
      byte[] newBytes = new byte[bytes.getBytes().length];
      for (int j = 0; j <= i; j++) {
        newBytes[j * 2 + 1] = (byte) (i - j);
      }
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 9, 0, 8, 0, 7, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2, 0, 1, 0, 0});
  }

  @Test
  void addInt16Events() {
    var bytes = new ByteArray();
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addInt16(0, (short) i);
    }
    assertThat(events).containsExactly(shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2));
  }

  @Test
  void removeInt16Events() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.addInt16(0, (short) i);
    }
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeInt16(0);
      byte[] newBytes = new byte[bytes.getBytes().length];
      for (int j = 0; j < 10 - 2 - i; j++) {
        newBytes[j * 2 + 1] = (byte) (10 - 2 - i - j);
      }
      assertThat(bytes.getBytes()).isEqualTo(newBytes);
    }
    assertThat(events).containsExactly(shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2));
  }

  @Test
  void uint16() {
    var bytes = new ByteArray(0);
    for (int i = 0; i < 10; i++) {
      bytes.uint16(i * 2, i + 32_768);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {-128, 0, -128, 1, -128, 2, -128, 3, -128, 4, -128, 5, -128, 6, -128, 7, -128, 8, -128, 9});
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.uint16(i * 2)).isEqualTo(i + 32_768);
    }
  }

  @Test
  void addUint16() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.addUint16(0, (short) i);
      byte[] newBytes = new byte[bytes.getBytes().length];
      for (int j = 0; j <= i; j++) {
        newBytes[j * 2 + 1] = (byte) (i - j);
      }
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 9, 0, 8, 0, 7, 0, 6, 0, 5, 0, 4, 0, 3, 0, 2, 0, 1, 0, 0});
  }

  @Test
  void addUint16Events() {
    var bytes = new ByteArray();
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addUint16(0, (short) i);
    }
    assertThat(events).containsExactly(shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2), shiftBytes(0, 2));
  }

  @Test
  void removeUint16Events() {
    var bytes = new ByteArray();
    for (int i = 0; i < 10; i++) {
      bytes.addUint16(0, i);
    }
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeUint16(0);
      byte[] newBytes = new byte[bytes.getBytes().length];
      for (int j = 0; j < 10 - 2 - i; j++) {
        newBytes[j * 2 + 1] = (byte) (10 - 2 - i - j);
      }
      assertThat(bytes.getBytes()).isEqualTo(newBytes);
    }
    assertThat(events).containsExactly(shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2), shiftBytes(0, -2));
  }

  @Test
  void setBytes() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(10);
    for (int i = 0; i < 10; i++) {
      source.int8(i, (byte) i);
    }
    bytes.setBytes(source, 0, 0, 10);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
  }

  @Test
  void setBytesSource0Offset0() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(2);
    source.int8(0, (byte) 1);
    source.int8(1, (byte) 1);
    bytes.setBytes(source, 0, 0, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {1, 1, 0, 0, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void setBytesSource1ffset4() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.int8(1, (byte) 1);
    source.int8(2, (byte) 1);
    bytes.setBytes(source, 1, 4, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0});
  }

  @Test
  void setBytesSource1offset8() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.int8(1, (byte) 1);
    source.int8(2, (byte) 1);
    bytes.setBytes(source, 1, 8, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 1});
  }

  @Test
  void setBytesSource1offset15Resize() {
    var bytes = new ByteArray(10);
    var source = new ByteArray(4);
    source.int8(1, (byte) 1);
    source.int8(2, (byte) 1);
    bytes.setBytes(source, 1, 15, 2);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1});
  }

  @Test
  void setBytesOversized() {
    var bytes = new ByteArray(3);
    var source = new ByteArray(10);
    for (int i = 0; i < 10; i++) {
      source.int8(i, (byte) i);
    }
    bytes.setBytes(source, 0, 0, 10);
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
  }

  @Test
  void compareBytes() {
    var bytes1 = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    var bytes2 = new ByteArray(new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
    assertThat(bytes1.compareBytes(0, bytes2, 0, 10)).isTrue();
  }
}
