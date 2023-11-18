package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LockModeSwitch implements OneWayRequest, Xf86vidmodeObject {
  public static final byte OPCODE = 5;

  private short screen;

  private short lock;

  public byte getOpCode() {
    return OPCODE;
  }

  public static LockModeSwitch readLockModeSwitch(X11Input in) throws IOException {
    LockModeSwitch.LockModeSwitchBuilder javaBuilder = LockModeSwitch.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short screen = in.readCard16();
    short lock = in.readCard16();
    javaBuilder.screen(screen);
    javaBuilder.lock(lock);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writeCard16(lock);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class LockModeSwitchBuilder {
    public int getSize() {
      return 8;
    }
  }
}
