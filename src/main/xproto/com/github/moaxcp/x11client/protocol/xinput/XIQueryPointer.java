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
public class XIQueryPointer implements TwoWayRequest<XIQueryPointerReply>, XinputObject {
  public static final byte OPCODE = 40;

  private int window;

  private short deviceid;

  public XReplyFunction<XIQueryPointerReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIQueryPointerReply.readXIQueryPointerReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIQueryPointer readXIQueryPointer(X11Input in) throws IOException {
    XIQueryPointer.XIQueryPointerBuilder javaBuilder = XIQueryPointer.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    short deviceid = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.window(window);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class XIQueryPointerBuilder {
    public int getSize() {
      return 12;
    }
  }
}
