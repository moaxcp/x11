package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetClientVersion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xf86vidmode";

  public static final byte OPCODE = 14;

  private short major;

  private short minor;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetClientVersion readSetClientVersion(X11Input in) throws IOException {
    SetClientVersion.SetClientVersionBuilder javaBuilder = SetClientVersion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short major = in.readCard16();
    short minor = in.readCard16();
    javaBuilder.major(major);
    javaBuilder.minor(minor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(major);
    out.writeCard16(minor);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetClientVersionBuilder {
    public int getSize() {
      return 8;
    }
  }
}
