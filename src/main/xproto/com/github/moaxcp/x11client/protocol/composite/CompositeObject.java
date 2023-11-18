package com.github.moaxcp.x11client.protocol.composite;

import com.github.moaxcp.x11client.protocol.XObject;

interface CompositeObject extends XObject {
  default String getPluginName() {
    return CompositePlugin.NAME;
  }
}
