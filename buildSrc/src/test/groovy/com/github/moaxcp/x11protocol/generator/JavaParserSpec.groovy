package com.github.moaxcp.x11protocol.generator

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import spock.lang.Specification

class JavaParserSpec extends Specification {

    def 'parse field type name glx'() {
        given:
        X11Result result = X11Parser.parse(new File('../src/main/xcbXmls/glx.xml'))
        JavaParser parser = new JavaParser(basePackage: 'com.github.moaxcp.x11protocol', x11Result: result)

        expect:
        parser.getFieldTypeName(x11Name) == javaName

        where:
        x11Name         || javaName
        'BOOL'          || TypeName.BOOLEAN
        'BYTE'          || TypeName.BYTE
        'INT8'          || TypeName.BYTE
        'INT16'         || TypeName.SHORT
        'INT32'         || TypeName.INT
        'CARD8'         || TypeName.BYTE
        'CARD16'        || TypeName.SHORT
        'CARD32'        || TypeName.INT
        'char'          || TypeName.CHAR
        'POINT'         || ClassName.get('com.github.moaxcp.x11protocol.xproto', 'PointStruct')
        'CreateContext' || ClassName.get('com.github.moaxcp.x11protocol.glx', 'CreateContextRequest')
        'WINDOW'        || TypeName.INT
        'glx:CONTEXT'   || TypeName.INT
        'xproto:WINDOW' || TypeName.INT
    }

    def 'parse field type name xkb'() {
        given:
        X11Result result = X11Parser.parse(new File('../src/main/xcbXmls/xkb.xml'))
        JavaParser parser = new JavaParser(basePackage: 'com.github.moaxcp.x11protocol', x11Result: result)

        expect:
        parser.getFieldTypeName(x11Name) == javaName

        where:
        x11Name  || javaName
        'PermamentLockBehavior'  || ClassName.get('com.github.moaxcp.x11protocol.xkb', 'DefaultBehaviorStruct')
    }

    def 'parse field type name xinput'() {
        given:
        X11Result result = X11Parser.parse(new File('../src/main/xcbXmls/xinput.xml'))
        JavaParser parser = new JavaParser(basePackage: 'com.github.moaxcp.x11protocol', x11Result: result)

        expect:
        parser.getFieldTypeName(x11Name) == javaName

        where:
        x11Name  || javaName
        'EventForSend'  || ClassName.get('com.github.moaxcp.x11protocol.xinput', 'EventForSendEventStruct')
    }
}
