package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeCursorByName implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 27;

  private int src;

  @NonNull
  private List<Byte> name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeCursorByName readChangeCursorByName(X11Input in) throws IOException {
    ChangeCursorByName.ChangeCursorByNameBuilder javaBuilder = ChangeCursorByName.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int src = in.readCard32();
    short nbytes = in.readCard16();
    byte[] pad5 = in.readPad(2);
    List<Byte> name = in.readChar(Short.toUnsignedInt(nbytes));
    javaBuilder.src(src);
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(src);
    short nbytes = (short) name.size();
    out.writeCard16(nbytes);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeCursorByNameBuilder {
    public int getSize() {
      return 12 + 1 * name.size();
    }
  }
}
