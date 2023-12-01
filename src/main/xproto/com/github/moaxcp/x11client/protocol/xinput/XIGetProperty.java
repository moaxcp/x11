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
public class XIGetProperty implements TwoWayRequest<XIGetPropertyReply>, XinputObject {
  public static final byte OPCODE = 59;

  private short deviceid;

  private boolean delete;

  private int property;

  private int type;

  private int offset;

  private int len;

  public XReplyFunction<XIGetPropertyReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIGetPropertyReply.readXIGetPropertyReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIGetProperty readXIGetProperty(X11Input in) throws IOException {
    XIGetProperty.XIGetPropertyBuilder javaBuilder = XIGetProperty.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceid = in.readCard16();
    boolean delete = in.readBool();
    byte[] pad5 = in.readPad(1);
    int property = in.readCard32();
    int type = in.readCard32();
    int offset = in.readCard32();
    int len = in.readCard32();
    javaBuilder.deviceid(deviceid);
    javaBuilder.delete(delete);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.offset(offset);
    javaBuilder.len(len);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceid);
    out.writeBool(delete);
    out.writePad(1);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard32(offset);
    out.writeCard32(len);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class XIGetPropertyBuilder {
    public int getSize() {
      return 24;
    }
  }
}
