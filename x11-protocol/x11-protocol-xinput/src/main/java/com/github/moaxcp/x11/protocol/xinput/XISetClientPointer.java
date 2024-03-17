package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XISetClientPointer implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 44;

  private int window;

  private short deviceid;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XISetClientPointer readXISetClientPointer(X11Input in) throws IOException {
    XISetClientPointer.XISetClientPointerBuilder javaBuilder = XISetClientPointer.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    short deviceid = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.window(window);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XISetClientPointerBuilder {
    public int getSize() {
      return 12;
    }
  }
}
