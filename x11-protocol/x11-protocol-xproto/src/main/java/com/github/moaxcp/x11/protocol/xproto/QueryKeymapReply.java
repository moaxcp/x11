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
public class QueryKeymapReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private List<Byte> keys;

  public static QueryKeymapReply readQueryKeymapReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    QueryKeymapReply.QueryKeymapReplyBuilder javaBuilder = QueryKeymapReply.builder();
    int length = in.readCard32();
    List<Byte> keys = in.readCard8(32);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keys(keys);
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
    out.writeCard8(keys);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * keys.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryKeymapReplyBuilder {
    public int getSize() {
      return 8 + 1 * keys.size();
    }
  }
}
