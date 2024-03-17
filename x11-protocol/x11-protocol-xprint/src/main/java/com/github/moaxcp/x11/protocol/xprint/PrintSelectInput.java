package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintSelectInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 15;

  private int context;

  private int eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintSelectInput readPrintSelectInput(X11Input in) throws IOException {
    PrintSelectInput.PrintSelectInputBuilder javaBuilder = PrintSelectInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    int eventMask = in.readCard32();
    javaBuilder.context(context);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    out.writeCard32(eventMask);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintSelectInputBuilder {
    public int getSize() {
      return 12;
    }
  }
}
