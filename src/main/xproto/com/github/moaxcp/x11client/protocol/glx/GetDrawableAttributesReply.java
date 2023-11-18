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
public class GetDrawableAttributesReply implements XReply, GlxObject {
  private short sequenceNumber;

  @NonNull
  private List<Integer> attribs;

  public static GetDrawableAttributesReply readGetDrawableAttributesReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetDrawableAttributesReply.GetDrawableAttributesReplyBuilder javaBuilder = GetDrawableAttributesReply.builder();
    int length = in.readCard32();
    int numAttribs = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Integer> attribs = in.readCard32((int) (Integer.toUnsignedLong(numAttribs) * 2));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.attribs(attribs);
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
    int numAttribs = attribs.size();
    out.writeCard32(numAttribs);
    out.writePad(20);
    out.writeCard32(attribs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * attribs.size();
  }

  public static class GetDrawableAttributesReplyBuilder {
    public int getSize() {
      return 32 + 4 * attribs.size();
    }
  }
}
