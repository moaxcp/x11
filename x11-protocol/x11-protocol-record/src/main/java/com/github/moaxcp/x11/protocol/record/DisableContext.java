package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DisableContext implements OneWayRequest {
  public static final String PLUGIN_NAME = "record";

  public static final byte OPCODE = 6;

  private int context;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DisableContext readDisableContext(X11Input in) throws IOException {
    DisableContext.DisableContextBuilder javaBuilder = DisableContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DisableContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
