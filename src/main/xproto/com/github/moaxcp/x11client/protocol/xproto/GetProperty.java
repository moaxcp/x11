package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetProperty implements TwoWayRequest<GetPropertyReply>, XprotoObject {
  public static final byte OPCODE = 20;

  private boolean delete;

  private int window;

  private int property;

  private int type;

  private int longOffset;

  private int longLength;

  public XReplyFunction<GetPropertyReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPropertyReply.readGetPropertyReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetProperty readGetProperty(X11Input in) throws IOException {
    GetProperty.GetPropertyBuilder javaBuilder = GetProperty.builder();
    boolean delete = in.readBool();
    short length = in.readCard16();
    int window = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    int longOffset = in.readCard32();
    int longLength = in.readCard32();
    javaBuilder.delete(delete);
    javaBuilder.window(window);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.longOffset(longOffset);
    javaBuilder.longLength(longLength);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(delete);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard32(longOffset);
    out.writeCard32(longLength);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class GetPropertyBuilder {
    public int getSize() {
      return 24;
    }
  }
}
