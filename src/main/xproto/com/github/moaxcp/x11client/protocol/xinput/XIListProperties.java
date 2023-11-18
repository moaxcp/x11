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
public class XIListProperties implements TwoWayRequest<XIListPropertiesReply>, XinputObject {
  public static final byte OPCODE = 56;

  private short deviceid;

  public XReplyFunction<XIListPropertiesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIListPropertiesReply.readXIListPropertiesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIListProperties readXIListProperties(X11Input in) throws IOException {
    XIListProperties.XIListPropertiesBuilder javaBuilder = XIListProperties.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceid = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class XIListPropertiesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
