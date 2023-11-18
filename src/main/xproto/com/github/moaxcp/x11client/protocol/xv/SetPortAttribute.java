package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetPortAttribute implements OneWayRequest, XvObject {
  public static final byte OPCODE = 13;

  private int port;

  private int attribute;

  private int value;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPortAttribute readSetPortAttribute(X11Input in) throws IOException {
    SetPortAttribute.SetPortAttributeBuilder javaBuilder = SetPortAttribute.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int port = in.readCard32();
    int attribute = in.readCard32();
    int value = in.readInt32();
    javaBuilder.port(port);
    javaBuilder.attribute(attribute);
    javaBuilder.value(value);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeCard32(attribute);
    out.writeInt32(value);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class SetPortAttributeBuilder {
    public int getSize() {
      return 16;
    }
  }
}
