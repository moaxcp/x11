package com.github.moaxcp.x11.protocol;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class BigEndian {
    public static byte[] write(int value, byte[] bytes) {
        bytes[0] = (byte) (value >> 24);
        bytes[1] = (byte) (value >> 16);
        bytes[2] = (byte) (value >> 8);
        bytes[3] = (byte) value;
        return bytes;
    }

    public static List<Byte> writeList(int value) {
        return Arrays.asList((byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value);
    }
}
