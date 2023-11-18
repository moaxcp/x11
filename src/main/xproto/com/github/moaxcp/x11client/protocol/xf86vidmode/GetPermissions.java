package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetPermissions implements TwoWayRequest<GetPermissionsReply>, Xf86vidmodeObject {
  public static final byte OPCODE = 20;

  private short screen;

  public XReplyFunction<GetPermissionsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPermissionsReply.readGetPermissionsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPermissions readGetPermissions(X11Input in) throws IOException {
    GetPermissions.GetPermissionsBuilder javaBuilder = GetPermissions.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short screen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetPermissionsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
