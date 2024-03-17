package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.MappingStatus;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetDeviceButtonMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  private byte status;

  public static SetDeviceButtonMappingReply readSetDeviceButtonMappingReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    SetDeviceButtonMappingReply.SetDeviceButtonMappingReplyBuilder javaBuilder = SetDeviceButtonMappingReply.builder();
    int length = in.readCard32();
    byte status = in.readCard8();
    byte[] pad5 = in.readPad(23);
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.status(status);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard8(status);
    out.writePad(23);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetDeviceButtonMappingReplyBuilder {
    public SetDeviceButtonMappingReply.SetDeviceButtonMappingReplyBuilder status(
        MappingStatus status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public SetDeviceButtonMappingReply.SetDeviceButtonMappingReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
