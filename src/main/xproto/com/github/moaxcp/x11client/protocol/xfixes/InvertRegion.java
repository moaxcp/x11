package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.xproto.Rectangle;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InvertRegion implements OneWayRequest, XfixesObject {
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
    byte[] pad1 = in.readPad(1);
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
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(source);
    bounds.write(out);
    out.writeCard32(destination);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class InvertRegionBuilder {
    public int getSize() {
      return 20;
    }
  }
}
