package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CopyColormapAndFree implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 80;

  private int mid;

  private int srcCmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CopyColormapAndFree readCopyColormapAndFree(X11Input in) throws IOException {
    CopyColormapAndFree.CopyColormapAndFreeBuilder javaBuilder = CopyColormapAndFree.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int mid = in.readCard32();
    int srcCmap = in.readCard32();
    javaBuilder.mid(mid);
    javaBuilder.srcCmap(srcCmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(mid);
    out.writeCard32(srcCmap);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CopyColormapAndFreeBuilder {
    public int getSize() {
      return 12;
    }
  }
}
