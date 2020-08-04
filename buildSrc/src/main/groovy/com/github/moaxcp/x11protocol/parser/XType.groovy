package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.TypeName
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.generator.Conventions.getEnumTypeName
import static com.github.moaxcp.x11protocol.generator.Conventions.getStructTypeName
import static com.github.moaxcp.x11protocol.generator.Conventions.x11PrimativeToJavaTypeName

@ToString(excludes = 'result')
@EqualsAndHashCode(excludes = 'result')
class XType {
    XResult result
    String type //(primative, struct, union, request)
    String name //(CARD32, POINT, CreateWindow)

    static XType xidType(XResult result, Node node) {
        String name = node.attributes().get('name')
        return new XType(result:result, type:'xid', name:name)
    }

    static XType xidUnionType(XResult result, Node node) {
        String name = node.attributes().get('name')
        return new XType(result:result, type:'xidunion', name:name)
    }

    TypeName getJavaType() {
        switch(type) {
            case 'primative':
                return x11PrimativeToJavaTypeName(name)
            case 'xid':
            case 'xidunion':
                return x11PrimativeToJavaTypeName('CARD32')
            case 'struct':
                return getStructTypeName(result.javaPackage, name)
            case 'enum':
                return getEnumTypeName(result.javaPackage, name)
        }
        throw new IllegalStateException("Could not create type for $type")
    }

    String getGroup() {
        result.header
    }
}
