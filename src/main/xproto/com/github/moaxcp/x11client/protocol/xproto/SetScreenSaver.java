package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetScreenSaver implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 107;

  private short timeout;

  private short interval;

  private byte preferBlanking;

  private byte allowExposures;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetScreenSaver readSetScreenSaver(X11Input in) throws IOException {
    SetScreenSaver.SetScreenSaverBuilder javaBuilder = SetScreenSaver.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short timeout = in.readInt16();
    short interval = in.readInt16();
    byte preferBlanking = in.readCard8();
    byte allowExposures = in.readCard8();
    javaBuilder.timeout(timeout);
    javaBuilder.interval(interval);
    javaBuilder.preferBlanking(preferBlanking);
    javaBuilder.allowExposures(allowExposures);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeInt16(timeout);
    out.writeInt16(interval);
    out.writeCard8(preferBlanking);
    out.writeCard8(allowExposures);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 10;
  }

  public static class SetScreenSaverBuilder {
    public SetScreenSaver.SetScreenSaverBuilder preferBlanking(Blanking preferBlanking) {
      this.preferBlanking = (byte) preferBlanking.getValue();
      return this;
    }

    public SetScreenSaver.SetScreenSaverBuilder preferBlanking(byte preferBlanking) {
      this.preferBlanking = preferBlanking;
      return this;
    }

    public SetScreenSaver.SetScreenSaverBuilder allowExposures(Exposures allowExposures) {
      this.allowExposures = (byte) allowExposures.getValue();
      return this;
    }

    public SetScreenSaver.SetScreenSaverBuilder allowExposures(byte allowExposures) {
      this.allowExposures = allowExposures;
      return this;
    }

    public int getSize() {
      return 10;
    }
  }
}
