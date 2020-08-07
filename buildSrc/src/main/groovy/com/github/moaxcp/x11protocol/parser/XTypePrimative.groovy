package com.github.moaxcp.x11protocol.parser


import static com.github.moaxcp.x11protocol.parser.JavaPrimativeProperty.javaPrimativeProperty

class XTypePrimative extends XTypeResolved {
    @Override
    JavaType getJavaType() {
        throw new UnsupportedOperationException("primatives")
    }

    @Override
    JavaPrimativeProperty getJavaProperty(XUnitField field) {
        return javaPrimativeProperty(field)
    }

    @Override
    JavaListProperty getJavaListProperty(XUnitListField field) {
        return javaPrimativeListProperty(field)
    }
}
