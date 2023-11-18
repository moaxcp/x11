package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.shape.Sk;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRegionFromWindow implements OneWayRequest, XfixesObject {
  public static final byte OPCODE = 7;

  private int region;

  private int window;

  private byte kind;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromWindow readCreateRegionFromWindow(X11Input in) throws IOException {
    CreateRegionFromWindow.CreateRegionFromWindowBuilder javaBuilder = CreateRegionFromWindow.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int region = in.readCard32();
    int window = in.readCard32();
    byte kind = in.readCard8();
    byte[] pad6 = in.readPad(3);
    javaBuilder.region(region);
    javaBuilder.window(window);
    javaBuilder.kind(kind);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(window);
    out.writeCard8(kind);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class CreateRegionFromWindowBuilder {
    public CreateRegionFromWindow.CreateRegionFromWindowBuilder kind(Sk kind) {
      this.kind = (byte) kind.getValue();
      return this;
    }

    public CreateRegionFromWindow.CreateRegionFromWindowBuilder kind(byte kind) {
      this.kind = kind;
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
