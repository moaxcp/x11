package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.XObject;

interface RandrObject extends XObject {
  default String getPluginName() {
    return RandrPlugin.NAME;
  }
}
