package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.XObject;

interface XprotoObject extends XObject {
  default String getPluginName() {
    return XprotoPlugin.NAME;
  }
}
