package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Mask implements OneWayRequest, ShapeObject {
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
    byte operation = in.readCard8();
    short length = in.readCard16();
    byte destinationKind = in.readCard8();
    byte[] pad4 = in.readPad(2);
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
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(operation);
    out.writeCard16((short) getLength());
    out.writeCard8(destinationKind);
    out.writePad(2);
    out.writeCard32(destinationWindow);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
    out.writeCard32(sourceBitmap);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 19;
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
      return 19;
    }
  }
}
