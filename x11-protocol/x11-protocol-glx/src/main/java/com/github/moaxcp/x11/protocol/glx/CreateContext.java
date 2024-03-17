package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateContext implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 3;

  private int context;

  private int visual;

  private int screen;

  private int shareList;

  private boolean direct;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateContext readCreateContext(X11Input in) throws IOException {
    CreateContext.CreateContextBuilder javaBuilder = CreateContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    int visual = in.readCard32();
    int screen = in.readCard32();
    int shareList = in.readCard32();
    boolean direct = in.readBool();
    byte[] pad8 = in.readPad(3);
    javaBuilder.context(context);
    javaBuilder.visual(visual);
    javaBuilder.screen(screen);
    javaBuilder.shareList(shareList);
    javaBuilder.direct(direct);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    out.writeCard32(visual);
    out.writeCard32(screen);
    out.writeCard32(shareList);
    out.writeBool(direct);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateContextBuilder {
    public int getSize() {
      return 24;
    }
  }
}
