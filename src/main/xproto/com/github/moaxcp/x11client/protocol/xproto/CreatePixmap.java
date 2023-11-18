package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreatePixmap implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 53;

  private byte depth;

  private int pid;

  private int drawable;

  private short width;

  private short height;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreatePixmap readCreatePixmap(X11Input in) throws IOException {
    CreatePixmap.CreatePixmapBuilder javaBuilder = CreatePixmap.builder();
    byte depth = in.readCard8();
    short length = in.readCard16();
    int pid = in.readCard32();
    int drawable = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.depth(depth);
    javaBuilder.pid(pid);
    javaBuilder.drawable(drawable);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(depth);
    out.writeCard16((short) getLength());
    out.writeCard32(pid);
    out.writeCard32(drawable);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class CreatePixmapBuilder {
    public int getSize() {
      return 16;
    }
  }
}
