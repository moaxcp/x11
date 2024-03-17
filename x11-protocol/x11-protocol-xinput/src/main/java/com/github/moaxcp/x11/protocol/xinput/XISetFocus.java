package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XISetFocus implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 49;

  private int window;

  private int time;

  private short deviceid;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XISetFocus readXISetFocus(X11Input in) throws IOException {
    XISetFocus.XISetFocusBuilder javaBuilder = XISetFocus.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int time = in.readCard32();
    short deviceid = in.readCard16();
    byte[] pad6 = in.readPad(2);
    javaBuilder.window(window);
    javaBuilder.time(time);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(time);
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

  public static class XISetFocusBuilder {
    public int getSize() {
      return 16;
    }
  }
}
