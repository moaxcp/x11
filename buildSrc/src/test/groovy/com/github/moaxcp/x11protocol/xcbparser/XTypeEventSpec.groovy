package com.github.moaxcp.x11protocol.xcbparser

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
        event.number == 35
        event.protocol[5].align == 8
        event.protocol[6].name == 'kind'
        event.protocol[7].name == 'serial'

    }
}
