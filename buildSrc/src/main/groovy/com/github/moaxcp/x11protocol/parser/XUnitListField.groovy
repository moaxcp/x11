package com.github.moaxcp.x11protocol.parser

import com.github.moaxcp.x11protocol.parser.expression.Expression
import com.github.moaxcp.x11protocol.parser.expression.ExpressionFactory
import com.github.moaxcp.x11protocol.parser.expression.FieldRefExpression
import groovy.util.slurpersupport.Node

class XUnitListField extends XUnitField {
    Expression lengthExpression

    static XUnitListField xUnitListField(XResult result, Node node) {
        String fieldName = node.attributes().get('name')
        String fieldType = node.attributes().get('type')
        String fieldEnum = node.attributes().get('enum')
        String fieldAltEnum = node.attributes().get('altenum')
        String fieldMask = node.attributes().get('mask')
        String fieldAltMask = node.attributes().get('altmask')

        Expression expression = null
        if(node.childNodes().hasNext()) {
            expression = ExpressionFactory.getExpression(result.basePackage, (Node) node.childNodes().next())
        }

        return new XUnitListField(
            result:result,
            name:fieldName,
            type:fieldType,
            enumType:fieldEnum,
            altEnumType: fieldAltEnum,
            maskType:fieldMask,
            altMaskType: fieldAltMask,
            lengthExpression: expression
        )
    }

    FieldRefExpression getLengthField() {
        List<FieldRefExpression> lengthFields = lengthExpression?.fieldRefs?.findAll {
            it.fieldName.endsWith('_len')
        } ?: []
        if(lengthFields.size() > 1) {
            throw new IllegalStateException("multiple lengthFields for $name in $lengthFields")
        }

        return lengthFields[0]
    }

    @Override
    JavaListProperty getJavaUnit() {
        if(enumType) {
            return resolvedEnumType.getJavaListProperty(this)
        }
        return resolvedType.getJavaListProperty(this)
    }
}
