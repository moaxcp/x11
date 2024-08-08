package com.github.moaxcp.x11.protocol.xc_misc;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class GetXIDListReply implements XReply {
  public static final String PLUGIN_NAME = "xc_misc";

  private short sequenceNumber;

  @NonNull
  private IntList ids;

  public static GetXIDListReply readGetXIDListReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetXIDListReply.GetXIDListReplyBuilder javaBuilder = GetXIDListReply.builder();
    int length = in.readCard32();
    int idsLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    IntList ids = in.readCard32((int) (Integer.toUnsignedLong(idsLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.ids(ids.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetXIDListReplyBuilder {
    public int getSize() {
      return 32 + 4 * ids.size();
    }
  }
}
