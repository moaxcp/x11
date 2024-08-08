package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SIAction implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  @NonNull
  private ByteList data;

  public static SIAction readSIAction(X11Input in) throws IOException {
    SIAction.SIActionBuilder javaBuilder = SIAction.builder();
    byte type = in.readCard8();
    ByteList data = in.readCard8(7);
    javaBuilder.type(type);
    javaBuilder.data(data.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
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
