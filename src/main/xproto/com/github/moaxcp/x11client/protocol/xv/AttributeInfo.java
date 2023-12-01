package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AttributeInfo implements XStruct, XvObject {
  private int flags;

  private int min;

  private int max;

  @NonNull
  private List<Byte> name;

  public static AttributeInfo readAttributeInfo(X11Input in) throws IOException {
    AttributeInfo.AttributeInfoBuilder javaBuilder = AttributeInfo.builder();
    int flags = in.readCard32();
    int min = in.readInt32();
    int max = in.readInt32();
    int size = in.readCard32();
    List<Byte> name = in.readChar((int) (Integer.toUnsignedLong(size)));
    in.readPadAlign((int) (Integer.toUnsignedLong(size)));
    javaBuilder.flags(flags);
    javaBuilder.min(min);
    javaBuilder.max(max);
    javaBuilder.name(name);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(flags);
    out.writeInt32(min);
    out.writeInt32(max);
    int size = name.size();
    out.writeCard32(size);
    out.writeChar(name);
    out.writePadAlign((int) (Integer.toUnsignedLong(size)));
  }

  public boolean isFlagsEnabled(@NonNull AttributeFlag... maskEnums) {
    for(AttributeFlag m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size());
  }

  public static class AttributeInfoBuilder {
    public boolean isFlagsEnabled(@NonNull AttributeFlag... maskEnums) {
      for(AttributeFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public AttributeInfo.AttributeInfoBuilder flagsEnable(AttributeFlag... maskEnums) {
      for(AttributeFlag m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public AttributeInfo.AttributeInfoBuilder flagsDisable(AttributeFlag... maskEnums) {
      for(AttributeFlag m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 16 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size());
    }
  }
}
