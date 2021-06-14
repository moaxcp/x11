package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.TypeSpec

class CaseTypeSpec extends XmlSpec {
    def inputInfo() {
        given:
        xmlBuilder.xcb() {
            struct(name: 'InputInfo') {
                field(type: 'CARD8', name: 'class_id', enum: 'InputClass')
                field(type: 'CARD8', name: 'len')
                'switch'(name: 'info') {
                    fieldref('class_id')
                    required_start_align(align: '4', offset: '2')
                    'case'(name: 'key') {
                        enumref(ref: 'InputClass', "Key")
                        field(type: 'KeyCode', name: 'min_keycode')
                        field(type: 'KeyCode', name: 'max_keycode')
                        field(type: 'CARD16', name: 'num_keys')
                        pad(bytes: '2')
                    }
                    'case'(name: 'button') {
                        enumref(ref: 'InputClass', 'Button')
                        field(type: 'CARD16', name: 'num_buttons')
                    }
                    'case'(name: 'valuator') {
                        enumref(ref: 'InputClass', 'Valuator')
                        required_start_align(align: '4', offset: '2')
                        field(type: 'CARD8', name: 'axes_len')
                        field(type: 'CARD8', name: 'mode', enum: 'ValuatorMode')
                        list(type: 'AxisInfo', name: 'axes') {
                            fieldref('axes_len')
                        }
                    }
                }
            }
        }

        addChildNodes()

        when:
        TypeSpec typeSpec = result.resolveXType('InputInfoKey').javaType[0].typeSpec

        then:
        typeSpec.toString() == '''\
        '''.stripIndent()
    }
}
