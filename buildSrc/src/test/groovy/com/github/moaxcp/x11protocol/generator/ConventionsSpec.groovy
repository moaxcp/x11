package com.github.moaxcp.x11protocol.generator


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
        'int' == Conventions.x11PrimativeToJavaPrimative('CARD32')
    }

    void 'convert x11 variable name to java variable name'() {
        expect:
        'overrideRedirect' == Conventions.convertX11VariableNameToJava('override_redirect')
    }
}