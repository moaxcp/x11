package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIChangeCursor implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 42;

  private int window;

  private int cursor;

  private short deviceid;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIChangeCursor readXIChangeCursor(X11Input in) throws IOException {
    XIChangeCursor.XIChangeCursorBuilder javaBuilder = XIChangeCursor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int cursor = in.readCard32();
    short deviceid = in.readCard16();
    byte[] pad6 = in.readPad(2);
    javaBuilder.window(window);
    javaBuilder.cursor(cursor);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(cursor);
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class XIChangeCursorBuilder {
    public int getSize() {
      return 16;
    }
  }
}
