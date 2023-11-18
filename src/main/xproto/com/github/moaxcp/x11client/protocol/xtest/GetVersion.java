package com.github.moaxcp.x11client.protocol.xtest;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetVersion implements TwoWayRequest<GetVersionReply>, XtestObject {
  public static final byte OPCODE = 0;

  private byte majorVersion;

  private short minorVersion;

  public XReplyFunction<GetVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetVersionReply.readGetVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetVersion readGetVersion(X11Input in) throws IOException {
    GetVersion.GetVersionBuilder javaBuilder = GetVersion.builder();
    byte majorVersion = in.readCard8();
    short length = in.readCard16();
    byte[] pad3 = in.readPad(1);
    short minorVersion = in.readCard16();
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(majorVersion);
    out.writeCard16((short) getLength());
    out.writePad(1);
    out.writeCard16(minorVersion);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7;
  }

  public static class GetVersionBuilder {
    public int getSize() {
      return 7;
    }
  }
}
