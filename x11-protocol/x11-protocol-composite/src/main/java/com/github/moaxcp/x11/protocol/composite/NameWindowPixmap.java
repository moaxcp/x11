package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NameWindowPixmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "composite";

  public static final byte OPCODE = 6;

  private int window;

  private int pixmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static NameWindowPixmap readNameWindowPixmap(X11Input in) throws IOException {
    NameWindowPixmap.NameWindowPixmapBuilder javaBuilder = NameWindowPixmap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int pixmap = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.pixmap(pixmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(pixmap);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class NameWindowPixmapBuilder {
    public int getSize() {
      return 12;
    }
  }
}
