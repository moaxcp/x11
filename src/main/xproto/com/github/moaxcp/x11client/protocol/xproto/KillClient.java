package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KillClient implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 113;

  private int resource;

  public byte getOpCode() {
    return OPCODE;
  }

  public static KillClient readKillClient(X11Input in) throws IOException {
    KillClient.KillClientBuilder javaBuilder = KillClient.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int resource = in.readCard32();
    javaBuilder.resource(resource);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(resource);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class KillClientBuilder {
    public int getSize() {
      return 8;
    }
  }
}
