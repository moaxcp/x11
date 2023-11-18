package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SIAction implements XStruct, XkbObject {
  private byte type;

  @NonNull
  private List<Byte> data;

  public static SIAction readSIAction(X11Input in) throws IOException {
    SIAction.SIActionBuilder javaBuilder = SIAction.builder();
    byte type = in.readCard8();
    List<Byte> data = in.readCard8(7);
    javaBuilder.type(type);
    javaBuilder.data(data);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(data);
  }

  @Override
  public int getSize() {
    return 1 + 1 * data.size();
  }

  public static class SIActionBuilder {
    public SIAction.SIActionBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SIAction.SIActionBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 1 + 1 * data.size();
    }
  }
}
