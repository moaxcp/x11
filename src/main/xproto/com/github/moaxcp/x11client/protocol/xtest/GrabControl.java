package com.github.moaxcp.x11client.protocol.xtest;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GrabControl implements OneWayRequest, XtestObject {
  public static final byte OPCODE = 3;

  private boolean impervious;

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabControl readGrabControl(X11Input in) throws IOException {
    GrabControl.GrabControlBuilder javaBuilder = GrabControl.builder();
    boolean impervious = in.readBool();
    short length = in.readCard16();
    byte[] pad3 = in.readPad(3);
    javaBuilder.impervious(impervious);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(impervious);
    out.writeCard16((short) getLength());
    out.writePad(3);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7;
  }

  public static class GrabControlBuilder {
    public int getSize() {
      return 7;
    }
  }
}
