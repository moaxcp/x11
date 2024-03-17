package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintDestroyContext implements OneWayRequest {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 5;

  private int context;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintDestroyContext readPrintDestroyContext(X11Input in) throws IOException {
    PrintDestroyContext.PrintDestroyContextBuilder javaBuilder = PrintDestroyContext.builder();
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

  public static class PrintDestroyContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
