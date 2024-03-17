package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetOutputPrimary implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 30;

  private int window;

  private int output;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetOutputPrimary readSetOutputPrimary(X11Input in) throws IOException {
    SetOutputPrimary.SetOutputPrimaryBuilder javaBuilder = SetOutputPrimary.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int output = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.output(output);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(output);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetOutputPrimaryBuilder {
    public int getSize() {
      return 12;
    }
  }
}
