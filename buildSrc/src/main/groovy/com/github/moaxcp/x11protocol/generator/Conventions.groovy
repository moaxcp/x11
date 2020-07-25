package com.github.moaxcp.x11protocol.generator

import com.google.common.base.CaseFormat

class Conventions {
    /**
     * hard-coded conversions of x11 types to java types
     */
    private static final Map<String, String> x11ToJavaType = [
        'CARD8': 'byte',
        'CARD16': 'short',
        'CARD32': 'int',
        'INT8': 'byte',
        'INT16': 'short',
        'INT32': 'int',
        'BOOL': 'boolean',
        'BYTE': 'byte',
        'INT64': 'long',
        'sync:INT64': 'long',

        'STR': 'String',

        'CHARINFO': 'CharInfo',
        'COLORITEM': 'ColorItem',
        'FONTPROP': 'FontProp',
        'TIMECOORD': 'TimeCoord',
        'VISUALTYPE': 'VisualType',
        'WAITCONDITION': 'WaitCondition'
    ]

    private static final List<String> typeFilter = ['STR', 'INT64']

    /**
     * types to filter when parsing. These types will not be converted to a new class.
     * @param name
     * @return
     */
    static boolean filterType(String name) {
        typeFilter.contains(name)
    }

    static String getX11ToJavaType(String x11Type) {
        if(x11ToJavaType.containsKey(x11Type)) {
            return x11ToJavaType.get(x11Type)
        }
        String javaType
        if(x11Type == x11Type.toUpperCase()) {
            javaType = x11Type.substring(0, 1) + x11Type.substring(1).toLowerCase()
        } else if(x11Type == 'class') {
            javaType = 'clazz'
        } else {
            javaType = x11Type
        }

        return javaType
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
}
