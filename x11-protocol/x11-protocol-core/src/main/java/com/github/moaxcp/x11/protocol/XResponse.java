package com.github.moaxcp.x11.protocol;

public interface XResponse {
  byte getResponseCode();
  short getSequenceNumber();
}
