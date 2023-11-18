package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.XObject;

interface ShapeObject extends XObject {
  default String getPluginName() {
    return ShapePlugin.NAME;
  }
}
