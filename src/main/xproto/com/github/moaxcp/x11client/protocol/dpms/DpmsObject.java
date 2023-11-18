package com.github.moaxcp.x11client.protocol.dpms;

import com.github.moaxcp.x11client.protocol.XObject;

interface DpmsObject extends XObject {
  default String getPluginName() {
    return DpmsPlugin.NAME;
  }
}
