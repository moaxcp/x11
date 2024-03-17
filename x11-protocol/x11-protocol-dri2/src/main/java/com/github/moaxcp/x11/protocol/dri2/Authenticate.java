package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Authenticate implements TwoWayRequest<AuthenticateReply> {
  public static final String PLUGIN_NAME = "dri2";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int magic = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.magic(magic);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(magic);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AuthenticateBuilder {
    public int getSize() {
      return 12;
    }
  }
}
