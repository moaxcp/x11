package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SANoAction implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  public static SANoAction readSANoAction(X11Input in) throws IOException {
    SANoAction.SANoActionBuilder javaBuilder = SANoAction.builder();
    byte type = in.readCard8();
    byte[] pad1 = in.readPad(7);
    javaBuilder.type(type);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writePad(7);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SANoActionBuilder {
    public SANoAction.SANoActionBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SANoAction.SANoActionBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
