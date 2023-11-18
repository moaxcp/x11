package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintStartJob implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 7;

  private byte outputMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintStartJob readPrintStartJob(X11Input in) throws IOException {
    PrintStartJob.PrintStartJobBuilder javaBuilder = PrintStartJob.builder();
    byte outputMode = in.readCard8();
    short length = in.readCard16();
    javaBuilder.outputMode(outputMode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(outputMode);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class PrintStartJobBuilder {
    public int getSize() {
      return 4;
    }
  }
}
