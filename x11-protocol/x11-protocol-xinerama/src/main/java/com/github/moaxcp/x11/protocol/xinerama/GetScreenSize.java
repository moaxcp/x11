package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetScreenSize implements TwoWayRequest<GetScreenSizeReply> {
  public static final String PLUGIN_NAME = "xinerama";

  public static final byte OPCODE = 3;

  private int window;

  private int screen;

  public XReplyFunction<GetScreenSizeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetScreenSizeReply.readGetScreenSizeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetScreenSize readGetScreenSize(X11Input in) throws IOException {
    GetScreenSize.GetScreenSizeBuilder javaBuilder = GetScreenSize.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int screen = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(screen);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetScreenSizeBuilder {
    public int getSize() {
      return 12;
    }
  }
}
