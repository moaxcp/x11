package com.github.moaxcp.x11protocol.generator


import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import groovy.util.slurpersupport.Node

import javax.lang.model.element.Modifier 

class JavaParser {
    private String basePackage
    private X11Result x11Result
    private JavaResult result

    static JavaResult parse(String basePackage, X11Result x11Result) {
        new JavaParser(basePackage:basePackage, x11Result:x11Result).parse()
    }

    JavaResult parse() {
        result = new JavaResult()
        result.packageName = basePackage + '.' + x11Result.header
        parseStructs()
        parseUnions()
        parseEnums()
        parseErrors()
        parseErrorCopies()
        parseEvents()
        parseEventCopies()
        parseEventStructs()
        parseRequests()
        return result
    }

    void parseStructs() {
        x11Result.structs.each { entry ->
            String className = Conventions.getStructJavaName(entry.key)
            result.structs.put(entry.key, parseType(className, entry.value))
        }
    }

    void parseUnions() {
        x11Result.unions.each { entry ->
            String className = Conventions.getUnionJavaName(entry.key)
            result.unions.put(entry.key, parseType(className, entry.value))
        }
    }

    void parseEnums() {
        x11Result.enums.each { entry ->
            String enumName = Conventions.getEnumJavaName(entry.key)
            result.enums.put(entry.key, parseEnum(enumName, entry.value))
        }
    }

    void parseErrors() {
        x11Result.errors.each { entry ->
            String className = Conventions.getErrorJavaName(entry.key)
            TypeSpec.Builder builder = parseType(className, entry.value)
            builder.addField(FieldSpec.builder(TypeName.INT, 'CODE')
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer('$L', entry.value.attributes().get('number'))
                .build())
            result.errors.put(entry.key, builder)
        }
    }

    void parseErrorCopies() {
        x11Result.errorCopies.each { entry ->
            String className = Conventions.getErrorJavaName(entry.key)
            String errorName = entry.value.attributes().get('ref')
            Node errorNode = x11Result.findError(errorName)
            TypeSpec.Builder builder = TypeSpec.classBuilder(className)
            builder.addField(FieldSpec.builder(TypeName.INT, 'CODE')
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer('$L', entry.value.attributes().get('number'))
                .build())
            List<FieldSpec> fields = errorNode.childNodes().collect { Node it ->
                getField(it)
            }.findAll { it }
            builder.addFields(fields)
                .addModifiers(Modifier.PUBLIC)

            result.errors.put(entry.key, builder)
        }
    }

    void parseEvents() {
        x11Result.events.each { entry ->
            String className = Conventions.getEventJavaName(entry.key)
            TypeSpec.Builder builder = parseType(className, entry.value)
            builder.addField(FieldSpec.builder(TypeName.INT, 'CODE')
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer('$L', entry.value.attributes().get('number'))
                .build())
            result.events.put(entry.key, builder)
        }
    }

    void parseEventCopies() {
        x11Result.eventCopies.each { entry ->
            String className = Conventions.getEventJavaName(entry.key)
            String eventName = entry.value.attributes().get('ref')
            Node eventNode = x11Result.findEvent(eventName)
            TypeSpec.Builder builder = TypeSpec.classBuilder(className)
            builder.addField(FieldSpec.builder(TypeName.INT, 'CODE')
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer('$L', entry.value.attributes().get('number'))
                .build())
            List<FieldSpec> fields = eventNode.childNodes().collect { Node it ->
                getField(it)
            }.findAll { it }
            builder.addFields(fields)
                .addModifiers(Modifier.PUBLIC)

            result.events.put(entry.key, builder)
        }
    }

    void parseEventStructs() {
        x11Result.eventStructs.each { entry ->
            String className= Conventions.getEventStructJavaName(entry.key)
            TypeSpec.Builder type = TypeSpec.interfaceBuilder(className)
            result.eventStructs.put(entry.key, type)
        }
    }

    void parseRequests() {
        x11Result.requests.each { entry ->
            String className = Conventions.getRequestJavaName(entry.key)
            TypeSpec.Builder builder = parseType(className, entry.value)
            builder.addField(FieldSpec.builder(TypeName.INT, 'OPCODE')
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer('$L', entry.value.attributes().get('opcode'))
                .build())
            result.requests.put(entry.key, builder)
            Node reply = (Node) entry.value.childNodes().find {Node it -> it.name() == 'reply'}
            if(reply) {
                result.replies.put(entry.key, parseType(Conventions.getReplyJavaName(entry.key), entry.value))
            }
        }
    }

    TypeSpec.Builder parseEnum(String enumName, Node node) {
        if(isBitMaskEnum(node)) {
            return parseValueMaskEnum(enumName, node)
        }
        TypeSpec.Builder builder = TypeSpec.enumBuilder(enumName)
        builder.addSuperinterface(ClassName.get(basePackage, 'IntValue'))
        builder.addField(FieldSpec.builder(TypeName.INT, 'value', Modifier.PRIVATE).build())
        builder.addMethod(MethodSpec.constructorBuilder()
            .addParameter(TypeName.INT, 'value')
            .addStatement("this.\$N = \$N", 'value', 'value')
            .build())
        builder.addMethod(MethodSpec.methodBuilder('getValue')
            .addAnnotation(Override)
            .addModifiers(Modifier.PUBLIC)
            .addStatement("return value")
            .returns(TypeName.INT)
            .build())

        node.childNodes().each { Node it ->
            switch(it.name()) {
                case 'item':
                    String itemName = Conventions.getEnumValueName((String) it.attributes().get('name'))
                    builder.addEnumConstant(itemName, TypeSpec.anonymousClassBuilder("\$L", getEnumItemValue(it)).build())
                    break
                case 'doc':
                    return
                    break
                default:
                    throw new IllegalArgumentException("cannot parse node ${it.name()}")
            }
        }
        builder.addModifiers(Modifier.PUBLIC)
        return builder
    }

    TypeSpec.Builder parseValueMaskEnum(String enumName, Node node) {
        TypeSpec.Builder builder = TypeSpec.enumBuilder(enumName)
        builder.addSuperinterface(ClassName.get(basePackage, 'ValueMask'))
        builder.addField(FieldSpec.builder(TypeName.INT, 'mask', Modifier.PRIVATE).build())
        builder.addMethod(MethodSpec.constructorBuilder()
            .addParameter(TypeName.INT, "mask")
            .addStatement("this.\$N = \$N", "mask", "mask")
            .build())
        builder.addMethod(MethodSpec.methodBuilder('getMask')
            .addAnnotation(Override)
            .addModifiers(Modifier.PUBLIC)
            .addStatement("return mask")
            .returns(TypeName.INT)
            .build())

        node.childNodes().each { Node it ->
            switch(it.name()) {
                case 'item':
                    String itemName = Conventions.getEnumValueName((String) it.attributes().get('name'))
                        builder.addEnumConstant(itemName, TypeSpec.anonymousClassBuilder("\$L", getEnumItemValue(it)).build())
                    break
                case 'doc':
                    return
                    break
                default:
                    throw new IllegalArgumentException("cannot parse node ${it.name()}")
            }
        }
        builder.addModifiers(Modifier.PUBLIC)
        return builder

    }

    boolean isBitMaskEnum(Node node) {
        return node.childNodes().find {Node child ->
            child.childNodes().find { it.name() == 'bit' }
        }
    }

    String getEnumItemValue(Node node) {
        def first = node.children().get(0)
        if(first instanceof String) {
            return first
        }
        Node firstNode = (Node) first
        int value
        switch(firstNode.name()) {
            case 'value':
                return node.text()
            case 'bit':
                value = ((Node) node.children().get(0)).text().toInteger()
                return getMask(value)
            default:
                throw new IllegalArgumentException("could not find value for item $node")
        }
    }

    String getMask(int bit) {
        if(bit > 32) {
            throw new IllegalArgumentException("bit $bit not supported")
        }
        StringBuilder builder = new StringBuilder('0b1')
        for(int i = 0; i < bit; i++) {
            builder.append('0')
        }
        return builder.toString()
    }

    TypeSpec.Builder parseType(String className, Node xml) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
        List<FieldSpec> fields = xml.childNodes().collect { Node it ->
            getField(it)
        }.findAll { it }
        builder.addFields(fields)
        .addModifiers(Modifier.PUBLIC)
        return builder
    }
    
    FieldSpec getField(Node node) {
        switch(node.name()) {
            case 'field':
                return getNormalField(node)
            case 'exprfield':
                return getNormalField(node)
            case 'list':
                return getListField(node)
            case 'fd':
                return null
            case 'switch':
                return null
            case 'doc':
                return null
            case 'pad':
                return null
            case 'reply':
                return null
            case 'required_start_align':
                return null
            default:
                throw new IllegalArgumentException("cannot parse node ${node.name()}")
        }
    }

    private FieldSpec getListField(Node node) {
        Tuple2<TypeName, String> field = getTypeAndVariableName(node)
        if(field.getFirst().isPrimitive()) {
            return null
        }
        ParameterizedTypeName paramType = ParameterizedTypeName.get(ClassName.get(List.class), field.getFirst())
        return FieldSpec.builder(paramType, field.getSecond()).
            addModifiers(Modifier.PRIVATE).build()
    }

    FieldSpec getNormalField(Node node) {
        Tuple2<TypeName, String> field = getTypeAndVariableName(node)
        return FieldSpec.builder(field.getFirst(), field.getSecond())
            .addModifiers(Modifier.PRIVATE).build()
    }

    Tuple2<TypeName, String> getTypeAndVariableName(Node node) {
        TypeName typeName = getFieldTypeName(node)
        String variableName = Conventions.convertX11VariableNameToJava((String) node.attributes().get('name'))
        return new Tuple2<>(typeName, variableName)
    }

    TypeName getFieldTypeName(Node node) {
        getFieldTypeName((String) node.attributes().get('type'))
    }

    TypeName getFieldTypeName(String type) {
        Tuple3<String, String, String> tuple = x11Result.resolveType(type)
        if(tuple.getSecond() == 'primative') {
            return Conventions.x11PrimativeToJavaTypeName(tuple.getThird())
        }
        return ClassName.get(basePackage + '.' + tuple.getFirst(), Conventions."get${tuple.second.capitalize()}JavaName"(tuple.getThird()))
    }
}
