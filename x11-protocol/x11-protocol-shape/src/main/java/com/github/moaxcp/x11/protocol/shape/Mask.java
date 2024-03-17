package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Mask implements OneWayRequest {
  public static final String PLUGIN_NAME = "shape";

  public static final byte OPCODE = 2;

  private byte operation;

  private byte destinationKind;

  private int destinationWindow;

  private short xOffset;

  private short yOffset;

  private int sourceBitmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Mask readMask(X11Input in) throws IOException {
    Mask.MaskBuilder javaBuilder = Mask.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte operation = in.readCard8();
    byte destinationKind = in.readCard8();
    byte[] pad5 = in.readPad(2);
    int destinationWindow = in.readCard32();
    short xOffset = in.readInt16();
    short yOffset = in.readInt16();
    int sourceBitmap = in.readCard32();
    javaBuilder.operation(operation);
    javaBuilder.destinationKind(destinationKind);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    javaBuilder.sourceBitmap(sourceBitmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(operation);
    out.writeCard8(destinationKind);
    out.writePad(2);
    out.writeCard32(destinationWindow);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
    out.writeCard32(sourceBitmap);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class MaskBuilder {
    public Mask.MaskBuilder operation(So operation) {
      this.operation = (byte) operation.getValue();
      return this;
    }

    public Mask.MaskBuilder operation(byte operation) {
      this.operation = operation;
      return this;
    }

    public Mask.MaskBuilder destinationKind(Sk destinationKind) {
      this.destinationKind = (byte) destinationKind.getValue();
      return this;
    }

    public Mask.MaskBuilder destinationKind(byte destinationKind) {
      this.destinationKind = destinationKind;
      return this;
    }

    public int getSize() {
      return 20;
    }
  }
}
