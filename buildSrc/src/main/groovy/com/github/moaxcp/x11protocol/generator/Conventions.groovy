package com.github.moaxcp.x11protocol.generator

import com.google.common.base.CaseFormat
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import pl.allegro.finance.tradukisto.ValueConverters

class Conventions {

    static final List<String> x11Primatives = [
        'BOOL',
        'BYTE',
        'INT8',
        'INT16',
        'INT32',
        'CARD8',
        'CARD16',
        'CARD32',
        'CARD64',
        'char',
        'float',
        'double',
        'void',
        'fd'
    ]

    static TypeName x11PrimativeToJavaTypeName(String x11Type) {
        if(x11Type == 'void') {
            return TypeName.BYTE
        }
        String primative = x11PrimativeToJavaPrimative(x11Type)
        switch(primative) {
            case 'boolean':
                return TypeName.BOOLEAN
            case 'byte':
                return TypeName.BYTE
            case 'short':
                return TypeName.SHORT
            case 'char':
                return TypeName.CHAR
            case 'int':
                return TypeName.INT
            case 'long':
                return TypeName.LONG
            case 'float':
                return TypeName.FLOAT
            case 'double':
                return TypeName.DOUBLE
        }
        throw new IllegalArgumentException("Could not convert $x11Type")
    }

    static String x11PrimativeToJavaPrimative(String x11Type) {
        switch(x11Type) {
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
            case 'char':
                return 'char'
            case 'float':
                return 'float'
            case 'double':
                return 'double'
            case 'fd':
                return 'int'
        }
        throw new IllegalArgumentException("Could not convert $x11Type")
    }

    static String convertX11VariableNameToJava(String x11Name) {
        String converted =  CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, x11Name)
        if(converted == 'class') {
            return 'clazz'
        }
        if(converted == 'private') {
            return 'xPrivate'
        }
        if(converted == 'default') {
            return 'defaultValue'
        }
        if(converted == 'new') {
            return 'newValue'
        }
        return converted
    }

    static String getStructJavaName(String x11Name) {
        return getJavaName(x11Name) + 'Struct'
    }

    static ClassName getStructTypeName(String javaPackage, String x11Name) {
        return ClassName.get(javaPackage, getStructJavaName(x11Name))
    }

    static String getUnionJavaName(String x11Name) {
        return getJavaName(x11Name) + 'Union'
    }

    static ClassName getUnionTypeName(String javaPackage, String x11Name) {
        return ClassName.get(javaPackage, getUnionJavaName(x11Name))
    }

    static String getEnumJavaName(String x11Name) {
        return getJavaName(x11Name) + 'Enum'
    }

    static ClassName getEnumTypeName(String javaPackage, String x11Name) {
        return ClassName.get(javaPackage, getEnumJavaName(x11Name))
    }

    static String getErrorJavaName(String x11Name) {
        return getJavaName(x11Name) + 'Error'
    }

    static String getEventJavaName(String x11Name) {
        return getJavaName(x11Name) + 'Event'
    }

    static ClassName getEventTypeName(String javaPackage, String x11Name) {
        return ClassName.get(javaPackage, getEventJavaName(x11Name))
    }

    static String getEventStructJavaName(String x11Name) {
        return getJavaName(x11Name) + 'EventStruct'
    }

    static String getRequestJavaName(String x11Name) {
        return getJavaName(x11Name) + 'Request'
    }

    static String getReplyJavaName(String x11Name) {
        return getJavaName(x11Name) + 'Reply'
    }

    static String getJavaName(String x11Name) {
        String startNumbers = x11Name.find('^\\d+')
        if(startNumbers) {
            String remainingString = x11Name.substring(startNumbers.length())
            String numberWords = ValueConverters.ENGLISH_INTEGER.asWords(startNumbers.toInteger())
                .replace('-', ' ')
                .split(' ').collect{ it.capitalize() }.join('')
            return numberWords + remainingString
        }
        if(x11Name == x11Name.toUpperCase()) {
            return x11Name.substring(0, 1) + x11Name.substring(1).toLowerCase()
        }

        return x11Name
    }

    static String getEnumValueName(String x11Name) {
        CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, getJavaName(x11Name))
    }

    static String fromUpperCamelToLowerCamel(String from) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, from)
    }

    static String fromUpperUnderscoreToUpperCamel(String from) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, from)
    }
}
