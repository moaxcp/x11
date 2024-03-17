package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Initialize implements TwoWayRequest<InitializeReply> {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 0;

  private byte desiredMajorVersion;

  private byte desiredMinorVersion;

  public XReplyFunction<InitializeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> InitializeReply.readInitializeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Initialize readInitialize(X11Input in) throws IOException {
    Initialize.InitializeBuilder javaBuilder = Initialize.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte desiredMajorVersion = in.readCard8();
    byte desiredMinorVersion = in.readCard8();
    javaBuilder.desiredMajorVersion(desiredMajorVersion);
    javaBuilder.desiredMinorVersion(desiredMinorVersion);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(desiredMajorVersion);
    out.writeCard8(desiredMinorVersion);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 6;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class InitializeBuilder {
    public int getSize() {
      return 6;
    }
  }
}
