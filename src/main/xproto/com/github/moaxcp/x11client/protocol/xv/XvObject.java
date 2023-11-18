package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.XObject;

interface XvObject extends XObject {
  default String getPluginName() {
    return XvPlugin.NAME;
  }
}
