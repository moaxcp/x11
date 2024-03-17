package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InternAtomReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  private int atom;

  public static InternAtomReply readInternAtomReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    InternAtomReply.InternAtomReplyBuilder javaBuilder = InternAtomReply.builder();
    int length = in.readCard32();
    int atom = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.atom(atom);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(atom);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class InternAtomReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
