package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetFontPath implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 51;

  @NonNull
  private List<Str> font;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetFontPath readSetFontPath(X11Input in) throws IOException {
    SetFontPath.SetFontPathBuilder javaBuilder = SetFontPath.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short fontQty = in.readCard16();
    byte[] pad4 = in.readPad(2);
    List<Str> font = new ArrayList<>(Short.toUnsignedInt(fontQty));
    for(int i = 0; i < Short.toUnsignedInt(fontQty); i++) {
      font.add(Str.readStr(in));
    }
    javaBuilder.font(font);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    short fontQty = (short) font.size();
    out.writeCard16(fontQty);
    out.writePad(2);
    for(Str t : font) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(font);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetFontPathBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(font);
    }
  }
}
