package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.XObject;

interface RenderObject extends XObject {
  default String getPluginName() {
    return RenderPlugin.NAME;
  }
}
