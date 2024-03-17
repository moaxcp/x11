package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetAtomNameReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private List<Byte> name;

  public static GetAtomNameReply readGetAtomNameReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetAtomNameReply.GetAtomNameReplyBuilder javaBuilder = GetAtomNameReply.builder();
    int length = in.readCard32();
    short nameLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.name(name);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writePad(22);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetAtomNameReplyBuilder {
    public int getSize() {
      return 32 + 1 * name.size();
    }
  }
}
