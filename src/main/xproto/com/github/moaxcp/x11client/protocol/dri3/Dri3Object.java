package com.github.moaxcp.x11client.protocol.dri3;

import com.github.moaxcp.x11client.protocol.XObject;

interface Dri3Object extends XObject {
  default String getPluginName() {
    return Dri3Plugin.NAME;
  }
}
