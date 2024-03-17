package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SASetGroup implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte flags;

  private byte group;

  public static SASetGroup readSASetGroup(X11Input in) throws IOException {
    SASetGroup.SASetGroupBuilder javaBuilder = SASetGroup.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte group = in.readInt8();
    byte[] pad3 = in.readPad(5);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.group(group);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeInt8(group);
    out.writePad(5);
  }

  public boolean isFlagsEnabled(@NonNull Sa... maskEnums) {
    for(Sa m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SASetGroupBuilder {
    public SASetGroup.SASetGroupBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASetGroup.SASetGroupBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(@NonNull Sa... maskEnums) {
      for(Sa m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SASetGroup.SASetGroupBuilder flagsEnable(Sa... maskEnums) {
      for(Sa m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SASetGroup.SASetGroupBuilder flagsDisable(Sa... maskEnums) {
      for(Sa m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
