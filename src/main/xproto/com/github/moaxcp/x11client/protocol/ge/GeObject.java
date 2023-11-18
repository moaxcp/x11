package com.github.moaxcp.x11client.protocol.ge;

import com.github.moaxcp.x11client.protocol.XObject;

interface GeObject extends XObject {
  default String getPluginName() {
    return GePlugin.NAME;
  }
}
