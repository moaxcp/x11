package com.github.moaxcp.x11client.protocol.glx;

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
public class GetStringReply implements XReply, GlxObject {
  private short sequenceNumber;

  @NonNull
  private List<Byte> string;

  public static GetStringReply readGetStringReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetStringReply.GetStringReplyBuilder javaBuilder = GetStringReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(4);
    int n = in.readCard32();
    byte[] pad6 = in.readPad(16);
    List<Byte> string = in.readChar((int) (Integer.toUnsignedLong(n)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.string(string);
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
    out.writePad(4);
    int n = string.size();
    out.writeCard32(n);
    out.writePad(16);
    out.writeChar(string);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * string.size();
  }

  public static class GetStringReplyBuilder {
    public int getSize() {
      return 32 + 1 * string.size();
    }
  }
}
