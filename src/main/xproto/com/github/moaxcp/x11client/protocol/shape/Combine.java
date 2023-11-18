package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Combine implements OneWayRequest, ShapeObject {
  public static final byte OPCODE = 3;

  private byte operation;

  private byte destinationKind;

  private byte sourceKind;

  private int destinationWindow;

  private short xOffset;

  private short yOffset;

  private int sourceWindow;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Combine readCombine(X11Input in) throws IOException {
    Combine.CombineBuilder javaBuilder = Combine.builder();
    byte operation = in.readCard8();
    short length = in.readCard16();
    byte destinationKind = in.readCard8();
    byte sourceKind = in.readCard8();
    byte[] pad5 = in.readPad(1);
    int destinationWindow = in.readCard32();
    short xOffset = in.readInt16();
    short yOffset = in.readInt16();
    int sourceWindow = in.readCard32();
    javaBuilder.operation(operation);
    javaBuilder.destinationKind(destinationKind);
    javaBuilder.sourceKind(sourceKind);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    javaBuilder.sourceWindow(sourceWindow);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(operation);
    out.writeCard16((short) getLength());
    out.writeCard8(destinationKind);
    out.writeCard8(sourceKind);
    out.writePad(1);
    out.writeCard32(destinationWindow);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
    out.writeCard32(sourceWindow);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 19;
  }

  public static class CombineBuilder {
    public Combine.CombineBuilder operation(So operation) {
      this.operation = (byte) operation.getValue();
      return this;
    }

    public Combine.CombineBuilder operation(byte operation) {
      this.operation = operation;
      return this;
    }

    public Combine.CombineBuilder destinationKind(Sk destinationKind) {
      this.destinationKind = (byte) destinationKind.getValue();
      return this;
    }

    public Combine.CombineBuilder destinationKind(byte destinationKind) {
      this.destinationKind = destinationKind;
      return this;
    }

    public Combine.CombineBuilder sourceKind(Sk sourceKind) {
      this.sourceKind = (byte) sourceKind.getValue();
      return this;
    }

    public Combine.CombineBuilder sourceKind(byte sourceKind) {
      this.sourceKind = sourceKind;
      return this;
    }

    public int getSize() {
      return 19;
    }
  }
}
