package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintEndPage implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 14;

  private boolean cancel;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintEndPage readPrintEndPage(X11Input in) throws IOException {
    PrintEndPage.PrintEndPageBuilder javaBuilder = PrintEndPage.builder();
    boolean cancel = in.readBool();
    short length = in.readCard16();
    byte[] pad3 = in.readPad(3);
    javaBuilder.cancel(cancel);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(cancel);
    out.writeCard16((short) getLength());
    out.writePad(3);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7;
  }

  public static class PrintEndPageBuilder {
    public int getSize() {
      return 7;
    }
  }
}
