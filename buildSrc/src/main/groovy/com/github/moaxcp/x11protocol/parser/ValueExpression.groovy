package com.github.moaxcp.x11protocol.parser

import groovy.transform.PackageScope

class ValueExpression implements Expression {
    private String value

    @PackageScope void setValue(String value) {
        this.value = value
    }
    String getExpression() {
        return value
    }
}
