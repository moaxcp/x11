package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.slurpersupport.Node

@ToString(includePackage = false)
@EqualsAndHashCode
class XPad implements XUnit {
    int bytes

    static XPad getXPad(Node node) {
        int padBytes = Integer.valueOf((String) node.attributes().get('bytes'))
        return new XPad(bytes:padBytes)
    }

    @Override
    CodeBlock getReadCode() {
        return CodeBlock.of("in.readPad($bytes)")
    }

    @Override
    CodeBlock getWriteCode() {
        return CodeBlock.of("out.writePad($bytes)")
    }
}
