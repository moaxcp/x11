package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import groovy.transform.ToString
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName
import static com.github.moaxcp.x11protocol.xcbparser.JavaStruct.javaStruct

@ToString(includeSuperProperties = true, includePackage = false, includes = ['name'])
class XTypeStruct extends XTypeObject {

    XTypeStruct(Map map) {
        super(map)
    }

    static XTypeStruct xTypeStruct(XResult result, Node node) {
        XTypeStruct struct = new XTypeStruct(result:result, name: node.attributes().get('name'), basePackage: result.basePackage, javaPackage: result.javaPackage)
        struct.addUnits(result, node)

        return struct
    }

    @Override
    List<ClassName> getCaseClassNames() {
        return getCaseNames().collect {
            getStructTypeName(javaPackage, name + it.capitalize())
        }
    }

    @Override
    List<JavaType> getJavaType() {
        return javaStruct(this)
    }
}
