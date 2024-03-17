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
public class GetMapdv implements TwoWayRequest<GetMapdvReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 120;

  private int contextTag;

  private int target;

  private int query;

  public XReplyFunction<GetMapdvReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMapdvReply.readGetMapdvReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMapdv readGetMapdv(X11Input in) throws IOException {
    GetMapdv.GetMapdvBuilder javaBuilder = GetMapdv.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int target = in.readCard32();
    int query = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.target(target);
    javaBuilder.query(query);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(target);
    out.writeCard32(query);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetMapdvBuilder {
    public int getSize() {
      return 16;
    }
  }
}
