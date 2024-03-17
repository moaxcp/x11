package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIQueryVersion implements TwoWayRequest<XIQueryVersionReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 47;

  private short majorVersion;

  private short minorVersion;

  public XReplyFunction<XIQueryVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIQueryVersionReply.readXIQueryVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIQueryVersion readXIQueryVersion(X11Input in) throws IOException {
    XIQueryVersion.XIQueryVersionBuilder javaBuilder = XIQueryVersion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short majorVersion = in.readCard16();
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
    out.writeCard16(majorVersion);
    out.writeCard16(minorVersion);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIQueryVersionBuilder {
    public int getSize() {
      return 8;
    }
  }
}
