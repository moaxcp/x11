package com.github.moaxcp.x11protocol.generator

import com.squareup.javapoet.ClassName

class KeySymResult {
  String keysymPackage
  Map<String, String> keysyms = [:]

  ClassName getClassName() {
    ClassName.get(keysymPackage, "KeySym")
  }
}
