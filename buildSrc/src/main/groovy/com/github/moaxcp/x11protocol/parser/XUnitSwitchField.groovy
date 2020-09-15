package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.Node

class XUnitSwitchField extends XUnitField {
    /**
     * Indicates this field is from a switch and should set the mask on maskField using the enumType
     */
    String maskField

    /**
     * Enum to use when setting the mask of maskField.
     */
    String enumType

    XUnitSwitchField(XResult result, Node node) {
        super(result, node)
    }
}
