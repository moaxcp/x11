package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 93;

  private int cid;

  private int source;

  private int mask;

  private short foreRed;

  private short foreGreen;

  private short foreBlue;

  private short backRed;

  private short backGreen;

  private short backBlue;

  private short x;

  private short y;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateCursor readCreateCursor(X11Input in) throws IOException {
    CreateCursor.CreateCursorBuilder javaBuilder = CreateCursor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cid = in.readCard32();
    int source = in.readCard32();
    int mask = in.readCard32();
    short foreRed = in.readCard16();
    short foreGreen = in.readCard16();
    short foreBlue = in.readCard16();
    short backRed = in.readCard16();
    short backGreen = in.readCard16();
    short backBlue = in.readCard16();
    short x = in.readCard16();
    short y = in.readCard16();
    javaBuilder.cid(cid);
    javaBuilder.source(source);
    javaBuilder.mask(mask);
    javaBuilder.foreRed(foreRed);
    javaBuilder.foreGreen(foreGreen);
    javaBuilder.foreBlue(foreBlue);
    javaBuilder.backRed(backRed);
    javaBuilder.backGreen(backGreen);
    javaBuilder.backBlue(backBlue);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cid);
    out.writeCard32(source);
    out.writeCard32(mask);
    out.writeCard16(foreRed);
    out.writeCard16(foreGreen);
    out.writeCard16(foreBlue);
    out.writeCard16(backRed);
    out.writeCard16(backGreen);
    out.writeCard16(backBlue);
    out.writeCard16(x);
    out.writeCard16(y);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateCursorBuilder {
    public int getSize() {
      return 32;
    }
  }
}
