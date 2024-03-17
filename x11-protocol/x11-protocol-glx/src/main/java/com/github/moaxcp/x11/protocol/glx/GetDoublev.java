package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDoublev implements TwoWayRequest<GetDoublevReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 114;

  private int contextTag;

  private int pname;

  public XReplyFunction<GetDoublevReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDoublevReply.readGetDoublevReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDoublev readGetDoublev(X11Input in) throws IOException {
    GetDoublev.GetDoublevBuilder javaBuilder = GetDoublev.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int pname = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.pname(pname);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(pname);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDoublevBuilder {
    public int getSize() {
      return 12;
    }
  }
}
