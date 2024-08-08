package com.github.moaxcp.x11.protocol;

import lombok.experimental.UtilityClass;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.impl.factory.primitive.ByteLists;

@UtilityClass
public class BigEndian {
    public static byte[] write(int value, byte[] bytes) {
        bytes[0] = (byte) (value >> 24);
        bytes[1] = (byte) (value >> 16);
        bytes[2] = (byte) (value >> 8);
        bytes[3] = (byte) value;
        return bytes;
    }

    public static ByteList writeList(int value) {
        return ByteLists.immutable.of((byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value);
    }
}
