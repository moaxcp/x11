package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintEndPage implements OneWayRequest {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 14;

  private boolean cancel;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintEndPage readPrintEndPage(X11Input in) throws IOException {
    PrintEndPage.PrintEndPageBuilder javaBuilder = PrintEndPage.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    boolean cancel = in.readBool();
    byte[] pad4 = in.readPad(3);
    javaBuilder.cancel(cancel);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeBool(cancel);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintEndPageBuilder {
    public int getSize() {
      return 8;
    }
  }
}
