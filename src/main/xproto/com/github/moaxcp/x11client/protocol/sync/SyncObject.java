package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.XObject;

interface SyncObject extends XObject {
  default String getPluginName() {
    return SyncPlugin.NAME;
  }
}
