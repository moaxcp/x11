package com.github.moaxcp.x11client.protocol.xselinux;

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
public class GetSelectionContextReply implements XReply, XselinuxObject {
  private short sequenceNumber;

  @NonNull
  private List<Byte> context;

  public static GetSelectionContextReply readGetSelectionContextReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetSelectionContextReply.GetSelectionContextReplyBuilder javaBuilder = GetSelectionContextReply.builder();
    int length = in.readCard32();
    int contextLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Byte> context = in.readChar((int) (Integer.toUnsignedLong(contextLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.context(context);
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
    int contextLen = context.size();
    out.writeCard32(contextLen);
    out.writePad(20);
    out.writeChar(context);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * context.size();
  }

  public static class GetSelectionContextReplyBuilder {
    public int getSize() {
      return 32 + 1 * context.size();
    }
  }
}
