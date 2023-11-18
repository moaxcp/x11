package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.XObject;

interface PresentObject extends XObject {
  default String getPluginName() {
    return PresentPlugin.NAME;
  }
}
