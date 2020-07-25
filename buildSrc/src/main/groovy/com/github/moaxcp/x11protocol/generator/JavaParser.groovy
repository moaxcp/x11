package com.github.moaxcp.x11protocol.generator


import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
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
        parseStructsWithUnions(x11Result.structs, x11Result.unions)
        parseEnums(x11Result.enums)
        parseErrors(x11Result.errors)
        parseErrorCopies(x11Result.errorCopies)
        parseEvents(x11Result.events)
        parseEventCopies(x11Result.eventCopies)
        parseRequests(x11Result.requests)
        return result
    }

    void parseStructsWithUnions(Map<String, Node> structs, Map<String, Node> unions) {
        structs.each { entry ->
            String className = getClassName(entry.key)
            result.javaTypes.put(entry.key, parseType(className, entry.value))
        }
        unions.each { entry ->
            String className = getClassName(entry.key)
            result.javaTypes.put(entry.key, parseType(className, entry.value))
        }
    }

    void parseEnums(Map<String, Node> enums) {
        //todo
    }

    void parseErrors(Map<String, Node> errors) {
        errors.each { entry ->
            String className = getClassName(entry.key)
            result.javaTypes.put(entry.key, parseType(className, entry.value))
        }
    }

    void parseErrorCopies(Map<String, Node> errorCopies) {
        //todo
    }

    void parseEvents(Map<String, Node> events) {
        events.each { entry ->
            String className = getClassName(entry.key)
            result.javaTypes.put(entry.key, parseType(className, entry.value))
        }
    }

    void parseEventCopies(Map<String, Node> errorCopies) {
        //todo
    }

    void parseRequests(Map<String, Node> requests) {
        requests.each { entry ->
            String className = getClassName(entry.key)
            result.javaTypes.put(entry.key, parseType(className, entry.value))
            Node reply = (Node) entry.value.childNodes().find {Node it -> it.name() == 'reply'}
            if(reply) {
                result.javaTypes.put(entry.key + 'Reply', parseType(className + 'Reply', entry.value))
            }
        }
    }

    TypeSpec parseType(String className, Node xml) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
        List<FieldSpec> fields = xml.children()
            .collect { Node it ->
                getField(it)
            }.findAll { it }
        builder.addFields(fields)
        .addModifiers(Modifier.PUBLIC)
        return builder.build()
    }
    
    FieldSpec getField(Node node) {
        switch(node.name()) {
            case 'field':
                return getNormalField(node)
            case 'exprfield':
                return getNormalField(node)
            case 'list':
                Tuple2<TypeName, String> field = getTypeAndVariableName(node)
                ParameterizedTypeName paramType = ParameterizedTypeName.get(ClassName.get(List.class), field.getFirst())
                return FieldSpec.builder(paramType, field.getSecond()).
                    addModifiers(Modifier.PRIVATE).build()
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
        Tuple2<String, String> tuple = x11Result.resolveType(type)
        if(tuple.first() == 'primative') {
            return ClassName.get('', getClassName(tuple.getSecond()))
        }
        return ClassName.get(basePackage + '.' + tuple.getFirst(), getClassName(tuple.getSecond()))
    }

    static String getClassName(String x11Name) {
        switch(x11Name) {
            case 'BOOL':
                return 'boolean'
            case 'BYTE':
                return 'byte'
            case 'INT8':
                return 'byte'
            case 'INT16':
                return 'short'
            case 'INT32':
                return 'int'
            case 'CARD8':
                return 'byte'
            case 'CARD16':
                return 'short'
            case 'CARD32':
                return 'int'
            case 'CARD64':
                return 'long'
        }

        String javaType
        if(x11Name == x11Name.toUpperCase()) {
            javaType = x11Name.substring(0, 1) + x11Name.substring(1).toLowerCase()
        } else if(x11Name == 'class') {
            javaType = 'clazz'
        } else {
            javaType = x11Name
        }

        return javaType
    }
}
