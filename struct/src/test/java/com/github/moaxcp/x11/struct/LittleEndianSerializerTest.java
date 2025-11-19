package com.github.moaxcp.x11.struct;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static com.github.moaxcp.x11.struct.LittleEndianSerializer.littleEndianSerializer;
import static org.assertj.core.api.Assertions.assertThat;

public class LittleEndianSerializerTest {
  private final LittleEndianSerializer ser = littleEndianSerializer();

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
  void int8_array_read() {
    byte[] bytes = new byte[] {10, 20, 30, 40, 50};
    byte[] result = ser.readInt8(bytes, 1, 3);
    assertThat(result).containsExactly((byte)20, (byte)30, (byte)40);
  }

  @Test
  void int8_uint8_array_write() {
    byte[] bytes = new byte[5];
    ser.writeInt8(bytes, 0, new byte[] {1, 2});
    ser.writeUint8(bytes, 2, new short[] {255, 0, 127});
    assertThat(bytes).isEqualTo(new byte[] {1, 2, (byte)0xFF, 0, 127});
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
  void int16_uint16_array_write() {
    byte[] bytes = new byte[8];
    ser.writeInt16(bytes, 0, new short[] {0x1122, (short)0xFFEE});
    ser.writeUint16(bytes, 4, new int[] {0xABCD, 0x0102});
    assertThat(bytes).isEqualTo(new byte[] {0x22, 0x11, (byte)0xEE, (byte)0xFF, (byte)0xCD, (byte)0xAB, 0x02, 0x01});
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
  void int32_uint32_array_write() {
    byte[] bytes = new byte[16];
    ser.writeInt32(bytes, 0, new int[] {0x01020304, 0xA0B0C0D0});
    ser.writeUint32(bytes, 8, new long[] {0x00000000L, 0xFFFFFFFFL});
    assertThat(bytes).isEqualTo(new byte[] {
        0x04,0x03,0x02,0x01,
        (byte)0xD0,(byte)0xC0,(byte)0xB0,(byte)0xA0,
        0x00,0x00,0x00,0x00,
        (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF
    });
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
  void int64_array_write() {
    byte[] bytes = new byte[16];
    ser.writeInt64(bytes, 0, new long[] {0x0102030405060708L, 0x1122334455667788L});
    assertThat(bytes).isEqualTo(new byte[] {
        (byte)0x08,0x07,0x06,0x05,0x04,0x03,0x02,0x01,
        (byte)0x88,0x77,0x66,0x55,0x44,0x33,0x22,0x11
    });
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
  void uint64_array_write() {
    byte[] bytes = new byte[16];
    ser.writeUint64(bytes, 0, new BigInteger[] {
        new BigInteger("01",16),
        new BigInteger("FFFFFFFFFFFFFFFF",16)
    });
    assertThat(bytes).isEqualTo(new byte[] {
        0x01,0,0,0,0,0,0,0,
        (byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF
    });
  }

  @Test
  void float_and_double_roundtrip() {
    byte[] bytes = new byte[12];
    ser.writeFloat32(bytes, 0, 1.0f); // 0x3F800000 -> LE: 00 00 80 3F
    ser.writeFloat64(bytes, 4, 1.0d); // 0x3FF0000000000000 -> LE reversed
    assertThat(bytes).isEqualTo(new byte[] {
        0x00, 0x00, (byte)0x80, 0x3F,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xF0, 0x3F
    });
    assertThat(ser.readFloat32(bytes, 0)).isEqualTo(1.0f);
    assertThat(ser.readFloat64(bytes, 4)).isEqualTo(1.0d);
  }

  @Test
  void float_double_array_write() {
    byte[] bytes = new byte[4*2 + 8*2];
    ser.writeFloat32(bytes, 0, new float[] {1.0f, -2.5f});
    ser.writeFloat64(bytes, 8, new double[] {0.0, -1.0});
    assertThat(ser.readFloat32(bytes, 0)).isEqualTo(1.0f);
    assertThat(ser.readFloat32(bytes, 4)).isEqualTo(-2.5f);
    assertThat(ser.readFloat64(bytes, 8)).isEqualTo(0.0);
    assertThat(ser.readFloat64(bytes, 16)).isEqualTo(-1.0);
  }

  @Test
  void boolean_roundtrip() {
    byte[] bytes = new byte[2];
    ser.writeBool(bytes, 0, true);
    ser.writeBool(bytes, 1, false);
    assertThat(bytes).isEqualTo(new byte[] {1, 0});
    assertThat(ser.readBool(bytes, 0)).isTrue();
    assertThat(ser.readBool(bytes, 1)).isFalse();
  }

  @Test
  void boolean_array_read() {
    byte[] bytes = new byte[] {0, 1, 2, 0, (byte) 0xFF};
    boolean[] result = ser.readBool(bytes, 0, bytes.length);
    assertThat(result).containsExactly(false, true, true, false, true);
  }

  @Test
  void boolean_array_write() {
    byte[] bytes = new byte[5];
    ser.writeBool(bytes, 0, new boolean[] {false, true, true, false, true});
    assertThat(bytes).isEqualTo(new byte[] {0,1,1,0,1});
  }
}
