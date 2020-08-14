package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.ClassName

class JavaUnionSpec extends XmlSpec {
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
        JavaUnion union = result.resolveXType('Behavior').javaType
        JavaStruct common = result.resolveXType('CommonBehavior').javaType
        JavaStruct defaultStruct = result.resolveXType('DefaultBehavior').javaType

        then:
        union.basePackage == result.basePackage
        union.simpleName == 'BehaviorUnion'
        union.className == ClassName.get(result.javaPackage, 'BehaviorUnion')
        union.typeSpec.toString() == '''\
            public interface BehaviorUnion {
            }
        '''.stripIndent()

        common.superType == union.className
        defaultStruct.superType == union.className
    }
}
