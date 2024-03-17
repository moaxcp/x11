package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateWindow implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 31;

  private int screen;

  private int fbconfig;

  private int window;

  private int glxWindow;

  @NonNull
  private List<Integer> attribs;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateWindow readCreateWindow(X11Input in) throws IOException {
    CreateWindow.CreateWindowBuilder javaBuilder = CreateWindow.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int screen = in.readCard32();
    int fbconfig = in.readCard32();
    int window = in.readCard32();
    int glxWindow = in.readCard32();
    int numAttribs = in.readCard32();
    List<Integer> attribs = in.readCard32((int) (Integer.toUnsignedLong(numAttribs) * 2));
    javaBuilder.screen(screen);
    javaBuilder.fbconfig(fbconfig);
    javaBuilder.window(window);
    javaBuilder.glxWindow(glxWindow);
    javaBuilder.attribs(attribs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
    out.writeCard32(fbconfig);
    out.writeCard32(window);
    out.writeCard32(glxWindow);
    int numAttribs = attribs.size();
    out.writeCard32(numAttribs);
    out.writeCard32(attribs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 4 * attribs.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateWindowBuilder {
    public int getSize() {
      return 24 + 4 * attribs.size();
    }
  }
}
