package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SetCursorName implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 23;

  private int cursor;

  @NonNull
  private ByteList name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCursorName readSetCursorName(X11Input in) throws IOException {
    SetCursorName.SetCursorNameBuilder javaBuilder = SetCursorName.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int cursor = in.readCard32();
    short nbytes = in.readCard16();
    byte[] pad5 = in.readPad(2);
    ByteList name = in.readChar(Short.toUnsignedInt(nbytes));
    javaBuilder.cursor(cursor);
    javaBuilder.name(name.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(cursor);
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

  public static class SetCursorNameBuilder {
    public int getSize() {
      return 12 + 1 * name.size();
    }
  }
}
