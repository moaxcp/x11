package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SelectPortNotify implements OneWayRequest {
  public static final String PLUGIN_NAME = "xv";

  public static final byte OPCODE = 11;

  private int port;

  private boolean onoff;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectPortNotify readSelectPortNotify(X11Input in) throws IOException {
    SelectPortNotify.SelectPortNotifyBuilder javaBuilder = SelectPortNotify.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int port = in.readCard32();
    boolean onoff = in.readBool();
    byte[] pad5 = in.readPad(3);
    javaBuilder.port(port);
    javaBuilder.onoff(onoff);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeBool(onoff);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SelectPortNotifyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
