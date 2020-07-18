package com.github.moaxcp.x11protocol

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import groovy.util.slurpersupport.GPathResult
import spock.lang.Specification

class ProtocolParserSpec extends Specification {
    GPathResult structXml = new XmlSlurper().parseText('''
        <struct name="Class">
            <field type="CARD8" name="class"/>
            <field type="INT32" name="a"/>
            <field type="sync:INT64" name="b"/>
        </struct>
    ''')

    GPathResult typesXml = new XmlSlurper().parseText('''
        <xcb>
            <xidtype name="COLORMAP" />
            <typedef oldname="CARD32" newname="BOOL32" />
            <xidunion name="FONTABLE">
                <type>FONT</type>
                <type>GCONTEXT</type>
            </xidunion>
        </xcb>
    ''')

    ProtocolParser parser = new ProtocolParser(packageName: 'package')

    void 'parse type defs returns all types'() {
        when:
        Map<String, String> result = parser.parseTypeDefs(typesXml)

        then:
        result == ['COLORMAP':'int', 'BOOL32':'int', 'FONTABLE':'int']
    }

    void 'parse fields returns all fields'() {
        when:
        List<FieldSpec> result = parser.parseFields(structXml)

        then:
        result.collectEntries { [(it.name):it.type.toString()] } == ['clazz':'package.byte', 'a':'package.int', 'b':'package.long']
    }

    void 'parse struct has name'() {
        when:
        TypeSpec result = parser.parseStruct(structXml)

        then:
        result.name == 'Class'
    }
}
