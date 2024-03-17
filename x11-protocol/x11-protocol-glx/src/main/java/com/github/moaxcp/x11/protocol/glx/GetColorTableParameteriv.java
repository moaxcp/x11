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
public class GetColorTableParameteriv implements TwoWayRequest<GetColorTableParameterivReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = (byte) 149;

  private int contextTag;

  private int target;

  private int pname;

  public XReplyFunction<GetColorTableParameterivReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetColorTableParameterivReply.readGetColorTableParameterivReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetColorTableParameteriv readGetColorTableParameteriv(X11Input in) throws
      IOException {
    GetColorTableParameteriv.GetColorTableParameterivBuilder javaBuilder = GetColorTableParameteriv.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int target = in.readCard32();
    int pname = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.target(target);
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
    out.writeCard32(pname);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetColorTableParameterivBuilder {
    public int getSize() {
      return 16;
    }
  }
}
