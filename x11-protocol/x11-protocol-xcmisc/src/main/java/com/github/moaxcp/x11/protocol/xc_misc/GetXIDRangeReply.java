package com.github.moaxcp.x11.protocol.xc_misc;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetXIDRangeReply implements XReply {
  public static final String PLUGIN_NAME = "xc_misc";

  private short sequenceNumber;

  private int startId;

  private int count;

  public static GetXIDRangeReply readGetXIDRangeReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetXIDRangeReply.GetXIDRangeReplyBuilder javaBuilder = GetXIDRangeReply.builder();
    int length = in.readCard32();
    int startId = in.readCard32();
    int count = in.readCard32();
    in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.startId(startId);
    javaBuilder.count(count);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(startId);
    out.writeCard32(count);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetXIDRangeReplyBuilder {
    public int getSize() {
      return 16;
    }
  }
}
