package com.github.moaxcp.x11client.protocol.composite;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RedirectWindow implements OneWayRequest, CompositeObject {
  public static final byte OPCODE = 1;

  private int window;

  private byte update;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RedirectWindow readRedirectWindow(X11Input in) throws IOException {
    RedirectWindow.RedirectWindowBuilder javaBuilder = RedirectWindow.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    byte update = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.window(window);
    javaBuilder.update(update);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard8(update);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class RedirectWindowBuilder {
    public RedirectWindow.RedirectWindowBuilder update(Redirect update) {
      this.update = (byte) update.getValue();
      return this;
    }

    public RedirectWindow.RedirectWindowBuilder update(byte update) {
      this.update = update;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
