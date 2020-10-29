package com.github.moaxcp.x11protocol.generator

import com.squareup.javapoet.ClassName
import spock.lang.Specification

class ConventionsSpec extends Specification {
    def 'parse class name'() {
        expect:
        Conventions.getJavaName(x11Name) == javaName

        where:
        x11Name          || javaName
        '1'              || 'One'
        '8Bits'          || 'EightBits'
        'POINT'          || 'Point'
        'CommonBehavior' || 'CommonBehavior'
    }
    def 'parse enum name'() {
        expect:
        Conventions.getEnumValueName(x11Name) == javaName

        where:
        x11Name          || javaName
        '1'              || 'ONE'
        '8Bits'          || 'EIGHT_BITS'
        '32Bits'         || 'THIRTY_TWO_BITS'
    }

    void 'hard-coded convention with CARD32'() {
        expect:
        'int' == Conventions.x11PrimativeToStoragePrimative('CARD32')
    }

    void 'convert x11 variable name to java variable name'() {
        expect:
        'overrideRedirect' == Conventions.convertX11VariableNameToJava('override_redirect')
    }

    void 'convert x11 primative type to boxed type'() {
        expect:
        Conventions.x11PrimativeToBoxedType(x11Name) == className

        where:
        x11Name  || className
        'void'   || ClassName.get(Byte.class)
        'BOOL'   || ClassName.get(Boolean.class)
        'BYTE'   || ClassName.get(Byte.class)
        'INT8'   || ClassName.get(Byte.class)
        'INT16'  || ClassName.get(Short.class)
        'INT32'  || ClassName.get(Integer.class)
        'CARD8'  || ClassName.get(Byte.class)
        'CARD16' || ClassName.get(Short.class)
        'CARD32' || ClassName.get(Integer.class)
        'CARD64' || ClassName.get(Long.class)
        'char'   || ClassName.get(Byte.class)
        'float'  || ClassName.get(Float.class)
        'double' || ClassName.get(Double.class)
        'fd'     || ClassName.get(Integer.class)
    }
}