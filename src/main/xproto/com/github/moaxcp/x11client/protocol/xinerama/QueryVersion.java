package com.github.moaxcp.x11client.protocol.xinerama;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryVersion implements TwoWayRequest<QueryVersionReply>, XineramaObject {
  public static final byte OPCODE = 0;

  private byte major;

  private byte minor;

  public XReplyFunction<QueryVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryVersionReply.readQueryVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryVersion readQueryVersion(X11Input in) throws IOException {
    QueryVersion.QueryVersionBuilder javaBuilder = QueryVersion.builder();
    byte major = in.readCard8();
    short length = in.readCard16();
    byte minor = in.readCard8();
    javaBuilder.major(major);
    javaBuilder.minor(minor);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(major);
    out.writeCard16((short) getLength());
    out.writeCard8(minor);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 5;
  }

  public static class QueryVersionBuilder {
    public int getSize() {
      return 5;
    }
  }
}
