package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InvertRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 16;

  private int source;

  @NonNull
  private Rectangle bounds;

  private int destination;

  public byte getOpCode() {
    return OPCODE;
  }

  public static InvertRegion readInvertRegion(X11Input in) throws IOException {
    InvertRegion.InvertRegionBuilder javaBuilder = InvertRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int source = in.readCard32();
    Rectangle bounds = Rectangle.readRectangle(in);
    int destination = in.readCard32();
    javaBuilder.source(source);
    javaBuilder.bounds(bounds);
    javaBuilder.destination(destination);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(source);
    bounds.write(out);
    out.writeCard32(destination);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class InvertRegionBuilder {
    public int getSize() {
      return 20;
    }
  }
}
