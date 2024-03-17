package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetCrtcConfigReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private byte status;

  private short sequenceNumber;

  private int timestamp;

  public static SetCrtcConfigReply readSetCrtcConfigReply(byte status, short sequenceNumber,
      X11Input in) throws IOException {
    SetCrtcConfigReply.SetCrtcConfigReplyBuilder javaBuilder = SetCrtcConfigReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetCrtcConfigReplyBuilder {
    public SetCrtcConfigReply.SetCrtcConfigReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public SetCrtcConfigReply.SetCrtcConfigReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
