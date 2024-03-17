package com.github.moaxcp.x11.protocol.xinput;

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
public class GetDeviceKeyMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  private byte keysymsPerKeycode;

  @NonNull
  private List<Integer> keysyms;

  public static GetDeviceKeyMappingReply readGetDeviceKeyMappingReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    GetDeviceKeyMappingReply.GetDeviceKeyMappingReplyBuilder javaBuilder = GetDeviceKeyMappingReply.builder();
    int length = in.readCard32();
    byte keysymsPerKeycode = in.readCard8();
    byte[] pad5 = in.readPad(23);
    List<Integer> keysyms = in.readCard32((int) (Integer.toUnsignedLong(length)));
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keysymsPerKeycode(keysymsPerKeycode);
    javaBuilder.keysyms(keysyms);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    int length = keysyms.size();
    out.writeCard32(getLength());
    out.writeCard8(keysymsPerKeycode);
    out.writePad(23);
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

  public static class GetDeviceKeyMappingReplyBuilder {
    public int getSize() {
      return 32 + 4 * keysyms.size();
    }
  }
}
