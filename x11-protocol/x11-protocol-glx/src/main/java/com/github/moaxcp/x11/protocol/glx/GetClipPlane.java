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
public class GetClipPlane implements TwoWayRequest<GetClipPlaneReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 113;

  private int contextTag;

  private int plane;

  public XReplyFunction<GetClipPlaneReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetClipPlaneReply.readGetClipPlaneReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetClipPlane readGetClipPlane(X11Input in) throws IOException {
    GetClipPlane.GetClipPlaneBuilder javaBuilder = GetClipPlane.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int plane = in.readInt32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.plane(plane);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeInt32(plane);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetClipPlaneBuilder {
    public int getSize() {
      return 12;
    }
  }
}
