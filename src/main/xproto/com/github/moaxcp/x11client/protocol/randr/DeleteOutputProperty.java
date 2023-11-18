package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeleteOutputProperty implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 14;

  private int output;

  private int property;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteOutputProperty readDeleteOutputProperty(X11Input in) throws IOException {
    DeleteOutputProperty.DeleteOutputPropertyBuilder javaBuilder = DeleteOutputProperty.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int output = in.readCard32();
    int property = in.readCard32();
    javaBuilder.output(output);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class DeleteOutputPropertyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
