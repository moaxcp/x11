package com.github.moaxcp.x11.protocol;

import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11.protocol.LittleEndian.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LittleEndianTest {
    @Test
    void shortConversion() {
        var expected = (short) 0b10000000_00000001;
        var buffer = new byte[8];
        write(expected, buffer);
        var result = toShort(buffer);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void intConversion() {
        var expected = 0b10000001_10000001_10000000_00000001;
        var buffer = new byte[8];
        write(expected, buffer);
        var result = toInt(buffer);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void longConversion() {
        var expected = 0b11100111_11100111_11000000_00000011_10000001_10000001_10000000_00000001L;
        var buffer = new byte[8];
        write(expected, buffer);
        var result = toLong(buffer);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void floatConversion() {
        var expected = Float.intBitsToFloat(0b10000001_10000001_10000000_00000001);
        var buffer = new byte[8];
        write(expected, buffer);
        var result = toFloat(buffer);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void doubleConversion() {
        var expected = Double.longBitsToDouble(0b11100111_11100111_11000000_00000011_10000001_10000001_10000000_00000001L);
        var buffer = new byte[8];
        write(expected, buffer);
        var result = toDouble(buffer);
        assertThat(result).isEqualTo(expected);
    }
}
