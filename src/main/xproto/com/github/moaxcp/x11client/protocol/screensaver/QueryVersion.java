package com.github.moaxcp.x11client.protocol.screensaver;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryVersion implements TwoWayRequest<QueryVersionReply>, ScreensaverObject {
  public static final byte OPCODE = 0;

  private byte clientMajorVersion;

  private byte clientMinorVersion;

  public XReplyFunction<QueryVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryVersionReply.readQueryVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryVersion readQueryVersion(X11Input in) throws IOException {
    QueryVersion.QueryVersionBuilder javaBuilder = QueryVersion.builder();
    byte clientMajorVersion = in.readCard8();
    short length = in.readCard16();
    byte clientMinorVersion = in.readCard8();
    byte[] pad4 = in.readPad(2);
    javaBuilder.clientMajorVersion(clientMajorVersion);
    javaBuilder.clientMinorVersion(clientMinorVersion);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(clientMajorVersion);
    out.writeCard16((short) getLength());
    out.writeCard8(clientMinorVersion);
    out.writePad(2);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7;
  }

  public static class QueryVersionBuilder {
    public int getSize() {
      return 7;
    }
  }
}
