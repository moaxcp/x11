package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UninstallColormap implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 82;

  private int cmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UninstallColormap readUninstallColormap(X11Input in) throws IOException {
    UninstallColormap.UninstallColormapBuilder javaBuilder = UninstallColormap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cmap = in.readCard32();
    javaBuilder.cmap(cmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class UninstallColormapBuilder {
    public int getSize() {
      return 8;
    }
  }
}
