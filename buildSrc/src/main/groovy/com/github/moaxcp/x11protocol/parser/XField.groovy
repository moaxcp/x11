package com.github.moaxcp.x11protocol.parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class XField extends ResolvableXUnit {
    /**
     * Indicates this field is from a switch and should set the mask when set
     */
    String mask

    /**
     * This field is an enum. type is how it should be read or written
     */
    String enumType
    boolean lengthField

    @Override
    String getReadCode() {
        return "$javaName = in.readCard32();"
    }

    @Override
    String getWriteCode() {
        return "out.writeCard32($javaName);"
    }
}
