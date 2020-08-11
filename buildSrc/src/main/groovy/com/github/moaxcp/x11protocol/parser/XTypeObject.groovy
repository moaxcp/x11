package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.ClassName

import static com.github.moaxcp.x11protocol.parser.JavaTypeListProperty.javaTypeListProperty
import static com.github.moaxcp.x11protocol.parser.JavaTypeProperty.javaTypeProperty

abstract class XTypeObject extends XTypeResolved implements XTypeUnit {
    ClassName superType
    List<XUnit> protocol = []

    List<JavaUnit> toJavaProtocol() {
        List<JavaUnit> java = protocol.collect {
            it.getJavaUnit()
        }

        java.eachWithIndex { JavaUnit entry, int i ->
            if(entry instanceof JavaPadAlign) {
                entry.list = java[i - 1]
            }
        }

        return java
    }

    @Override
    JavaTypeProperty getJavaProperty(XUnitField field) {
        return javaTypeProperty(field)
    }

    @Override
    JavaListProperty getJavaListProperty(XUnitListField field) {
        return javaTypeListProperty(field)
    }
}
