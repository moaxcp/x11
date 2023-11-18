package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.XObject;

interface Dri2Object extends XObject {
  default String getPluginName() {
    return Dri2Plugin.NAME;
  }
}
