package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.XReply;

import java.io.IOException;

public interface XIGetPropertyReply extends XReply {
  static XIGetPropertyReply readXIGetPropertyReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    int length = in.readCard32();
    int type = in.readCard32();
    int bytesAfter = in.readCard32();
    int numItems = in.readCard32();
    byte format = in.readCard8();
    in.readPad(11);
    PropertyFormat ref = PropertyFormat.getByCode(format);
    if(ref == PropertyFormat.EIGHT_BITS) {
      return XIGetProperty8BitsReply.readXIGetProperty8BitsReply((byte) 1, (byte) 0, sequenceNumber, length, type, bytesAfter, numItems, format, new byte[11], in);
    }
    if(ref == PropertyFormat.SIXTEEN_BITS) {
      return XIGetProperty16BitsReply.readXIGetProperty16BitsReply((byte) 1, (byte) 0, sequenceNumber, length, type, bytesAfter, numItems, format, new byte[11], in);
    }
    if(ref == PropertyFormat.THIRTY_TWO_BITS) {
      return XIGetProperty32BitsReply.readXIGetProperty32BitsReply((byte) 1, (byte) 0, sequenceNumber, length, type, bytesAfter, numItems, format, new byte[11], in);
    }
    throw new IllegalStateException("Could not find class for " + ref);
  }
}
