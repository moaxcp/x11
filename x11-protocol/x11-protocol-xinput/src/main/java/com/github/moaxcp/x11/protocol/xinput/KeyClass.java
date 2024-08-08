package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class KeyClass implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short type;

  private short len;

  private short sourceid;

  @NonNull
  private IntList keys;

  public static KeyClass readKeyClass(X11Input in) throws IOException {
    KeyClass.KeyClassBuilder javaBuilder = KeyClass.builder();
    short type = in.readCard16();
    short len = in.readCard16();
    short sourceid = in.readCard16();
    short numKeys = in.readCard16();
    IntList keys = in.readCard32(Short.toUnsignedInt(numKeys));
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.sourceid(sourceid);
    javaBuilder.keys(keys.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    out.writeCard16(sourceid);
    short numKeys = (short) keys.size();
    out.writeCard16(numKeys);
    out.writeCard32(keys);
  }

  @Override
  public int getSize() {
    return 8 + 4 * keys.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KeyClassBuilder {
    public KeyClass.KeyClassBuilder type(DeviceClassType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public KeyClass.KeyClassBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8 + 4 * keys.size();
    }
  }
}
