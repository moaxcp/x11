package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XError;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KeyboardError implements XError, XkbObject {
  public static final byte CODE = 0;

  private byte firstErrorOffset;

  private short sequenceNumber;

  private int value;

  private short minorOpcode;

  private byte majorOpcode;

  @Override
  public byte getCode() {
    return (byte) (firstErrorOffset + CODE);
  }

  public static KeyboardError readKeyboardError(byte firstErrorOffset, X11Input in) throws
      IOException {
    KeyboardError.KeyboardErrorBuilder javaBuilder = KeyboardError.builder();
    short sequenceNumber = in.readCard16();
    int value = in.readCard32();
    short minorOpcode = in.readCard16();
    byte majorOpcode = in.readCard8();
    byte[] pad6 = in.readPad(21);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.value(value);
    javaBuilder.minorOpcode(minorOpcode);
    javaBuilder.majorOpcode(majorOpcode);

    javaBuilder.firstErrorOffset(firstErrorOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(getCode());
    out.writeCard16(sequenceNumber);
    out.writeCard32(value);
    out.writeCard16(minorOpcode);
    out.writeCard8(majorOpcode);
    out.writePad(21);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class KeyboardErrorBuilder {
    public int getSize() {
      return 32;
    }
  }
}
