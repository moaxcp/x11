package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SALockPtrBtn implements ActionUnion, XStruct, XkbObject {
  private byte type;

  private byte flags;

  private byte button;

  public static SALockPtrBtn readSALockPtrBtn(X11Input in) throws IOException {
    SALockPtrBtn.SALockPtrBtnBuilder javaBuilder = SALockPtrBtn.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte[] pad2 = in.readPad(1);
    byte button = in.readCard8();
    byte[] pad4 = in.readPad(4);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.button(button);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writePad(1);
    out.writeCard8(button);
    out.writePad(4);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class SALockPtrBtnBuilder {
    public SALockPtrBtn.SALockPtrBtnBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SALockPtrBtn.SALockPtrBtnBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
