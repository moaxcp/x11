package com.github.moaxcp.x11protocol

import com.google.common.base.CaseFormat

class Conventions {
    /**
     * hard-coded conversions of x11 types to java types
     */
    private final Map<String, String> x11ToJavaType = [
        'CARD8': 'byte',
        'CARD16': 'short',
        'CARD32': 'int',
        'INT8': 'byte',
        'INT16': 'short',
        'INT32': 'int',
        'BOOL': 'boolean',
        'BYTE': 'byte',

        'CHARINFO': 'CharInfo',
        'FONTPROP': 'FontProp',
        'COLORITEM': 'ColorItem',
        'TIMECOORD': 'TimeCoord',
        'VISUALTYPE': 'VisualType'
    ]

    void putAllTypes(Map<String, String> types) {
        x11ToJavaType.putAll(types)
    }

    String getX11ToJavaType(String x11Type) {
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
        x11ToJavaType.put(x11Type, javaType)

        return javaType
    }

    String convertX11VariableNameToJava(String x11Name) {
        String converted =  CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, x11Name)
        if(converted == 'class') {
            return 'clazz'
        }
        return converted
    }
}
