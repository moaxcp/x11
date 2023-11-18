package com.github.moaxcp.x11client.protocol.xc_misc;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetVersion implements TwoWayRequest<GetVersionReply>, XcMiscObject {
  public static final byte OPCODE = 0;

  private short clientMajorVersion;

  private short clientMinorVersion;

  public XReplyFunction<GetVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetVersionReply.readGetVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetVersion readGetVersion(X11Input in) throws IOException {
    GetVersion.GetVersionBuilder javaBuilder = GetVersion.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short clientMajorVersion = in.readCard16();
    short clientMinorVersion = in.readCard16();
    javaBuilder.clientMajorVersion(clientMajorVersion);
    javaBuilder.clientMinorVersion(clientMinorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(clientMajorVersion);
    out.writeCard16(clientMinorVersion);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetVersionBuilder {
    public int getSize() {
      return 8;
    }
  }
}
