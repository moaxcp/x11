package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintStartJob implements OneWayRequest {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 7;

  private byte outputMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintStartJob readPrintStartJob(X11Input in) throws IOException {
    PrintStartJob.PrintStartJobBuilder javaBuilder = PrintStartJob.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte outputMode = in.readCard8();
    javaBuilder.outputMode(outputMode);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(outputMode);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 5;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintStartJobBuilder {
    public int getSize() {
      return 5;
    }
  }
}
