package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetScreenSize implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 7;

  private int window;

  private short width;

  private short height;

  private int mmWidth;

  private int mmHeight;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetScreenSize readSetScreenSize(X11Input in) throws IOException {
    SetScreenSize.SetScreenSizeBuilder javaBuilder = SetScreenSize.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    int mmWidth = in.readCard32();
    int mmHeight = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.mmWidth(mmWidth);
    javaBuilder.mmHeight(mmHeight);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(mmWidth);
    out.writeCard32(mmHeight);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetScreenSizeBuilder {
    public int getSize() {
      return 20;
    }
  }
}
