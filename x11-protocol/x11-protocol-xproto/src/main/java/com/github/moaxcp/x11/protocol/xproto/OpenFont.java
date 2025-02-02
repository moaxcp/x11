package com.github.moaxcp.x11.protocol.xproto;

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
public class OpenFont implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 45;

  private int fid;

  @NonNull
  private ByteList name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static OpenFont readOpenFont(X11Input in) throws IOException {
    OpenFont.OpenFontBuilder javaBuilder = OpenFont.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int fid = in.readCard32();
    short nameLen = in.readCard16();
    byte[] pad5 = in.readPad(2);
    ByteList name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.fid(fid);
    javaBuilder.name(name.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(fid);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
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

  public static class OpenFontBuilder {
    public int getSize() {
      return 12 + 1 * name.size();
    }
  }
}
