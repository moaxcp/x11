package com.github.moaxcp.x11protocol.xkb

import com.github.moaxcp.x11protocol.XmlSpec
import spock.lang.Ignore

@Ignore
class GetKbdByNameReplySpec extends XmlSpec {
  def getKbdByNameReply() {
    given:
    xmlBuilder.xcb() {
      request(name: 'GetKbdByName', opcode: '23') {
        field(name: 'deviceSpec', type: 'DeviceSpec')
        field(name: 'need', type: 'CARD16', mask: 'GBNDetail')
        field(name: 'want', type: 'CARD16', mask: 'GBNDetail')
        field(name: 'load', type: 'BOOL')
        pad(byte: '1')
        reply {
          field(name: 'deviceID', type: 'CARD8')
          field(name: 'minKeyCode', type: 'KEYCODE')
          field(name: 'maxKeyCode', type: 'KEYCODE')
          field(name: 'loaded', type: 'BOOL')
          field(name: 'newKeyboard', type: 'BOOL')
          field(name: 'found', type: 'CARD16', mask: 'GBNDetail')
          field(name: 'reported', type: 'CARD16', mask: 'GBNDetail')
          pad(byte: '16')
          'switch'(name: 'replies') {
            fieldref('reported')
            bitcase(name: 'types') {
              enumref(ref: 'GBNDetail', 'Types')
              enumref(ref: 'GBNDetail', 'ClientSymbols')
              enumref(ref: 'GBNDetail', 'ClientSymbols')

            }
          }
        }
      }
    }
  }
}
