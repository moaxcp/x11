package com.github.moaxcp.x11.protocol;

import java.io.IOException;
import java.util.Optional;

public interface XProtocolPlugin {
  String getPluginName();
  Optional<String> getExtensionXName();
  Optional<String> getExtensionName();
  Optional<Boolean> getExtensionMultiword();
  Optional<Byte> getMajorVersion();
  Optional<Byte> getMinorVersion();
  byte getMajorOpcode();
  void setMajorOpcode(byte firstOpcode);
  byte getFirstEvent();
  void setFirstEvent(byte firstEvent);
  byte getFirstError();
  void setFirstError(byte firstError);
  boolean supportedRequest(XRequest request);
  boolean supportedRequest(byte majorOpcode, byte minorOpcode);
  boolean supportedEvent(byte number);
  boolean supportedError(byte code);
  <T extends XRequest> T readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException;
  <T extends XEvent> T readEvent(byte number, boolean sentEvent, X11Input in) throws IOException;
  <T extends XError> T readError(byte code, X11Input in) throws IOException;
  <T extends XGenericEvent> T readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws IOException;

}
