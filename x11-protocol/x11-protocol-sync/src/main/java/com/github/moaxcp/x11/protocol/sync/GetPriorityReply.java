package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetPriorityReply implements XReply {
  public static final String PLUGIN_NAME = "sync";

  private short sequenceNumber;

  private int priority;

  public static GetPriorityReply readGetPriorityReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetPriorityReply.GetPriorityReplyBuilder javaBuilder = GetPriorityReply.builder();
    int length = in.readCard32();
    int priority = in.readInt32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.priority(priority);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt32(priority);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetPriorityReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
