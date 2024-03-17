package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AddModeLine implements OneWayRequest {
  public static final String PLUGIN_NAME = "xf86vidmode";

  public static final byte OPCODE = 7;

  private int screen;

  private int dotclock;

  private short hdisplay;

  private short hsyncstart;

  private short hsyncend;

  private short htotal;

  private short hskew;

  private short vdisplay;

  private short vsyncstart;

  private short vsyncend;

  private short vtotal;

  private int flags;

  private int afterDotclock;

  private short afterHdisplay;

  private short afterHsyncstart;

  private short afterHsyncend;

  private short afterHtotal;

  private short afterHskew;

  private short afterVdisplay;

  private short afterVsyncstart;

  private short afterVsyncend;

  private short afterVtotal;

  private int afterFlags;

  @NonNull
  private List<Byte> xPrivate;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AddModeLine readAddModeLine(X11Input in) throws IOException {
    AddModeLine.AddModeLineBuilder javaBuilder = AddModeLine.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int screen = in.readCard32();
    int dotclock = in.readCard32();
    short hdisplay = in.readCard16();
    short hsyncstart = in.readCard16();
    short hsyncend = in.readCard16();
    short htotal = in.readCard16();
    short hskew = in.readCard16();
    short vdisplay = in.readCard16();
    short vsyncstart = in.readCard16();
    short vsyncend = in.readCard16();
    short vtotal = in.readCard16();
    byte[] pad14 = in.readPad(2);
    int flags = in.readCard32();
    byte[] pad16 = in.readPad(12);
    int privsize = in.readCard32();
    int afterDotclock = in.readCard32();
    short afterHdisplay = in.readCard16();
    short afterHsyncstart = in.readCard16();
    short afterHsyncend = in.readCard16();
    short afterHtotal = in.readCard16();
    short afterHskew = in.readCard16();
    short afterVdisplay = in.readCard16();
    short afterVsyncstart = in.readCard16();
    short afterVsyncend = in.readCard16();
    short afterVtotal = in.readCard16();
    byte[] pad28 = in.readPad(2);
    int afterFlags = in.readCard32();
    byte[] pad30 = in.readPad(12);
    List<Byte> xPrivate = in.readCard8((int) (Integer.toUnsignedLong(privsize)));
    javaBuilder.screen(screen);
    javaBuilder.dotclock(dotclock);
    javaBuilder.hdisplay(hdisplay);
    javaBuilder.hsyncstart(hsyncstart);
    javaBuilder.hsyncend(hsyncend);
    javaBuilder.htotal(htotal);
    javaBuilder.hskew(hskew);
    javaBuilder.vdisplay(vdisplay);
    javaBuilder.vsyncstart(vsyncstart);
    javaBuilder.vsyncend(vsyncend);
    javaBuilder.vtotal(vtotal);
    javaBuilder.flags(flags);
    javaBuilder.afterDotclock(afterDotclock);
    javaBuilder.afterHdisplay(afterHdisplay);
    javaBuilder.afterHsyncstart(afterHsyncstart);
    javaBuilder.afterHsyncend(afterHsyncend);
    javaBuilder.afterHtotal(afterHtotal);
    javaBuilder.afterHskew(afterHskew);
    javaBuilder.afterVdisplay(afterVdisplay);
    javaBuilder.afterVsyncstart(afterVsyncstart);
    javaBuilder.afterVsyncend(afterVsyncend);
    javaBuilder.afterVtotal(afterVtotal);
    javaBuilder.afterFlags(afterFlags);
    javaBuilder.xPrivate(xPrivate);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
    out.writeCard32(dotclock);
    out.writeCard16(hdisplay);
    out.writeCard16(hsyncstart);
    out.writeCard16(hsyncend);
    out.writeCard16(htotal);
    out.writeCard16(hskew);
    out.writeCard16(vdisplay);
    out.writeCard16(vsyncstart);
    out.writeCard16(vsyncend);
    out.writeCard16(vtotal);
    out.writePad(2);
    out.writeCard32(flags);
    out.writePad(12);
    int privsize = xPrivate.size();
    out.writeCard32(privsize);
    out.writeCard32(afterDotclock);
    out.writeCard16(afterHdisplay);
    out.writeCard16(afterHsyncstart);
    out.writeCard16(afterHsyncend);
    out.writeCard16(afterHtotal);
    out.writeCard16(afterHskew);
    out.writeCard16(afterVdisplay);
    out.writeCard16(afterVsyncstart);
    out.writeCard16(afterVsyncend);
    out.writeCard16(afterVtotal);
    out.writePad(2);
    out.writeCard32(afterFlags);
    out.writePad(12);
    out.writeCard8(xPrivate);
    out.writePadAlign(getSize());
  }

  public boolean isFlagsEnabled(@NonNull ModeFlag... maskEnums) {
    for(ModeFlag m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAfterFlagsEnabled(@NonNull ModeFlag... maskEnums) {
    for(ModeFlag m : maskEnums) {
      if(!m.isEnabled(afterFlags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 92 + 1 * xPrivate.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AddModeLineBuilder {
    public boolean isFlagsEnabled(@NonNull ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public AddModeLine.AddModeLineBuilder flagsEnable(ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public AddModeLine.AddModeLineBuilder flagsDisable(ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public boolean isAfterFlagsEnabled(@NonNull ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        if(!m.isEnabled(afterFlags)) {
          return false;
        }
      }
      return true;
    }

    public AddModeLine.AddModeLineBuilder afterFlagsEnable(ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        afterFlags((int) m.enableFor(afterFlags));
      }
      return this;
    }

    public AddModeLine.AddModeLineBuilder afterFlagsDisable(ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        afterFlags((int) m.disableFor(afterFlags));
      }
      return this;
    }

    public int getSize() {
      return 92 + 1 * xPrivate.size();
    }
  }
}
