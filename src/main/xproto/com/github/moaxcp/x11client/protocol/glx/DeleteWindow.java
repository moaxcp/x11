package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeleteWindow implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 32;

  private int glxwindow;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteWindow readDeleteWindow(X11Input in) throws IOException {
    DeleteWindow.DeleteWindowBuilder javaBuilder = DeleteWindow.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int glxwindow = in.readCard32();
    javaBuilder.glxwindow(glxwindow);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(glxwindow);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DeleteWindowBuilder {
    public int getSize() {
      return 8;
    }
  }
}
