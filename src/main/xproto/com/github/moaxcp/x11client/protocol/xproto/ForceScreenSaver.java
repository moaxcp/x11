package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ForceScreenSaver implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 115;

  private byte mode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ForceScreenSaver readForceScreenSaver(X11Input in) throws IOException {
    ForceScreenSaver.ForceScreenSaverBuilder javaBuilder = ForceScreenSaver.builder();
    byte mode = in.readCard8();
    short length = in.readCard16();
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(mode);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class ForceScreenSaverBuilder {
    public ForceScreenSaver.ForceScreenSaverBuilder mode(ScreenSaver mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ForceScreenSaver.ForceScreenSaverBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 4;
    }
  }
}
