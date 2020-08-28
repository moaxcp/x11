package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec

class XTypeEventSpec extends XmlSpec {
    def create() {
        given:
        xmlBuilder.xcb() {
            event(name:'CompleteNotify', number:'1', xge:'true') {
                required_start_align(align:'8')
                field(type:'CARD8', name:'kind', 'enum':'CompleteKind')

                field(type:'CARD32', name:'serial')
            }
        }

        when:
        result.addEvent(getFirstChild())
        XTypeEvent event = result.resolveXType('CompleteNotify')

        then:
        event.name == 'CompleteNotify'
        event.number == 1
        event.protocol[0].align == 8
        event.protocol[1].name == 'kind'
        event.protocol[2].name == 'serial'

    }
}