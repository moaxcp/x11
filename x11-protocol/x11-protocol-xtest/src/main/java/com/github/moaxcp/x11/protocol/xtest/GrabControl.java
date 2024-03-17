package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GrabControl implements OneWayRequest {
  public static final String PLUGIN_NAME = "xtest";

  public static final byte OPCODE = 3;

  private boolean impervious;

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabControl readGrabControl(X11Input in) throws IOException {
    GrabControl.GrabControlBuilder javaBuilder = GrabControl.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    boolean impervious = in.readBool();
    byte[] pad4 = in.readPad(3);
    javaBuilder.impervious(impervious);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeBool(impervious);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GrabControlBuilder {
    public int getSize() {
      return 8;
    }
  }
}
