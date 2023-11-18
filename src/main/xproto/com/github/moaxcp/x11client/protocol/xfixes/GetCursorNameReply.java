package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetCursorNameReply implements XReply, XfixesObject {
  private short sequenceNumber;

  private int atom;

  @NonNull
  private List<Byte> name;

  public static GetCursorNameReply readGetCursorNameReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetCursorNameReply.GetCursorNameReplyBuilder javaBuilder = GetCursorNameReply.builder();
    int length = in.readCard32();
    int atom = in.readCard32();
    short nbytes = in.readCard16();
    byte[] pad6 = in.readPad(18);
    List<Byte> name = in.readChar(Short.toUnsignedInt(nbytes));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.atom(atom);
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
    out.writeCard32(atom);
    short nbytes = (short) name.size();
    out.writeCard16(nbytes);
    out.writePad(18);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * name.size();
  }

  public static class GetCursorNameReplyBuilder {
    public int getSize() {
      return 32 + 1 * name.size();
    }
  }
}
