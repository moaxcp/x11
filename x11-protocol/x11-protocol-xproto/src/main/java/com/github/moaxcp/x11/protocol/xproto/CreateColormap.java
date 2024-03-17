package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateColormap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 78;

  private byte alloc;

  private int mid;

  private int window;

  private int visual;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateColormap readCreateColormap(X11Input in) throws IOException {
    CreateColormap.CreateColormapBuilder javaBuilder = CreateColormap.builder();
    byte alloc = in.readByte();
    short length = in.readCard16();
    int mid = in.readCard32();
    int window = in.readCard32();
    int visual = in.readCard32();
    javaBuilder.alloc(alloc);
    javaBuilder.mid(mid);
    javaBuilder.window(window);
    javaBuilder.visual(visual);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeByte(alloc);
    out.writeCard16((short) getLength());
    out.writeCard32(mid);
    out.writeCard32(window);
    out.writeCard32(visual);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateColormapBuilder {
    public CreateColormap.CreateColormapBuilder alloc(ColormapAlloc alloc) {
      this.alloc = (byte) alloc.getValue();
      return this;
    }

    public CreateColormap.CreateColormapBuilder alloc(byte alloc) {
      this.alloc = alloc;
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
