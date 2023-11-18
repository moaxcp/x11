package com.github.moaxcp.x11client.protocol.bigreq;

import com.github.moaxcp.x11client.protocol.XObject;

interface BigreqObject extends XObject {
  default String getPluginName() {
    return BigreqPlugin.NAME;
  }
}
