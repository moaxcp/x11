package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WarpPointer implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 41;

  private int srcWindow;

  private int dstWindow;

  private short srcX;

  private short srcY;

  private short srcWidth;

  private short srcHeight;

  private short dstX;

  private short dstY;

  public byte getOpCode() {
    return OPCODE;
  }

  public static WarpPointer readWarpPointer(X11Input in) throws IOException {
    WarpPointer.WarpPointerBuilder javaBuilder = WarpPointer.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int srcWindow = in.readCard32();
    int dstWindow = in.readCard32();
    short srcX = in.readInt16();
    short srcY = in.readInt16();
    short srcWidth = in.readCard16();
    short srcHeight = in.readCard16();
    short dstX = in.readInt16();
    short dstY = in.readInt16();
    javaBuilder.srcWindow(srcWindow);
    javaBuilder.dstWindow(dstWindow);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.srcWidth(srcWidth);
    javaBuilder.srcHeight(srcHeight);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(srcWindow);
    out.writeCard32(dstWindow);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    out.writeCard16(srcWidth);
    out.writeCard16(srcHeight);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class WarpPointerBuilder {
    public int getSize() {
      return 24;
    }
  }
}
