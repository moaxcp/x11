package com.github.moaxcp.x11.protocol;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class LittleEndian {
    public static short toShort(byte[] bytes) {
        int value = (bytes[1] & 0xFF);
        value = (value << 8) + (bytes[0] & 0xFF);
        return (short) value;
    }

    public static void write(short value, byte[] bytes) {
        bytes[0] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[1] = (byte) (value & 0xFF);
    }

    public static int toInt(byte[] bytes) {
        int value = (bytes[3] & 0xFF);
        value = (value << 8) + (bytes[2] & 0xFF);
        value = (value << 8) + (bytes[1] & 0xFF);
        value = (value << 8) + (bytes[0] & 0xFF);
        return value;
    }

    public static byte[] write(int value, byte[] bytes) {
        bytes[0] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[1] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[2] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[3] = (byte) (value & 0xFF);
        return bytes;
    }

    public static List<Byte> writeList(int value) {
        var first = (byte) (value & 0xFF);
        value >>= 8;
        var second = (byte) (value & 0xFF);
        value >>= 8;
        var third = (byte) (value & 0xFF);
        value >>= 8;
        var fourth = (byte) (value & 0xFF);
        return List.of(first, second, third, fourth);
    }

    public static Long toLong(byte[] bytes) {
        long value = (bytes[7] & 0xFF);
        value = (value << 8) + (bytes[6] & 0xFF);
        value = (value << 8) + (bytes[5] & 0xFF);
        value = (value << 8) + (bytes[4] & 0xFF);
        value = (value << 8) + (bytes[3] & 0xFF);
        value = (value << 8) + (bytes[2] & 0xFF);
        value = (value << 8) + (bytes[1] & 0xFF);
        value = (value << 8) + (bytes[0] & 0xFF);
        return value;
    }

    public static void write(long value, byte[] bytes) {
        bytes[0] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[1] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[2] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[3] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[4] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[5] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[6] = (byte) (value & 0xFF);
        value >>= 8;
        bytes[7] = (byte) (value & 0xFF);
    }

    public static float toFloat(byte[] bytes) {
        int value = toInt(bytes);
        return Float.intBitsToFloat(value);
    }

    public static void write(float value, byte[] bytes) {
        int intValue = Float.floatToRawIntBits(value);
        write(intValue, bytes);
    }

    public static double toDouble(byte[] bytes) {
        long value = toLong(bytes);
        return Double.longBitsToDouble(value);
    }

    public static void write(double value, byte[] bytes) {
        long longValue = Double.doubleToLongBits(value);
        write(longValue, bytes);
    }
}
