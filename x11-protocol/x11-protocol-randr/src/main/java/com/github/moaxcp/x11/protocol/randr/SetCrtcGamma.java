package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ShortList;

@Value
@Builder
public class SetCrtcGamma implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 24;

  private int crtc;

  @NonNull
  private ShortList red;

  @NonNull
  private ShortList green;

  @NonNull
  private ShortList blue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCrtcGamma readSetCrtcGamma(X11Input in) throws IOException {
    SetCrtcGamma.SetCrtcGammaBuilder javaBuilder = SetCrtcGamma.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int crtc = in.readCard32();
    short size = in.readCard16();
    byte[] pad5 = in.readPad(2);
    ShortList red = in.readCard16(Short.toUnsignedInt(size));
    ShortList green = in.readCard16(Short.toUnsignedInt(size));
    ShortList blue = in.readCard16(Short.toUnsignedInt(size));
    javaBuilder.crtc(crtc);
    javaBuilder.red(red.toImmutable());
    javaBuilder.green(green.toImmutable());
    javaBuilder.blue(blue.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetCrtcGammaBuilder {
    public int getSize() {
      return 12 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
    }
  }
}
