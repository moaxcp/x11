package com.github.moaxcp.x11protocol.generator

import com.squareup.javapoet.ClassName
import spock.lang.Specification

class JavaParserSpec extends Specification {
    def 'parse class name'() {
        expect:
        JavaParser.getClassName(x11Name) == javaName

        where:
        x11Name          || javaName
        'BOOL'           || 'boolean'
        'BYTE'           || 'byte'
        'INT8'           || 'byte'
        'INT16'          || 'short'
        'INT32'          || 'int'
        'CARD8'          || 'byte'
        'CARD16'         || 'short'
        'CARD32'         || 'int'
        'CARD64'         || 'long'
        'POINT'          || 'Point'
        'class'          || 'clazz'
        'CommonBehavior' || 'CommonBehavior'
    }

    def 'parse field type name glx'() {
        given:
        X11Result result = X11Parser.parse(new File('../src/main/xcbXmls/glx.xml'))
        JavaParser parser = new JavaParser(basePackage: 'com.github.moaxcp.x11protocol', x11Result: result)

        expect:
        parser.getFieldTypeName(x11Name) == javaName

        where:
        x11Name  || javaName
        'BOOL'   || ClassName.get('', 'boolean')
        'BYTE'   || ClassName.get('', 'byte')
        'INT8'   || ClassName.get('',  'byte')
        'INT16'  || ClassName.get('',  'short')
        'INT32'  || ClassName.get('',  'int')
        'CARD8'  || ClassName.get('',  'byte')
        'CARD16' || ClassName.get('',  'short')
        'CARD32' || ClassName.get('',  'int')
        'char'   || ClassName.get('', 'char')
        'POINT'  || ClassName.get('com.github.moaxcp.x11protocol.xproto', 'Point')
        'CreateContext' || ClassName.get('com.github.moaxcp.x11protocol.glx', 'CreateContext')
        'WINDOW' || ClassName.get('',  'int')
        'glx:CONTEXT' || ClassName.get('',  'int')
        'xproto:WINDOW' || ClassName.get('',  'int')
    }

    def 'parse field type name xkb'() {
        given:
        X11Result result = X11Parser.parse(new File('../src/main/xcbXmls/xkb.xml'))
        JavaParser parser = new JavaParser(basePackage: 'com.github.moaxcp.x11protocol', x11Result: result)

        expect:
        parser.getFieldTypeName(x11Name) == javaName

        where:
        x11Name  || javaName
        'PermamentLockBehavior'  || ClassName.get('com.github.moaxcp.x11protocol.xkb', 'DefaultBehavior')
    }
}
