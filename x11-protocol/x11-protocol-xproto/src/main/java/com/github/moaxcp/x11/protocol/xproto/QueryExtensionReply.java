package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryExtensionReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  private boolean present;

  private byte majorOpcode;

  private byte firstEvent;

  private byte firstError;

  public static QueryExtensionReply readQueryExtensionReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryExtensionReply.QueryExtensionReplyBuilder javaBuilder = QueryExtensionReply.builder();
    int length = in.readCard32();
    boolean present = in.readBool();
    byte majorOpcode = in.readCard8();
    byte firstEvent = in.readCard8();
    byte firstError = in.readCard8();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.present(present);
    javaBuilder.majorOpcode(majorOpcode);
    javaBuilder.firstEvent(firstEvent);
    javaBuilder.firstError(firstError);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(present);
    out.writeCard8(majorOpcode);
    out.writeCard8(firstEvent);
    out.writeCard8(firstError);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryExtensionReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
