package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetClientVersion implements OneWayRequest, Xf86vidmodeObject {
  public static final byte OPCODE = 14;

  private short major;

  private short minor;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetClientVersion readSetClientVersion(X11Input in) throws IOException {
    SetClientVersion.SetClientVersionBuilder javaBuilder = SetClientVersion.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short major = in.readCard16();
    short minor = in.readCard16();
    javaBuilder.major(major);
    javaBuilder.minor(minor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(major);
    out.writeCard16(minor);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class SetClientVersionBuilder {
    public int getSize() {
      return 8;
    }
  }
}
