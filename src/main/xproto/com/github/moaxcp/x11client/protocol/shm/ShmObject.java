package com.github.moaxcp.x11client.protocol.shm;

import com.github.moaxcp.x11client.protocol.XObject;

interface ShmObject extends XObject {
  default String getPluginName() {
    return ShmPlugin.NAME;
  }
}
