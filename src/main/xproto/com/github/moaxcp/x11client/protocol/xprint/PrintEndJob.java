package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintEndJob implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 8;

  private boolean cancel;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintEndJob readPrintEndJob(X11Input in) throws IOException {
    PrintEndJob.PrintEndJobBuilder javaBuilder = PrintEndJob.builder();
    boolean cancel = in.readBool();
    short length = in.readCard16();
    javaBuilder.cancel(cancel);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(cancel);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class PrintEndJobBuilder {
    public int getSize() {
      return 4;
    }
  }
}
