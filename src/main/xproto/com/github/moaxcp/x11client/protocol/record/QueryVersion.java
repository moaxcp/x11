package com.github.moaxcp.x11client.protocol.record;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryVersion implements TwoWayRequest<QueryVersionReply>, RecordObject {
  public static final byte OPCODE = 0;

  private short majorVersion;

  private short minorVersion;

  public XReplyFunction<QueryVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryVersionReply.readQueryVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryVersion readQueryVersion(X11Input in) throws IOException {
    QueryVersion.QueryVersionBuilder javaBuilder = QueryVersion.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short majorVersion = in.readCard16();
    short minorVersion = in.readCard16();
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(majorVersion);
    out.writeCard16(minorVersion);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class QueryVersionBuilder {
    public int getSize() {
      return 8;
    }
  }
}
