package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SelectInput implements OneWayRequest, ShapeObject {
  public static final byte OPCODE = 6;

  private int destinationWindow;

  private boolean enable;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectInput readSelectInput(X11Input in) throws IOException {
    SelectInput.SelectInputBuilder javaBuilder = SelectInput.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int destinationWindow = in.readCard32();
    boolean enable = in.readBool();
    byte[] pad5 = in.readPad(3);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.enable(enable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(destinationWindow);
    out.writeBool(enable);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class SelectInputBuilder {
    public int getSize() {
      return 12;
    }
  }
}
