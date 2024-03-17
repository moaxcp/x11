package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetPortAttribute implements OneWayRequest {
  public static final String PLUGIN_NAME = "xv";

  public static final byte OPCODE = 13;

  private int port;

  private int attribute;

  private int value;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPortAttribute readSetPortAttribute(X11Input in) throws IOException {
    SetPortAttribute.SetPortAttributeBuilder javaBuilder = SetPortAttribute.builder();
    byte majorOpcode = in.readCard8();
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeCard32(attribute);
    out.writeInt32(value);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetPortAttributeBuilder {
    public int getSize() {
      return 16;
    }
  }
}
