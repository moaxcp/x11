package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GrabPortReply implements XReply {
  public static final String PLUGIN_NAME = "xv";

  private byte result;

  private short sequenceNumber;

  public static GrabPortReply readGrabPortReply(byte result, short sequenceNumber, X11Input in)
      throws IOException {
    GrabPortReply.GrabPortReplyBuilder javaBuilder = GrabPortReply.builder();
    int length = in.readCard32();
    in.readPad(24);
    javaBuilder.result(result);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(result);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GrabPortReplyBuilder {
    public GrabPortReply.GrabPortReplyBuilder result(GrabPortStatus result) {
      this.result = (byte) result.getValue();
      return this;
    }

    public GrabPortReply.GrabPortReplyBuilder result(byte result) {
      this.result = result;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
