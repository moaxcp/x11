package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListDeviceProperties implements TwoWayRequest<ListDevicePropertiesReply>, XinputObject {
  public static final byte OPCODE = 36;

  private byte deviceId;

  public XReplyFunction<ListDevicePropertiesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListDevicePropertiesReply.readListDevicePropertiesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListDeviceProperties readListDeviceProperties(X11Input in) throws IOException {
    ListDeviceProperties.ListDevicePropertiesBuilder javaBuilder = ListDeviceProperties.builder();
    byte deviceId = in.readCard8();
    short length = in.readCard16();
    byte[] pad3 = in.readPad(3);
    javaBuilder.deviceId(deviceId);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(deviceId);
    out.writeCard16((short) getLength());
    out.writePad(3);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7;
  }

  public static class ListDevicePropertiesBuilder {
    public int getSize() {
      return 7;
    }
  }
}
