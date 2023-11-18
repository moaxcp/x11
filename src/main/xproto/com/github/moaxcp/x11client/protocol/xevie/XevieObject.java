package com.github.moaxcp.x11client.protocol.xevie;

import com.github.moaxcp.x11client.protocol.XObject;

interface XevieObject extends XObject {
  default String getPluginName() {
    return XeviePlugin.NAME;
  }
}
