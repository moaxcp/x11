package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SATerminate implements ActionUnion, XStruct, XkbObject {
  private byte type;

  public static SATerminate readSATerminate(X11Input in) throws IOException {
    SATerminate.SATerminateBuilder javaBuilder = SATerminate.builder();
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

  public static class SATerminateBuilder {
    public SATerminate.SATerminateBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SATerminate.SATerminateBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
