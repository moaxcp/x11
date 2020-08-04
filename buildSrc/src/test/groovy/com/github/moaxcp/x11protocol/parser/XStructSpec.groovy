package com.github.moaxcp.x11protocol.parser

import groovy.util.slurpersupport.GPathResult
import groovy.xml.MarkupBuilder
import spock.lang.Specification
import groovy.util.slurpersupport.Node

class XStructSpec extends Specification {
    StringWriter writer = new StringWriter()
    MarkupBuilder xmlBuilder = new MarkupBuilder(writer)
    XResult result = new XResult(basePackage: 'com.github.moaxcp.x11client.protocol', header:'xproto')

    GPathResult getGPathResult() {
        new XmlSlurper().parseText(writer.toString())
    }

    Node getFirstChild() {
        (Node) getGPathResult().childNodes().next()
    }

    def 'struct from node with normal fields'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            struct(name:'POINT') {
                field(type:'INT16', name:'x')
                field(type:'INT16', name:'y')
            }
        }

        when:
        result.addStruct(getFirstChild())

        then:
        result.structs.POINT.name == 'POINT'
        result.structs.POINT.type == 'struct'
        result.structs.POINT.group == 'xproto'
        result.structs.POINT.javaClassName == 'PointStruct'
        result.structs.POINT.protocol[0].name == 'x'
        result.structs.POINT.protocol[0].type == 'INT16'
        result.structs.POINT.protocol[1].name == 'y'
        result.structs.POINT.protocol[1].type == 'INT16'
    }

    def 'struct from node with normal fields and pad'() {
        given:
        xmlBuilder.xcb(header:'xproto') {
            struct(name:'FORMAT') {
                field(type:'CARD8', name:'depth')
                field(type:'CARD8', name:'bits_per_pixel')
                field(type:'CARD8', name:'scanline_pad')
                pad(bytes:5)
            }
        }

        when:
        result.addStruct(getFirstChild())

        then:
        result.structs.FORMAT.name == 'FORMAT'
        result.structs.FORMAT.type == 'struct'
        result.structs.FORMAT.group == 'xproto'
        result.structs.FORMAT.javaClassName == 'FormatStruct'
        result.structs.FORMAT.protocol[0].name == 'depth'
        result.structs.FORMAT.protocol[0].type == 'CARD8'
        result.structs.FORMAT.protocol[1].name == 'bits_per_pixel'
        result.structs.FORMAT.protocol[1].type == 'CARD8'
        result.structs.FORMAT.protocol[2].name == 'scanline_pad'
        result.structs.FORMAT.protocol[2].type == 'CARD8'

        result.structs.FORMAT.protocol[3].bytes == 5
    }

    def 'readMethod struct from node with normal fields and pad'() {
        given:
        xmlBuilder.xcb() {
            struct(name:'FORMAT') {
                field(type:'CARD8', name:'depth')
                field(type:'CARD8', name:'bits_per_pixel')
                field(type:'CARD8', name:'scanline_pad')
                pad(bytes:5)
            }
        }

        when:
        result.addStruct(getFirstChild())

        then:
        result.structs.FORMAT.readMethod.toString() == '''\
            public static com.github.moaxcp.x11client.protocol.xproto.FormatStruct readFormatStruct() {
              byte depth = in.readCard8();
              byte bitsPerPixel = in.readCard8();
              byte scanlinePad = in.readCard8();
              in.readPad(5);
              com.github.moaxcp.x11client.protocol.xproto.FormatStruct struct = new com.github.moaxcp.x11client.protocol.xproto.FormatStruct();
              struct.setDepth(depth);
              struct.setBitsPerPixel(bitsPerPixel);
              struct.setScanlinePad(scanlinePad);
              return struct;
            }
            '''.stripIndent()
    }

    def 'writeMethod struct from node with normal fields and pad'() {
        given:
        xmlBuilder.xcb() {
            struct(name:'FORMAT') {
                field(type:'CARD8', name:'depth')
                field(type:'CARD8', name:'bits_per_pixel')
                field(type:'CARD8', name:'scanline_pad')
                pad(bytes:5)
            }
        }

        when:
        result.addStruct(getFirstChild())

        then:
        result.structs.FORMAT.writeMethod.toString() == '''\
            public void writeFormatStruct() {
              out.writeCard8(depth);
              out.writeCard8(bitsPerPixel);
              out.writeCard8(scanlinePad);
              out.writePad(5);
            }
        '''.stripIndent()
    }

    def 'typeSpec struct from node with normal fields and pad'() {
        given:
        xmlBuilder.xcb() {
            struct(name:'FORMAT') {
                field(type:'CARD8', name:'depth')
                field(type:'CARD8', name:'bits_per_pixel')
                field(type:'CARD8', name:'scanline_pad')
                pad(bytes:5)
            }
        }

        when:
        result.addStruct(getFirstChild())

        then:
        result.structs.FORMAT.typeSpec.toString() == '''\
            class FormatStruct {
              private byte depth;
            
              private byte bitsPerPixel;
            
              private byte scanlinePad;
            
              public static com.github.moaxcp.x11client.protocol.xproto.FormatStruct readFormatStruct() {
                byte depth = in.readCard8();
                byte bitsPerPixel = in.readCard8();
                byte scanlinePad = in.readCard8();
                in.readPad(5);
                com.github.moaxcp.x11client.protocol.xproto.FormatStruct struct = new com.github.moaxcp.x11client.protocol.xproto.FormatStruct();
                struct.setDepth(depth);
                struct.setBitsPerPixel(bitsPerPixel);
                struct.setScanlinePad(scanlinePad);
                return struct;
              }
            
              public void writeFormatStruct() {
                out.writeCard8(depth);
                out.writeCard8(bitsPerPixel);
                out.writeCard8(scanlinePad);
                out.writePad(5);
              }
            }
        '''.stripIndent()
    }
}
