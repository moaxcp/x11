package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintEndDoc implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 10;

  private boolean cancel;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintEndDoc readPrintEndDoc(X11Input in) throws IOException {
    PrintEndDoc.PrintEndDocBuilder javaBuilder = PrintEndDoc.builder();
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

  public static class PrintEndDocBuilder {
    public int getSize() {
      return 4;
    }
  }
}
