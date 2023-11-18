package com.github.moaxcp.x11client.protocol.damage;

import com.github.moaxcp.x11client.protocol.XObject;

interface DamageObject extends XObject {
  default String getPluginName() {
    return DamagePlugin.NAME;
  }
}
