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
public class GetTexLevelParameteriv implements TwoWayRequest<GetTexLevelParameterivReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = (byte) 139;

  private int contextTag;

  private int target;

  private int level;

  private int pname;

  public XReplyFunction<GetTexLevelParameterivReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetTexLevelParameterivReply.readGetTexLevelParameterivReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetTexLevelParameteriv readGetTexLevelParameteriv(X11Input in) throws IOException {
    GetTexLevelParameteriv.GetTexLevelParameterivBuilder javaBuilder = GetTexLevelParameteriv.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int target = in.readCard32();
    int level = in.readInt32();
    int pname = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.target(target);
    javaBuilder.level(level);
    javaBuilder.pname(pname);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(target);
    out.writeInt32(level);
    out.writeCard32(pname);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetTexLevelParameterivBuilder {
    public int getSize() {
      return 20;
    }
  }
}
