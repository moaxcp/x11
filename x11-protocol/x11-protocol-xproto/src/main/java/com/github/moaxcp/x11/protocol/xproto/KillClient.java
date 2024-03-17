package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KillClient implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(resource);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KillClientBuilder {
    public int getSize() {
      return 8;
    }
  }
}
