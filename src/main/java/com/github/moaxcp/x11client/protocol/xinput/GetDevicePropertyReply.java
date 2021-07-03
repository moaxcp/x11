package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XReply;

import java.io.IOException;

public interface GetDevicePropertyReply extends XReply {
  static GetDevicePropertyReply readGetDevicePropertyReply(byte xiReplyType, short sequenceNumber, X11Input in) throws IOException {
    int length = in.readCard32();
    int type = in.readCard32();
    int bytesAfter = in.readCard32();
    int numItems = in.readCard32();
    byte format = in.readCard8();
    byte deviceId = in.readCard8();
    in.readPad(10);
    PropertyFormat ref = PropertyFormat.getByCode(format);
    if(ref == PropertyFormat.EIGHT_BITS) {
      return GetDeviceProperty8BitsReply.readGetDeviceProperty8BitsReply((byte) 1, xiReplyType, sequenceNumber,
          length, type, bytesAfter, numItems, format, deviceId, new byte[10], in);
    }
    if(ref == PropertyFormat.SIXTEEN_BITS) {
      return GetDeviceProperty16BitsReply.readGetDeviceProperty16BitsReply((byte) 1, xiReplyType, sequenceNumber,
          length, type, bytesAfter, numItems, format, deviceId, new byte[10], in);
    }
    if(ref == PropertyFormat.THIRTY_TWO_BITS) {
      return GetDeviceProperty32BitsReply.readGetDeviceProperty32BitsReply((byte) 1, xiReplyType, sequenceNumber,
          length, type, bytesAfter, numItems, format, deviceId, new byte[10], in);
    }
    throw new IllegalStateException("Could not find class for " + ref);
  }
}
