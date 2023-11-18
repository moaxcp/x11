package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CirculateWindow implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 13;

  private byte direction;

  private int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CirculateWindow readCirculateWindow(X11Input in) throws IOException {
    CirculateWindow.CirculateWindowBuilder javaBuilder = CirculateWindow.builder();
    byte direction = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.direction(direction);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(direction);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class CirculateWindowBuilder {
    public CirculateWindow.CirculateWindowBuilder direction(Circulate direction) {
      this.direction = (byte) direction.getValue();
      return this;
    }

    public CirculateWindow.CirculateWindowBuilder direction(byte direction) {
      this.direction = direction;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
