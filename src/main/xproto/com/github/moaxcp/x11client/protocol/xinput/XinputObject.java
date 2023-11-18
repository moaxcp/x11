package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.XObject;

interface XinputObject extends XObject {
  default String getPluginName() {
    return XinputPlugin.NAME;
  }
}
