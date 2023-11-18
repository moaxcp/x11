package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChangePointerControl implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 105;

  private short accelerationNumerator;

  private short accelerationDenominator;

  private short threshold;

  private boolean doAcceleration;

  private boolean doThreshold;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangePointerControl readChangePointerControl(X11Input in) throws IOException {
    ChangePointerControl.ChangePointerControlBuilder javaBuilder = ChangePointerControl.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short accelerationNumerator = in.readInt16();
    short accelerationDenominator = in.readInt16();
    short threshold = in.readInt16();
    boolean doAcceleration = in.readBool();
    boolean doThreshold = in.readBool();
    javaBuilder.accelerationNumerator(accelerationNumerator);
    javaBuilder.accelerationDenominator(accelerationDenominator);
    javaBuilder.threshold(threshold);
    javaBuilder.doAcceleration(doAcceleration);
    javaBuilder.doThreshold(doThreshold);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeInt16(accelerationNumerator);
    out.writeInt16(accelerationDenominator);
    out.writeInt16(threshold);
    out.writeBool(doAcceleration);
    out.writeBool(doThreshold);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class ChangePointerControlBuilder {
    public int getSize() {
      return 12;
    }
  }
}
