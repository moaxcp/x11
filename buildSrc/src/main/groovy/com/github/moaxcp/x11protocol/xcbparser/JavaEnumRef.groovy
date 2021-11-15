package com.github.moaxcp.x11protocol.xcbparser

import com.squareup.javapoet.ClassName
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class JavaEnumRef {
  ClassName enumType
  String enumItem
}
