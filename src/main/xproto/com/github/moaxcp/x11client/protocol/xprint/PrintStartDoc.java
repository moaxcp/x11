package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintStartDoc implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 9;

  private byte driverMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintStartDoc readPrintStartDoc(X11Input in) throws IOException {
    PrintStartDoc.PrintStartDocBuilder javaBuilder = PrintStartDoc.builder();
    byte driverMode = in.readCard8();
    short length = in.readCard16();
    javaBuilder.driverMode(driverMode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(driverMode);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class PrintStartDocBuilder {
    public int getSize() {
      return 4;
    }
  }
}
