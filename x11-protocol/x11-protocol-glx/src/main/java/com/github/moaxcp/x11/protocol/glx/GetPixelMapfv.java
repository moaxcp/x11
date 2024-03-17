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
public class GetPixelMapfv implements TwoWayRequest<GetPixelMapfvReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 125;

  private int contextTag;

  private int map;

  public XReplyFunction<GetPixelMapfvReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPixelMapfvReply.readGetPixelMapfvReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPixelMapfv readGetPixelMapfv(X11Input in) throws IOException {
    GetPixelMapfv.GetPixelMapfvBuilder javaBuilder = GetPixelMapfv.builder();
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

  public static class GetPixelMapfvBuilder {
    public int getSize() {
      return 12;
    }
  }
}
