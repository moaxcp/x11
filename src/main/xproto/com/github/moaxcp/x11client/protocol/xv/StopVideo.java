package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StopVideo implements OneWayRequest, XvObject {
  public static final byte OPCODE = 9;

  private int port;

  private int drawable;

  public byte getOpCode() {
    return OPCODE;
  }

  public static StopVideo readStopVideo(X11Input in) throws IOException {
    StopVideo.StopVideoBuilder javaBuilder = StopVideo.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int port = in.readCard32();
    int drawable = in.readCard32();
    javaBuilder.port(port);
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class StopVideoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
