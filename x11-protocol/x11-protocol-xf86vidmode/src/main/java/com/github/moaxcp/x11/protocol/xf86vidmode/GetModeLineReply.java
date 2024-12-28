package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetModeLineReply implements XReply {
  public static final String PLUGIN_NAME = "xf86vidmode";

  private short sequenceNumber;

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

  @NonNull
  private List<Byte> xPrivate;

  public static GetModeLineReply readGetModeLineReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetModeLineReply.GetModeLineReplyBuilder javaBuilder = GetModeLineReply.builder();
    int length = in.readCard32();
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
    List<Byte> xPrivate = in.readCard8((int) (Integer.toUnsignedLong(privsize)));
    javaBuilder.sequenceNumber(sequenceNumber);
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
    javaBuilder.xPrivate(xPrivate);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
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

  @Override
  public int getSize() {
    return 52 + 1 * xPrivate.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetModeLineReplyBuilder {
    public boolean isFlagsEnabled(@NonNull ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public GetModeLineReply.GetModeLineReplyBuilder flagsEnable(ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public GetModeLineReply.GetModeLineReplyBuilder flagsDisable(ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 52 + 1 * xPrivate.size();
    }
  }
}
