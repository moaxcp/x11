package com.github.moaxcp.x11client.protocol.xtest;

import com.github.moaxcp.x11client.protocol.XObject;

interface XtestObject extends XObject {
  default String getPluginName() {
    return XtestPlugin.NAME;
  }
}
