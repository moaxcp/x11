package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Offset implements OneWayRequest {
  public static final String PLUGIN_NAME = "shape";

  public static final byte OPCODE = 4;

  private byte destinationKind;

  private int destinationWindow;

  private short xOffset;

  private short yOffset;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Offset readOffset(X11Input in) throws IOException {
    Offset.OffsetBuilder javaBuilder = Offset.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte destinationKind = in.readCard8();
    byte[] pad4 = in.readPad(3);
    int destinationWindow = in.readCard32();
    short xOffset = in.readInt16();
    short yOffset = in.readInt16();
    javaBuilder.destinationKind(destinationKind);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(destinationKind);
    out.writePad(3);
    out.writeCard32(destinationWindow);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class OffsetBuilder {
    public Offset.OffsetBuilder destinationKind(Sk destinationKind) {
      this.destinationKind = (byte) destinationKind.getValue();
      return this;
    }

    public Offset.OffsetBuilder destinationKind(byte destinationKind) {
      this.destinationKind = destinationKind;
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
