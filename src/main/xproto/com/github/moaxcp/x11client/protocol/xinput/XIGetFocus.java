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
public class XIGetFocus implements TwoWayRequest<XIGetFocusReply>, XinputObject {
  public static final byte OPCODE = 50;

  private short deviceid;

  public XReplyFunction<XIGetFocusReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIGetFocusReply.readXIGetFocusReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIGetFocus readXIGetFocus(X11Input in) throws IOException {
    XIGetFocus.XIGetFocusBuilder javaBuilder = XIGetFocus.builder();
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

  public static class XIGetFocusBuilder {
    public int getSize() {
      return 8;
    }
  }
}
