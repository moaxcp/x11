package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SAPtrBtn implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte flags;

  private byte count;

  private byte button;

  public static SAPtrBtn readSAPtrBtn(X11Input in) throws IOException {
    SAPtrBtn.SAPtrBtnBuilder javaBuilder = SAPtrBtn.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte count = in.readCard8();
    byte button = in.readCard8();
    byte[] pad4 = in.readPad(4);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.count(count);
    javaBuilder.button(button);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(count);
    out.writeCard8(button);
    out.writePad(4);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SAPtrBtnBuilder {
    public SAPtrBtn.SAPtrBtnBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SAPtrBtn.SAPtrBtnBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
