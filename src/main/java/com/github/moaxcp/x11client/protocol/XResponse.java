package com.github.moaxcp.x11client.protocol;

public interface XResponse {
  byte getResponseCode();
  short getSequenceNumber();
}
