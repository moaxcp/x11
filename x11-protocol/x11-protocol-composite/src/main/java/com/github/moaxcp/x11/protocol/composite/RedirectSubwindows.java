package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RedirectSubwindows implements OneWayRequest {
  public static final String PLUGIN_NAME = "composite";

  public static final byte OPCODE = 2;

  private int window;

  private byte update;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RedirectSubwindows readRedirectSubwindows(X11Input in) throws IOException {
    RedirectSubwindows.RedirectSubwindowsBuilder javaBuilder = RedirectSubwindows.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    byte update = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.window(window);
    javaBuilder.update(update);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard8(update);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class RedirectSubwindowsBuilder {
    public RedirectSubwindows.RedirectSubwindowsBuilder update(Redirect update) {
      this.update = (byte) update.getValue();
      return this;
    }

    public RedirectSubwindows.RedirectSubwindowsBuilder update(byte update) {
      this.update = update;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
