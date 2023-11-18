package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.XObject;

interface XfixesObject extends XObject {
  default String getPluginName() {
    return XfixesPlugin.NAME;
  }
}
