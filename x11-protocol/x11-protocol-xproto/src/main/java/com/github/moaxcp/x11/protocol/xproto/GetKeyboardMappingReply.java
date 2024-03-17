package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetKeyboardMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private byte keysymsPerKeycode;

  private short sequenceNumber;

  @NonNull
  private List<Integer> keysyms;

  public static GetKeyboardMappingReply readGetKeyboardMappingReply(byte keysymsPerKeycode,
      short sequenceNumber, X11Input in) throws IOException {
    GetKeyboardMappingReply.GetKeyboardMappingReplyBuilder javaBuilder = GetKeyboardMappingReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    List<Integer> keysyms = in.readCard32((int) (Integer.toUnsignedLong(length)));
    javaBuilder.keysymsPerKeycode(keysymsPerKeycode);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keysyms(keysyms);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(keysymsPerKeycode);
    out.writeCard16(sequenceNumber);
    int length = keysyms.size();
    out.writeCard32(getLength());
    out.writePad(24);
    out.writeCard32(keysyms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * keysyms.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetKeyboardMappingReplyBuilder {
    public int getSize() {
      return 32 + 4 * keysyms.size();
    }
  }
}
