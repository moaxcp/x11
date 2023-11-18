package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Authenticate implements TwoWayRequest<AuthenticateReply>, Dri2Object {
  public static final byte OPCODE = 2;

  private int window;

  private int magic;

  public XReplyFunction<AuthenticateReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> AuthenticateReply.readAuthenticateReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Authenticate readAuthenticate(X11Input in) throws IOException {
    Authenticate.AuthenticateBuilder javaBuilder = Authenticate.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int magic = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.magic(magic);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(magic);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class AuthenticateBuilder {
    public int getSize() {
      return 12;
    }
  }
}
