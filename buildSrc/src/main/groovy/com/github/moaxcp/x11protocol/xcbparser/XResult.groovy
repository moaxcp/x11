package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.generator.Conventions
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import groovy.transform.EqualsAndHashCode
import groovy.transform.Memoized
import groovy.transform.ToString
import groovy.util.slurpersupport.Node
import javax.lang.model.element.Modifier
import lombok.Getter
import lombok.Setter

import static com.github.moaxcp.x11protocol.generator.Conventions.getJavaName

@ToString(includePackage = false, includes='header')
@EqualsAndHashCode
class XResult {
    File file
    String basePackage
    String header
    String extensionXName
    String extensionName
    Boolean extensionMultiword
    int majorVersion
    int minorVersion
    Map<String, XResult> imports = [:]
    Set<String> xidTypes = []
    Set<String> xidUnions = []
    Map<String, String> typedefs = [:]
    Map<String, XTypeStruct> structs = [:]
    Map<String, XTypeEnum> enums = [:]
    Map<String, XTypeUnion> unions = [:]
    Map<String, XTypeEvent> events = [:]
    Map<String, XTypeEventStruct> eventStructs = [:]
    Map<String, XTypeError> errors = [:]
    Map<String, XTypeRequest> requests = [:]

    Map<String, XTypePrimative> primatives = Conventions.x11Primatives.collectEntries {
            [(it):new XTypePrimative(result:this, name:it)]
        }

    String getJavaPackage() {
        return "$basePackage.$header"
    }

    String getPluginXObjectInterfaceName() {
        return getJavaName(header.capitalize()) + 'Object'
    }

    TypeSpec getPluginXObjectInterface() {
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(getPluginXObjectInterfaceName())
            .addSuperinterface(ClassName.get(basePackage, 'XObject'))
            .addMethod(MethodSpec.methodBuilder('getPluginName')
                    .returns(String.class)
                    .addModifiers(Modifier.DEFAULT, Modifier.PUBLIC)
                    .addStatement('return $T.NAME', getPluginClassName())
                    .build())
        return builder.build()
    }

    TypeSpec getXPlugin() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(getPluginClassName())
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(ClassName.get(basePackage, 'XProtocolPlugin'))

        builder.addField(
            FieldSpec.builder(String.class, 'NAME', Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer('"$L"', extensionXName)
                .build())

        builder.addMethod(MethodSpec.methodBuilder('getName')
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement('return NAME')
                .build())

        builder.addField(
            FieldSpec.builder(byte.class, 'majorVersion', Modifier.PRIVATE)
            .addAnnotation(Getter.class)
            .initializer('$L', majorVersion)
            .build())

        builder.addField(
                FieldSpec.builder(byte.class, 'minorVersion', Modifier.PRIVATE)
                        .addAnnotation(Getter.class)
                        .initializer('$L', minorVersion)
                        .build())

        builder.addField(
            FieldSpec.builder(byte.class, 'firstEvent', Modifier.PRIVATE)
                .addAnnotation(Getter.class)
                .addAnnotation(Setter.class)
                .build())

        builder.addField(
            FieldSpec.builder(byte.class, 'firstError', Modifier.PRIVATE)
                .addAnnotation(Getter.class)
                .addAnnotation(Setter.class)
                .build())

        MethodSpec.Builder supportedRequest = MethodSpec.methodBuilder('supportedRequest')
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(boolean.class)
            .addParameter(ClassName.get(basePackage, 'XRequest'), 'request')

        for(XTypeRequest request : requests.values()) {
            supportedRequest.beginControlFlow('if(request instanceof $T)', request.javaType.className)
            supportedRequest.addStatement('return true')
            supportedRequest.endControlFlow()
        }
        supportedRequest.addStatement('return false')

        builder.addMethod(supportedRequest.build())

        MethodSpec.Builder supportedEvent = MethodSpec.methodBuilder('supportedEvent')
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(boolean.class)
            .addParameter(byte.class, 'number')

        for(XTypeEvent event : events.values()) {
            if(event.number != 35) {
                supportedEvent.beginControlFlow('if(number - firstEvent == $L)', event.number)
                supportedEvent.addStatement('return true')
                supportedEvent.endControlFlow()
            }
        }
        supportedEvent.addStatement('return false')

        builder.addMethod(supportedEvent.build())

        MethodSpec.Builder supportedError = MethodSpec.methodBuilder('supportedError')
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(boolean.class)
            .addParameter(byte.class, 'code')

        for(XTypeError error : errors.values()) {
            supportedError.beginControlFlow('if(code - firstError == $L)', error.number)
            supportedError.addStatement('return true')
            supportedError.endControlFlow()
        }
        supportedError.addStatement('return false')

        builder.addMethod(supportedError.build())

        MethodSpec.Builder readEvent = MethodSpec.methodBuilder('readEvent')
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(ClassName.get(basePackage, 'XEvent'))
            .addParameter(byte.class, 'number')
            .addParameter(boolean.class, 'sentEvent')
            .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
            .addException(IOException.class)

        for(XTypeEvent event : events.values()) {
            if(event.number != 35) {
                readEvent.beginControlFlow('if(number - firstEvent == $L)', event.number)
                readEvent.addStatement('return $T.read$L(firstEvent, sentEvent, in)', event.javaType.className, event.javaType.className.simpleName())
                readEvent.endControlFlow()
            }
        }
        readEvent.addStatement('throw new $T("number " + number + " is not supported")', IllegalArgumentException.class)

        builder.addMethod(readEvent.build())

        MethodSpec.Builder readError = MethodSpec.methodBuilder('readError')
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(ClassName.get(basePackage, 'XError'))
            .addParameter(byte.class, 'code')
            .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
            .addException(IOException.class)

        for(XTypeError error : errors.values()) {
            readError.beginControlFlow('if(code - firstError == $L)', error.number)
            readError.addStatement('return $T.read$L(firstError, in)', error.javaType.className, error.javaType.className.simpleName())
            readError.endControlFlow()
        }
        readError.addStatement('throw new $T("code " + code + " is not supported")', IllegalArgumentException.class)

        builder.addMethod(readError.build())

        MethodSpec.Builder readGenericEvent = MethodSpec.methodBuilder('readGenericEvent')
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(ClassName.get(basePackage, 'XGenericEvent'))
                .addParameter(boolean.class, 'sentEvent')
            .addParameter(byte.class, 'extension')
            .addParameter(short.class, 'sequenceNumber')
            .addParameter(int.class, 'length')
            .addParameter(short.class, 'eventType')
            .addParameter(ClassName.get(basePackage, 'X11Input'), 'in')
            .addException(IOException.class)

        for(XTypeEvent event : events.values()) {
            if(event.number == 35) {
                readGenericEvent.beginControlFlow('if(eventType == $L)', event.genericEventNumber)
                readError.addStatement('return $T.read$L(sentEvent, extension, sequenceNumber, length, eventType, in)', event.javaType.className, event.javaType.className.simpleName())
                readGenericEvent.endControlFlow()
            }
        }
        readGenericEvent.addStatement('throw new $T("eventType " + eventType + " is not supported")', IllegalArgumentException.class)

        builder.addMethod(readGenericEvent.build())

        return builder.build()
    }

    ClassName getPluginClassName() {
        ClassName.get(javaPackage, getJavaName(header.capitalize() + 'Plugin'))
    }

    void addNode(Node node) {
        switch(node.name()) {
            case 'xidtype':
                addXidtype(node)
                break
            case 'xidunion':
                addXidunion(node)
                break
            case 'typedef':
                addTypeDef(node)
                break
            case 'enum':
                addEnum(node)
                break
            case 'struct':
                addStruct(node)
                break
            case 'event':
                addEvent(node)
                break
            case 'eventcopy':
                addEventCopy(node)
                break
            case 'eventstruct':
                addEventStruct(node)
                break
            case 'union':
                addUnion(node)
                break
            case 'error':
                addError(node)
                break
            case 'errorcopy':
                addErrorCopy(node)
                break
            case 'request':
                addRequest(node)
                break
            default:
                throw new IllegalArgumentException("could not parse ${node.name()}")
        }
    }

    void addXidtype(Node node) {
        xidTypes.add((String) node.attributes().get('name'))
    }

    void addXidunion(Node node) {
        xidUnions.add((String) node.attributes().get('name'))
    }

    void addTypeDef(Node node) {
        typedefs.put((String) node.attributes().get('newname'), (String) node.attributes().get('oldname'))
    }

    void addImport(XResult result) {
        imports.put(result.header, result)
    }

    void addEnum(Node node) {
        String name = node.attributes().get('name')
        checkName(name)
        enums.put(name, XTypeEnum.xTypeEnum(this, node))
    }

    void checkName(String name) {
        if(enums[name]) {
            throw new IllegalStateException("enum already exists with name $name")
        }
        if(structs[name]) {
            throw new IllegalStateException("struct already exists with name $name")
        }
        if(requests[name]) {
            throw new IllegalStateException("request already exists with name $name")
        }
    }

    void addStruct(Node node) {
        String name = node.attributes().get('name')
        checkName(name)
        structs.put(name, XTypeStruct.xTypeStruct(this, node))
    }

    void addEvent(Node node) {
        String name = node.attributes().get('name')
        events.put(name, XTypeEvent.xTypeEvent(this, node))
    }

    void addEventCopy(Node node) {
        String name = node.attributes().get('name')
        events.put(name, XTypeEvent.xTypeEventCopy(this, node))
    }

    void addEventStruct(Node node) {
        eventStructs.put((String) node.attributes().get('name'), XTypeEventStruct.xTypeEventStruct(this, node))
    }

    void addUnion(Node node) {
        String name = node.attributes().get('name')
        unions.put(name, XTypeUnion.xTypeUnion(this, node))
    }

    void addError(Node node) {
        String name = node.attributes().get('name')
        errors.put(name, XTypeError.xTypeError(this, node))
    }

    void addErrorCopy(Node node) {
        String name = node.attributes().get('name')
        errors.put(name, XTypeError.xTypeErrorCopy(this, node))
    }

    void addRequest(Node node) {
        String name = node.attributes().get('name')
        checkName(name)
        requests.put(name, XTypeRequest.xTypeRequest(this, node))
    }

    @Memoized
    <T extends XType> T resolveXType(String type) {
        XType resolved
        if(type.contains(':')) {
            String specificImport = type.substring(0, type.indexOf(':'))
            String actualType = type.substring(type.indexOf(':') + 1)
            if(header == specificImport) {
                resolved = resolveLocal(actualType)
            } else {
                resolved = imports.get(specificImport).resolveTypeRecursive(actualType)
            }
        }
        if(resolved) {
            return (T) resolved
        }

        resolved = resolveTypeRecursive(type)

        if(!resolved) {
            throw new IllegalArgumentException("could not resolve $type in $extensionName")
        }

        return resolved
    }

    @Memoized
    XTypeEnum resolveXTypeEnum(String type) {
        XTypeEnum resolved
        if(type.contains(':')) {
            String specificImport = type.substring(0, type.indexOf(':'))
            String actualType = type.substring(type.indexOf(':') + 1)
            if(header == specificImport) {
                resolved = resolveLocalEnum(actualType)
            } else {
                resolved = imports.get(specificImport).resolveEnumRecursive(actualType)
            }
        }

        if(resolved) {
            return resolved
        }

        resolved = resolveEnumRecursive(type)
        if(!resolved) {
            throw new IllegalArgumentException("could not resolve $type")
        }
        return resolved
    }

    XType resolveTypeRecursive(String type) {
        XType fromLocal = resolveLocal(type)

        if(fromLocal) {
            return fromLocal
        }

        XType fromImport = imports.values().collect {
            it.resolveTypeRecursive(type)
        }.find {
            it
        }

        if(fromImport) {
            return fromImport
        }

        return null
    }

    XTypeEnum resolveEnumRecursive(String type) {
        XTypeEnum fromLocal = resolveLocalEnum(type)
        if(fromLocal) {
            return fromLocal
        }

        XTypeEnum fromImport = imports.values().collect {
            it.resolveEnumRecursive(type)
        }.find {
            it
        }

        if(fromImport) {
            return fromImport
        }

        return null
    }

    XType resolveLocal(String type) {
        type = resolveTypeDef(type)
        if(xidTypes.contains(type) || xidUnions.contains(type)) {
            type = 'CARD32'
        }
        XType xType = primatives[type] ?: structs[type] ?: unions[type] ?: enums[type] ?: events[type] ?: eventStructs[type] ?: errors[type] ?: requests[type]

        if(xType) {
            return xType
        }

        return null
    }

    XTypeEnum resolveLocalEnum(String type) {
        type = resolveTypeDef(type)
        XTypeEnum xType = enums[type]
        if(xType) {
            return xType
        }
        return null
    }

    /**
     * Resolves a typedef recursively. If no typedef is found then the original type is returned.
     * @param type
     * @return
     */
    String resolveTypeDef(String type) {
        return resolveTypeDefRecursive(type) ?: type
    }

    /**
     * Resolved a typedef recursively. A null return is used to control when the typedef is found.
     * @param type
     * @return
     */
    String resolveTypeDefRecursive(String type) {
        String resultType = typedefs.find { it.key == type }?.value
        if(!resultType) {
            return null
        }
        String nextType = resolveTypeDefRecursive(resultType)
        if(!nextType) {
            return resultType
        }
        return nextType
    }
}
