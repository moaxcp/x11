package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIWarpPointer implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 41;

  private int srcWin;

  private int dstWin;

  private int srcX;

  private int srcY;

  private short srcWidth;

  private short srcHeight;

  private int dstX;

  private int dstY;

  private short deviceid;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIWarpPointer readXIWarpPointer(X11Input in) throws IOException {
    XIWarpPointer.XIWarpPointerBuilder javaBuilder = XIWarpPointer.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int srcWin = in.readCard32();
    int dstWin = in.readCard32();
    int srcX = in.readInt32();
    int srcY = in.readInt32();
    short srcWidth = in.readCard16();
    short srcHeight = in.readCard16();
    int dstX = in.readInt32();
    int dstY = in.readInt32();
    short deviceid = in.readCard16();
    byte[] pad12 = in.readPad(2);
    javaBuilder.srcWin(srcWin);
    javaBuilder.dstWin(dstWin);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.srcWidth(srcWidth);
    javaBuilder.srcHeight(srcHeight);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(srcWin);
    out.writeCard32(dstWin);
    out.writeInt32(srcX);
    out.writeInt32(srcY);
    out.writeCard16(srcWidth);
    out.writeCard16(srcHeight);
    out.writeInt32(dstX);
    out.writeInt32(dstY);
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIWarpPointerBuilder {
    public int getSize() {
      return 36;
    }
  }
}
