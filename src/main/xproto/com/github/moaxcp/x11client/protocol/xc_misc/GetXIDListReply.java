package com.github.moaxcp.x11client.protocol.xc_misc;

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
public class GetXIDListReply implements XReply, XcMiscObject {
  private short sequenceNumber;

  @NonNull
  private List<Integer> ids;

  public static GetXIDListReply readGetXIDListReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetXIDListReply.GetXIDListReplyBuilder javaBuilder = GetXIDListReply.builder();
    int length = in.readCard32();
    int idsLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Integer> ids = in.readCard32((int) (Integer.toUnsignedLong(idsLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.ids(ids);
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
    int idsLen = ids.size();
    out.writeCard32(idsLen);
    out.writePad(20);
    out.writeCard32(ids);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * ids.size();
  }

  public static class GetXIDListReplyBuilder {
    public int getSize() {
      return 32 + 4 * ids.size();
    }
  }
}
