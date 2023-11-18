package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintDestroyContext implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 5;

  private int context;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintDestroyContext readPrintDestroyContext(X11Input in) throws IOException {
    PrintDestroyContext.PrintDestroyContextBuilder javaBuilder = PrintDestroyContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int context = in.readCard32();
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class PrintDestroyContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
