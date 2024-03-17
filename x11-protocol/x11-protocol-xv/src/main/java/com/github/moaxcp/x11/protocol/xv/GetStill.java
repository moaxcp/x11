package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetStill implements OneWayRequest {
  public static final String PLUGIN_NAME = "xv";

  public static final byte OPCODE = 8;

  private int port;

  private int drawable;

  private int gc;

  private short vidX;

  private short vidY;

  private short vidW;

  private short vidH;

  private short drwX;

  private short drwY;

  private short drwW;

  private short drwH;

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetStill readGetStill(X11Input in) throws IOException {
    GetStill.GetStillBuilder javaBuilder = GetStill.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int port = in.readCard32();
    int drawable = in.readCard32();
    int gc = in.readCard32();
    short vidX = in.readInt16();
    short vidY = in.readInt16();
    short vidW = in.readCard16();
    short vidH = in.readCard16();
    short drwX = in.readInt16();
    short drwY = in.readInt16();
    short drwW = in.readCard16();
    short drwH = in.readCard16();
    javaBuilder.port(port);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.vidX(vidX);
    javaBuilder.vidY(vidY);
    javaBuilder.vidW(vidW);
    javaBuilder.vidH(vidH);
    javaBuilder.drwX(drwX);
    javaBuilder.drwY(drwY);
    javaBuilder.drwW(drwW);
    javaBuilder.drwH(drwH);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeInt16(vidX);
    out.writeInt16(vidY);
    out.writeCard16(vidW);
    out.writeCard16(vidH);
    out.writeInt16(drwX);
    out.writeInt16(drwY);
    out.writeCard16(drwW);
    out.writeCard16(drwH);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetStillBuilder {
    public int getSize() {
      return 32;
    }
  }
}
