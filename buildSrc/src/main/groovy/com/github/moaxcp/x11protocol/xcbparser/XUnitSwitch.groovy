package com.github.moaxcp.x11protocol.xcbparser

import groovy.util.slurpersupport.Node

import static com.github.moaxcp.x11protocol.xcbparser.XUnitSwitchBitcase.parseValueList
import static com.github.moaxcp.x11protocol.xcbparser.XUnitSwitchCase.parseCases

abstract class XUnitSwitch implements XUnit {

    XCaseInfo getCaseInfo() {
        throw new UnsupportedOperationException("nested cases not supported")
    }

    static XUnitSwitch parseXUnitSwitch(XResult result, Node node) {

        if(node.childNodes().find { Node it -> it.name() == 'bitcase'}) {
            return parseValueList(result, node)
        } else if(node.childNodes().find { Node it -> it.name() == 'case'}) {
            return parseCases(result, node)
        }
        throw new IllegalArgumentException("unknown switch " + node.toString())
    }
}
