package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SASwitchScreen implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte flags;

  private byte newScreen;

  public static SASwitchScreen readSASwitchScreen(X11Input in) throws IOException {
    SASwitchScreen.SASwitchScreenBuilder javaBuilder = SASwitchScreen.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte newScreen = in.readInt8();
    byte[] pad3 = in.readPad(5);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.newScreen(newScreen);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeInt8(newScreen);
    out.writePad(5);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SASwitchScreenBuilder {
    public SASwitchScreen.SASwitchScreenBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASwitchScreen.SASwitchScreenBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
