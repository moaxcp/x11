package com.github.moaxcp.x11protocol

import com.squareup.javapoet.*
import groovy.util.slurpersupport.GPathResult
import com.google.common.base.CaseFormat

class ProtocolGenerator {
    InputStream inputStream
    File outputSrc
    String basePackage
    String fullPackage

    Map<String, String> types = [
        'CARD8': 'int',
        'CARD16': 'int',
        'CARD32': 'int',
        'INT8': 'int',
        'INT16': 'int',
        'INT32': 'int'
    ]
    Map<String, String> classNames = [
        'CHARINFO': 'CharInfo',
        'FONTPROP': 'FontProp',
        'COLORITEM': 'ColorItem',
        'TIMECOORD': 'TimeCoord',
        'VISUALTYPE': 'VisualType'
    ]

    void generate() {
        GPathResult root = new XmlSlurper().parse(inputStream)
        fullPackage = basePackage + ".${root.@header}"
        loadTypes(root)
        createStructClasses(root)
    }

    void loadTypes(GPathResult root) {
        root.children().findAll { it.name() == 'xidtype' }
        .each {
            types.put((String) it.@name, 'int')
        }

        root.children().findAll { it.name() == 'typedef' }
        .each {
            types.put((String) it.@newname, types.get(it.@oldname))
        }
    }

    void createStructClasses(GPathResult root) {
        root.children()
            .findAll { it.name() == 'struct' }
            .each {
                createStructClass(it)
            }
    }

    void createStructClass(GPathResult struct) {
        String name = struct.@name
        String className = makeClassName(name)
        def type = TypeSpec.classBuilder(className)

        struct.children().findAll { it.name() == 'field' }
            .each {
                addMember(type, it)
            }

        def javaFile = JavaFile.builder(fullPackage, type.build())
                .build()

        javaFile.writeTo(outputSrc)
    }

    private String makeClassName(String name) {
        StringBuilder className = new StringBuilder()
        if (classNames.containsKey(name)) {
            className.append(classNames.get(name))
        } else if (name.toUpperCase() == name) {
            className.append(name.charAt(0))
            className.append(name.substring(1).toLowerCase())
        } else {
            className.append(name)
        }
        className.toString()
    }

    void addMember(TypeSpec.Builder type, GPathResult field) {
        String name = field.@name
        if(name == 'class' && field.@enum) {
            name = field.@enum
        }
        name = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name)

        String fieldType = field.@type
        if(types.containsKey(fieldType)) {
            fieldType = types.get(fieldType)
        } else {
            fieldType = makeClassName(fieldType)
        }

        TypeName fieldSpecType = getTypeName(fieldType)

        FieldSpec member = FieldSpec.builder(fieldSpecType, name).build();
        type.addField(member)
    }

    private TypeName getTypeName(String fieldType) {
        if(fieldType == 'int') {
            return TypeName.INT
        } else {
            return ClassName.get(fullPackage, fieldType)
        }
    }
}
