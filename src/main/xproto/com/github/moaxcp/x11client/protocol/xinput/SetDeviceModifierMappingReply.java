package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import com.github.moaxcp.x11client.protocol.xproto.MappingStatus;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetDeviceModifierMappingReply implements XReply, XinputObject {
  private byte xiReplyType;

  private short sequenceNumber;

  private byte status;

  public static SetDeviceModifierMappingReply readSetDeviceModifierMappingReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    SetDeviceModifierMappingReply.SetDeviceModifierMappingReplyBuilder javaBuilder = SetDeviceModifierMappingReply.builder();
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

  public static class SetDeviceModifierMappingReplyBuilder {
    public SetDeviceModifierMappingReply.SetDeviceModifierMappingReplyBuilder status(
        MappingStatus status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public SetDeviceModifierMappingReply.SetDeviceModifierMappingReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
