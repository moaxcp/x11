package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.shape.Sk;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetWindowShapeRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 21;

  private int dest;

  private byte destKind;

  private short xOffset;

  private short yOffset;

  private int region;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetWindowShapeRegion readSetWindowShapeRegion(X11Input in) throws IOException {
    SetWindowShapeRegion.SetWindowShapeRegionBuilder javaBuilder = SetWindowShapeRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int dest = in.readCard32();
    byte destKind = in.readCard8();
    byte[] pad5 = in.readPad(3);
    short xOffset = in.readInt16();
    short yOffset = in.readInt16();
    int region = in.readCard32();
    javaBuilder.dest(dest);
    javaBuilder.destKind(destKind);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    javaBuilder.region(region);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(dest);
    out.writeCard8(destKind);
    out.writePad(3);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
    out.writeCard32(region);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetWindowShapeRegionBuilder {
    public SetWindowShapeRegion.SetWindowShapeRegionBuilder destKind(Sk destKind) {
      this.destKind = (byte) destKind.getValue();
      return this;
    }

    public SetWindowShapeRegion.SetWindowShapeRegionBuilder destKind(byte destKind) {
      this.destKind = destKind;
      return this;
    }

    public int getSize() {
      return 20;
    }
  }
}
