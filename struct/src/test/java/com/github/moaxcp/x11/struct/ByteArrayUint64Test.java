package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteArrayUint64Test {

  @Test
  void uint64_overwrite() {
    var bytes = new ByteArray(new byte[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
    for (int i = 0; i < 10; i++) {
      bytes.setUint64(i * 8, new BigInteger("18446744073709551615").subtract(BigInteger.valueOf(i)));
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {
        -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -2,
        -1, -1, -1, -1, -1, -1, -1, -3,
        -1, -1, -1, -1, -1, -1, -1, -4,
        -1, -1, -1, -1, -1, -1, -1, -5,
        -1, -1, -1, -1, -1, -1, -1, -6,
        -1, -1, -1, -1, -1, -1, -1, -7,
        -1, -1, -1, -1, -1, -1, -1, -8,
        -1, -1, -1, -1, -1, -1, -1, -9,
        -1, -1, -1, -1, -1, -1, -1, -10
    });
    for (int i = 0; i < 10; i++) {
      assertThat(bytes.getUint64(i * 8)).isEqualTo(new BigInteger("18446744073709551615").subtract(BigInteger.valueOf(i)));
    }
  }

  @Test
  void addInt32() {
    var bytes = new ByteArray(new byte[] {100});
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.addUint64(0, BigInteger.valueOf(i));
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {
        0, 0, 0, 0, 0, 0, 0, 9,
        0, 0, 0, 0, 0, 0, 0, 8,
        0, 0, 0, 0, 0, 0, 0, 7,
        0, 0, 0, 0, 0, 0, 0, 6,
        0, 0, 0, 0, 0, 0, 0, 5,
        0, 0, 0, 0, 0, 0, 0, 4,
        0, 0, 0, 0, 0, 0, 0, 3,
        0, 0, 0, 0, 0, 0, 0, 2,
        0, 0, 0, 0, 0, 0, 0, 1,
        0, 0, 0, 0, 0, 0, 0, 0,
        100
    });
    assertThat(events).containsExactly(shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8), shiftBytes(0, 8));
  }

  @Test
  void removeInt32Events() {
    var bytes = new ByteArray(new byte[] {
        0, 0, 0, 0, 0, 0, 0, 9,
        0, 0, 0, 0, 0, 0, 0, 8,
        0, 0, 0, 0, 0, 0, 0, 7,
        0, 0, 0, 0, 0, 0, 0, 6,
        0, 0, 0, 0, 0, 0, 0, 5,
        0, 0, 0, 0, 0, 0, 0, 4,
        0, 0, 0, 0, 0, 0, 0, 3,
        0, 0, 0, 0, 0, 0, 0, 2,
        0, 0, 0, 0, 0, 0, 0, 1,
        0, 0, 0, 0, 0, 0, 0, 0,
        100
    });
    var events = new ArrayList<ShiftBytes>();
    bytes.addListener(events::add);
    for (int i = 0; i < 10; i++) {
      bytes.removeUint64(0);
    }
    assertThat(bytes.getBytes()).isEqualTo(new byte[] {100});
    assertThat(events).containsExactly(shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8), shiftBytes(0, -8));
  }
}
