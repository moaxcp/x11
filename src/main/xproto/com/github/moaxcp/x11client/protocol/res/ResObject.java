package com.github.moaxcp.x11client.protocol.res;

import com.github.moaxcp.x11client.protocol.XObject;

interface ResObject extends XObject {
  default String getPluginName() {
    return ResPlugin.NAME;
  }
}
