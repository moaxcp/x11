package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ModeInfo implements XStruct, RandrObject {
  private int id;

  private short width;

  private short height;

  private int dotClock;

  private short hsyncStart;

  private short hsyncEnd;

  private short htotal;

  private short hskew;

  private short vsyncStart;

  private short vsyncEnd;

  private short vtotal;

  private short nameLen;

  private int modeFlags;

  public static ModeInfo readModeInfo(X11Input in) throws IOException {
    ModeInfo.ModeInfoBuilder javaBuilder = ModeInfo.builder();
    int id = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    int dotClock = in.readCard32();
    short hsyncStart = in.readCard16();
    short hsyncEnd = in.readCard16();
    short htotal = in.readCard16();
    short hskew = in.readCard16();
    short vsyncStart = in.readCard16();
    short vsyncEnd = in.readCard16();
    short vtotal = in.readCard16();
    short nameLen = in.readCard16();
    int modeFlags = in.readCard32();
    javaBuilder.id(id);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.dotClock(dotClock);
    javaBuilder.hsyncStart(hsyncStart);
    javaBuilder.hsyncEnd(hsyncEnd);
    javaBuilder.htotal(htotal);
    javaBuilder.hskew(hskew);
    javaBuilder.vsyncStart(vsyncStart);
    javaBuilder.vsyncEnd(vsyncEnd);
    javaBuilder.vtotal(vtotal);
    javaBuilder.nameLen(nameLen);
    javaBuilder.modeFlags(modeFlags);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(id);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(dotClock);
    out.writeCard16(hsyncStart);
    out.writeCard16(hsyncEnd);
    out.writeCard16(htotal);
    out.writeCard16(hskew);
    out.writeCard16(vsyncStart);
    out.writeCard16(vsyncEnd);
    out.writeCard16(vtotal);
    out.writeCard16(nameLen);
    out.writeCard32(modeFlags);
  }

  public boolean isModeFlagsEnabled(@NonNull ModeFlag... maskEnums) {
    for(ModeFlag m : maskEnums) {
      if(!m.isEnabled(modeFlags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class ModeInfoBuilder {
    public boolean isModeFlagsEnabled(@NonNull ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        if(!m.isEnabled(modeFlags)) {
          return false;
        }
      }
      return true;
    }

    public ModeInfo.ModeInfoBuilder modeFlagsEnable(ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        modeFlags((int) m.enableFor(modeFlags));
      }
      return this;
    }

    public ModeInfo.ModeInfoBuilder modeFlagsDisable(ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        modeFlags((int) m.disableFor(modeFlags));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
