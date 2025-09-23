package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class LittleEndianSerializerTest {
  private final LittleEndianSerializer ser = new LittleEndianSerializer();

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
    ser.writeInt16(bytes, 0, (short) -2);           // 0xFE FF in LE
    ser.writeUint16(bytes, 2, 0xABCD);              // 0xCD 0xAB in LE
    assertThat(bytes).isEqualTo(new byte[] {(byte) 0xFE, (byte) 0xFF, (byte) 0xCD, (byte) 0xAB});
    assertThat(ser.readInt16(bytes, 0)).isEqualTo((short) -2);
    assertThat(ser.readUint16(bytes, 2)).isEqualTo(0xABCD);
  }

  @Test
  void int32_and_uint32_roundtrip() {
    byte[] bytes = new byte[8];
    ser.writeInt32(bytes, 0, 0x8000_0001);          // LE: 01 00 00 80
    ser.writeUint32(bytes, 4, 0xFFFF_FFFFL);        // LE: FF FF FF FF
    assertThat(bytes).isEqualTo(new byte[] {1, 0, 0, (byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
    assertThat(ser.readInt32(bytes, 0)).isEqualTo(0x8000_0001);
    assertThat(ser.readUint32(bytes, 4)).isEqualTo(0xFFFF_FFFFL);
  }

  @Test
  void int64_roundtrip() {
    byte[] bytes = new byte[8];
    long v = 0x0123_4567_89AB_CDEFL;
    ser.writeInt64(bytes, 0, v);
    assertThat(bytes).isEqualTo(new byte[] {(byte) 0xEF, (byte) 0xCD, (byte) 0xAB, (byte) 0x89, 0x67, 0x45, 0x23, 0x01});
    assertThat(ser.readInt64(bytes, 0)).isEqualTo(v);
  }

  @Test
  void uint64_roundtrip_and_padding() {
    byte[] bytes = new byte[8];
    BigInteger val = new BigInteger("FFFFFFFFFFFFFFFF", 16); // max uint64
    ser.writeUint64(bytes, 0, val);
    assertThat(bytes).isEqualTo(new byte[] {(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF});
    assertThat(ser.readUint64(bytes, 0)).isEqualTo(val);

    // shorter magnitude should be right-padded with zeros in little-endian
    BigInteger val2 = new BigInteger("0102030405060708", 16);
    ser.writeUint64(bytes, 0, val2);
    assertThat(bytes).isEqualTo(new byte[] {0x08,0x07,0x06,0x05,0x04,0x03,0x02,0x01});
    assertThat(ser.readUint64(bytes, 0)).isEqualTo(val2);
  }

  @Test
  void float_and_double_roundtrip() {
    byte[] bytes = new byte[12];
    ser.writeFloat(bytes, 0, 1.0f); // 0x3F800000 -> LE: 00 00 80 3F
    ser.writeDouble(bytes, 4, 1.0d); // 0x3FF0000000000000 -> LE reversed
    assertThat(bytes).isEqualTo(new byte[] {
        0x00, 0x00, (byte)0x80, 0x3F,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xF0, 0x3F
    });
    assertThat(ser.readFloat(bytes, 0)).isEqualTo(1.0f);
    assertThat(ser.readDouble(bytes, 4)).isEqualTo(1.0d);
  }
}
