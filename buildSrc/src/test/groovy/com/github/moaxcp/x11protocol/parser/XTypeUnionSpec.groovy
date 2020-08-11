package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ClassName

import static com.github.moaxcp.x11protocol.generator.Conventions.getUnionJavaName

class XTypeUnionSpec extends XmlSpec {
    def create() {
        given:
        xmlBuilder.xcb() {
            struct(name:'CommonBehavior') {
                field(name:'type', type:'CARD8')
                field(name:'data', type:'CARD8')
            }
            struct(name:'DefaultBehavior') {
                field(name:'type', type:'CARD8')
                pad(bytes:'1')
            }
            union(name:'Behavior') {
                field(name:'common', type:'CommonBehavior')
                field(name:'default', type:'DefaultBehavior')
                field(name:'type', type:'CARD8')
            }
        }
        addChildNodes()

        when:
        XTypeUnion union = result.resolveXType('Behavior')
        ClassName unionClass = ClassName.get(union.javaPackage, getUnionJavaName(union.name))
        XTypeStruct common = result.resolveXType('CommonBehavior')
        XTypeStruct defaultStruct = result.resolveXType('DefaultBehavior')

        then:
        union.name == 'Behavior'
        common.superType == unionClass
        defaultStruct.superType == unionClass
    }
}
