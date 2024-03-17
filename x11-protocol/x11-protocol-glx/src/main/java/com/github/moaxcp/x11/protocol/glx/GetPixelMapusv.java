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
public class GetPixelMapusv implements TwoWayRequest<GetPixelMapusvReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 127;

  private int contextTag;

  private int map;

  public XReplyFunction<GetPixelMapusvReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPixelMapusvReply.readGetPixelMapusvReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPixelMapusv readGetPixelMapusv(X11Input in) throws IOException {
    GetPixelMapusv.GetPixelMapusvBuilder javaBuilder = GetPixelMapusv.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int map = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.map(map);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(map);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetPixelMapusvBuilder {
    public int getSize() {
      return 12;
    }
  }
}
