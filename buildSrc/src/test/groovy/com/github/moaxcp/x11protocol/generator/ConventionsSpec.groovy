package com.github.moaxcp.x11protocol.generator


import spock.lang.Specification

class ConventionsSpec extends Specification {
    Conventions conventions = new Conventions()

    void 'hard-coded convention with CARD32'() {
        expect:
        'int' == conventions.getX11ToJavaType('CARD32')
    }

    void 'all-caps x11Type is converted with RECTANGLE'() {
        expect:
        'Rectangle' == conventions.getX11ToJavaType('RECTANGLE')
    }

    void 'clazz x11Type is converted from class'() {
        expect:
        'clazz' == conventions.getX11ToJavaType('class')
    }

    void 'ConfigureNotify x11Type is not converted'() {
        expect:
        'ConfigureNotify' == conventions.getX11ToJavaType('ConfigureNotify')
    }

    void 'convert x11 variable name to java variable name'() {
        expect:
        'overrideRedirect' == conventions.convertX11VariableNameToJava('override_redirect')
    }
}