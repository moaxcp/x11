package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetPointerMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private byte status;

  private short sequenceNumber;

  public static SetPointerMappingReply readSetPointerMappingReply(byte status, short sequenceNumber,
      X11Input in) throws IOException {
    SetPointerMappingReply.SetPointerMappingReplyBuilder javaBuilder = SetPointerMappingReply.builder();
    int length = in.readCard32();
    in.readPad(24);
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(status);
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

  public static class SetPointerMappingReplyBuilder {
    public SetPointerMappingReply.SetPointerMappingReplyBuilder status(MappingStatus status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public SetPointerMappingReply.SetPointerMappingReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
