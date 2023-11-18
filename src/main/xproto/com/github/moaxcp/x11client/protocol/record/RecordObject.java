package com.github.moaxcp.x11client.protocol.record;

import com.github.moaxcp.x11client.protocol.XObject;

interface RecordObject extends XObject {
  default String getPluginName() {
    return RecordPlugin.NAME;
  }
}
