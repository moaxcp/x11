package com.github.moaxcp.x11protocol.xcbparser

import com.github.moaxcp.x11protocol.XmlSpec
import com.squareup.javapoet.CodeBlock

class JavaPadAlignSpec extends XmlSpec {
    def 'XPadAlign read expression 4'() {
        given:
        JavaClass javaClass = Mock(JavaClass) {
            it.getXUnitSubtype() >> Optional.empty()
        }
        xmlBuilder.list(type:'CARD32', name:'formats') {
            value('5')
        }
        JavaPadAlign align = new JavaPadAlign(align:4,
            list: new JavaPrimativeListProperty(javaClass, new XUnitListField(result, getFirstNode())))

        expect:
        align.declareAndReadCode.toString() == 'in.readPadAlign(5);\n'
    }

    def 'XPadAlign read expression'() {
        given:
        JavaClass javaClass = Mock(JavaClass) {
            it.getXUnitSubtype() >> Optional.empty()
        }
        xmlBuilder.list(type:'CARD32', name:'formats') {
            value('5')
        }
        JavaPadAlign align = new JavaPadAlign(align:5,
            list: new JavaPrimativeListProperty(javaClass, new XUnitListField(result, getFirstNode())))

        expect:
        align.declareAndReadCode.toString() == 'in.readPadAlign(5, 5);\n'
    }

    def 'XPadAlign write expression 4'() {
        given:
        JavaClass javaClass = Mock(JavaClass) {
            it.getXUnitSubtype() >> Optional.empty()
        }
        xmlBuilder.list(type:'CARD32', name:'formats') {
            value('5')
        }
        JavaPadAlign align = new JavaPadAlign(align:4,
            list: new JavaPrimativeListProperty(javaClass, new XUnitListField(result, getFirstNode())))

        when:
        CodeBlock.Builder builder = CodeBlock.builder()
        align.addWriteCode(builder)

        then:
        builder.build().toString() == 'out.writePadAlign(5);\n'
    }

    def 'XPadAlign write expression'() {
        given:
        JavaClass javaClass = Mock(JavaClass) {
            it.getXUnitSubtype() >> Optional.empty()
        }
        xmlBuilder.list(type:'CARD32', name:'formats') {
            value('5')
        }
        JavaPadAlign align = new JavaPadAlign(align:5,
            list: new JavaPrimativeListProperty(javaClass, new XUnitListField(result, getFirstNode())))

        when:
        CodeBlock.Builder builder = CodeBlock.builder()
        align.addWriteCode(builder)

        then:
        builder.build().toString() == 'out.writePadAlign(5, 5);\n'
    }
}
