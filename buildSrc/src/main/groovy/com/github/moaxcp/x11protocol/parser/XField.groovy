package com.github.moaxcp.x11protocol.parser

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(excludes = 'xResult')
@EqualsAndHashCode
class XField implements XUnit {
    XResult xResult
    String type
    String name
    String mask
    String enumType
    boolean lengthField

    XType getResolvedType() {
        xResult.resolveXType(type)
    }

    @Override
    String getReadCode() {
        return null
    }

    @Override
    String getWriteCode() {
        return null
    }
}
