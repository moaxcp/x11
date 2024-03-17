package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WaitSBC implements TwoWayRequest<WaitSBCReply> {
  public static final String PLUGIN_NAME = "dri2";

  public static final byte OPCODE = 11;

  private int drawable;

  private int targetSbcHi;

  private int targetSbcLo;

  public XReplyFunction<WaitSBCReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> WaitSBCReply.readWaitSBCReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static WaitSBC readWaitSBC(X11Input in) throws IOException {
    WaitSBC.WaitSBCBuilder javaBuilder = WaitSBC.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int targetSbcHi = in.readCard32();
    int targetSbcLo = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.targetSbcHi(targetSbcHi);
    javaBuilder.targetSbcLo(targetSbcLo);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(targetSbcHi);
    out.writeCard32(targetSbcLo);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class WaitSBCBuilder {
    public int getSize() {
      return 16;
    }
  }
}
