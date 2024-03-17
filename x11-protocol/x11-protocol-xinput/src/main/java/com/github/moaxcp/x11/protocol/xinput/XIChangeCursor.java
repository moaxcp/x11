package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIChangeCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 42;

  private int window;

  private int cursor;

  private short deviceid;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIChangeCursor readXIChangeCursor(X11Input in) throws IOException {
    XIChangeCursor.XIChangeCursorBuilder javaBuilder = XIChangeCursor.builder();
    byte majorOpcode = in.readCard8();
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIChangeCursorBuilder {
    public int getSize() {
      return 16;
    }
  }
}
