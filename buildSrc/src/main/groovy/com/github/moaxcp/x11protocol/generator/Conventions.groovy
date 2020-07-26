package com.github.moaxcp.x11protocol.generator

import com.google.common.base.CaseFormat
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.TypeName
import pl.allegro.finance.tradukisto.ValueConverters

class Conventions {

    static TypeName x11PrimativeToJavaTypeName(String x11Type) {
        if(x11Type == 'void') {
            return ArrayTypeName.of(TypeName.BYTE)
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

    static String getClassName(String x11Name) {
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

    static String getEnumName(String x11Name) {
        CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, getClassName(x11Name))
    }
}
