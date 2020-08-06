package com.github.moaxcp.x11protocol.parser

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec

class XUnitSwitchField extends XUnitField {
    /**
     * Indicates this field is from a switch and should set the mask on maskField using the enumType
     */
    String maskField

    /**
     * Enum to use when setting the mask of maskField.
     */
    String enumType

    @Override
    FieldSpec getMember() {
        return null
    }

    @Override
    boolean isReadOnly() {
        return false
    }

    @Override
    CodeBlock getReadCode() {
        return null
    }

    @Override
    CodeBlock getWriteCode() {
        return null
    }
}
