package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDeviceDontPropagateList implements TwoWayRequest<GetDeviceDontPropagateListReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 9;

  private int window;

  public XReplyFunction<GetDeviceDontPropagateListReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDeviceDontPropagateListReply.readGetDeviceDontPropagateListReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDeviceDontPropagateList readGetDeviceDontPropagateList(X11Input in) throws
      IOException {
    GetDeviceDontPropagateList.GetDeviceDontPropagateListBuilder javaBuilder = GetDeviceDontPropagateList.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceDontPropagateListBuilder {
    public int getSize() {
      return 8;
    }
  }
}
