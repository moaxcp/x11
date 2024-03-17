package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PutImage implements OneWayRequest {
  public static final String PLUGIN_NAME = "xv";

  public static final byte OPCODE = 18;

  private int port;

  private int drawable;

  private int gc;

  private int id;

  private short srcX;

  private short srcY;

  private short srcW;

  private short srcH;

  private short drwX;

  private short drwY;

  private short drwW;

  private short drwH;

  private short width;

  private short height;

  @NonNull
  private List<Byte> data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PutImage readPutImage(X11Input in) throws IOException {
    PutImage.PutImageBuilder javaBuilder = PutImage.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int port = in.readCard32();
    javaStart += 4;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    int id = in.readCard32();
    javaStart += 4;
    short srcX = in.readInt16();
    javaStart += 2;
    short srcY = in.readInt16();
    javaStart += 2;
    short srcW = in.readCard16();
    javaStart += 2;
    short srcH = in.readCard16();
    javaStart += 2;
    short drwX = in.readInt16();
    javaStart += 2;
    short drwY = in.readInt16();
    javaStart += 2;
    short drwW = in.readCard16();
    javaStart += 2;
    short drwH = in.readCard16();
    javaStart += 2;
    short width = in.readCard16();
    javaStart += 2;
    short height = in.readCard16();
    javaStart += 2;
    List<Byte> data = in.readCard8(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.port(port);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.id(id);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.srcW(srcW);
    javaBuilder.srcH(srcH);
    javaBuilder.drwX(drwX);
    javaBuilder.drwY(drwY);
    javaBuilder.drwW(drwW);
    javaBuilder.drwH(drwH);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
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
    out.writeCard32(id);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    out.writeCard16(srcW);
    out.writeCard16(srcH);
    out.writeInt16(drwX);
    out.writeInt16(drwY);
    out.writeCard16(drwW);
    out.writeCard16(drwH);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard8(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 40 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PutImageBuilder {
    public int getSize() {
      return 40 + 1 * data.size();
    }
  }
}
