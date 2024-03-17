package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetVersion implements TwoWayRequest<GetVersionReply> {
  public static final String PLUGIN_NAME = "xtest";

  public static final byte OPCODE = 0;

  private byte majorVersion;

  private short minorVersion;

  public XReplyFunction<GetVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetVersionReply.readGetVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetVersion readGetVersion(X11Input in) throws IOException {
    GetVersion.GetVersionBuilder javaBuilder = GetVersion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte majorVersion = in.readCard8();
    byte[] pad4 = in.readPad(1);
    short minorVersion = in.readCard16();
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(majorVersion);
    out.writePad(1);
    out.writeCard16(minorVersion);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetVersionBuilder {
    public int getSize() {
      return 8;
    }
  }
}
