package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetCrtcGamma implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 24;

  private int crtc;

  @NonNull
  private List<Short> red;

  @NonNull
  private List<Short> green;

  @NonNull
  private List<Short> blue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCrtcGamma readSetCrtcGamma(X11Input in) throws IOException {
    SetCrtcGamma.SetCrtcGammaBuilder javaBuilder = SetCrtcGamma.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int crtc = in.readCard32();
    short size = in.readCard16();
    byte[] pad5 = in.readPad(2);
    List<Short> red = in.readCard16(Short.toUnsignedInt(size));
    List<Short> green = in.readCard16(Short.toUnsignedInt(size));
    List<Short> blue = in.readCard16(Short.toUnsignedInt(size));
    javaBuilder.crtc(crtc);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    short size = (short) blue.size();
    out.writeCard16(size);
    out.writePad(2);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
  }

  public static class SetCrtcGammaBuilder {
    public int getSize() {
      return 12 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
    }
  }
}
