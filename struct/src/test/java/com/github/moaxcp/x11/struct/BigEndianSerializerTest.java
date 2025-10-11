package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class BigEndianSerializerTest {
  private final BigEndianSerializer ser = new BigEndianSerializer();

  @Test
  void int8_and_uint8_roundtrip() {
    byte[] bytes = new byte[2];
    ser.writeInt8(bytes, 0, (byte) -1);
    ser.writeUint8(bytes, 1, (short) 255);
    assertThat(bytes).isEqualTo(new byte[] {(byte) 0xFF, (byte) 0xFF});
    assertThat(ser.readInt8(bytes, 0)).isEqualTo((byte) -1);
    assertThat(ser.readUint8(bytes, 1)).isEqualTo((short) 255);
  }

  @Test
  void int16_and_uint16_roundtrip() {
    byte[] bytes = new byte[4];
    ser.writeInt16(bytes, 0, (short) -2);           // 0xFFFE
    ser.writeUint16(bytes, 2, 0xABCD);              // 0xAB 0xCD
    assertThat(bytes).isEqualTo(new byte[] {(byte) 0xFF, (byte) 0xFE, (byte) 0xAB, (byte) 0xCD});
    assertThat(ser.readInt16(bytes, 0)).isEqualTo((short) -2);
    assertThat(ser.readUint16(bytes, 2)).isEqualTo(0xABCD);
  }

  @Test
  void int32_and_uint32_roundtrip() {
    byte[] bytes = new byte[8];
    ser.writeInt32(bytes, 0, 0x8000_0001);          // sign bit + 1
    ser.writeUint32(bytes, 4, 0xFFFF_FFFFL);
    assertThat(bytes).isEqualTo(new byte[] {(byte) 0x80, 0, 0, 1, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
    assertThat(ser.readInt32(bytes, 0)).isEqualTo(0x8000_0001);
    assertThat(ser.readUint32(bytes, 4)).isEqualTo(0xFFFF_FFFFL);
  }

  @Test
  void int64_roundtrip() {
    byte[] bytes = new byte[8];
    long v = 0x0123_4567_89AB_CDEFL;
    ser.writeInt64(bytes, 0, v);
    assertThat(bytes).isEqualTo(new byte[] {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF});
    assertThat(ser.readInt64(bytes, 0)).isEqualTo(v);
  }

  @Test
  void uint64_roundtrip_and_padding() {
    byte[] bytes = new byte[8];
    BigInteger val = new BigInteger("FFFFFFFFFFFFFFFF", 16); // max uint64
    ser.writeUint64(bytes, 0, val);
    assertThat(bytes).isEqualTo(new byte[] {(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF});
    assertThat(ser.readUint64(bytes, 0)).isEqualTo(val);

    // shorter magnitude should be left-padded with zeros in big-endian
    BigInteger val2 = new BigInteger("0102030405060708", 16);
    ser.writeUint64(bytes, 0, val2);
    assertThat(bytes).isEqualTo(new byte[] {0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08});
    assertThat(ser.readUint64(bytes, 0)).isEqualTo(val2);
  }

  @Test
  void float_and_double_roundtrip() {
    byte[] bytes = new byte[12];
    ser.writeFloat(bytes, 0, 1.0f); // 0x3F800000
    ser.writeDouble(bytes, 4, 1.0d); // 0x3FF0000000000000
    assertThat(bytes).isEqualTo(new byte[] {
        0x3F, (byte)0x80, 0x00, 0x00,
        0x3F, (byte)0xF0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    });
    assertThat(ser.readFloat(bytes, 0)).isEqualTo(1.0f);
    assertThat(ser.readDouble(bytes, 4)).isEqualTo(1.0d);
  }

  @Test
  void boolean_roundtrip() {
    byte[] bytes = new byte[2];
    ser.writeBoolean(bytes, 0, true);
    ser.writeBoolean(bytes, 1, false);
    assertThat(bytes).isEqualTo(new byte[] {1, 0});
    assertThat(ser.readBoolean(bytes, 0)).isTrue();
    assertThat(ser.readBoolean(bytes, 1)).isFalse();
  }
}
