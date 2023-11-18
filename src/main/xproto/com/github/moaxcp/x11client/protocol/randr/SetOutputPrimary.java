package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetOutputPrimary implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 30;

  private int window;

  private int output;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetOutputPrimary readSetOutputPrimary(X11Input in) throws IOException {
    SetOutputPrimary.SetOutputPrimaryBuilder javaBuilder = SetOutputPrimary.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int output = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.output(output);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(output);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class SetOutputPrimaryBuilder {
    public int getSize() {
      return 12;
    }
  }
}
