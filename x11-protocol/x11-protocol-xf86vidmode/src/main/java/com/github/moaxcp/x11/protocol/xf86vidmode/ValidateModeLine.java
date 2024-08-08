package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class ValidateModeLine implements TwoWayRequest<ValidateModeLineReply> {
  public static final String PLUGIN_NAME = "xf86vidmode";

  public static final byte OPCODE = 9;

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

  @NonNull
  private ByteList xPrivate;

  public XReplyFunction<ValidateModeLineReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ValidateModeLineReply.readValidateModeLineReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ValidateModeLine readValidateModeLine(X11Input in) throws IOException {
    ValidateModeLine.ValidateModeLineBuilder javaBuilder = ValidateModeLine.builder();
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
    ByteList xPrivate = in.readCard8((int) (Integer.toUnsignedLong(privsize)));
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
    javaBuilder.xPrivate(xPrivate.toImmutable());
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

  public static class ValidateModeLineBuilder {
    public boolean isFlagsEnabled(@NonNull ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public ValidateModeLine.ValidateModeLineBuilder flagsEnable(ModeFlag... maskEnums) {
      for(ModeFlag m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public ValidateModeLine.ValidateModeLineBuilder flagsDisable(ModeFlag... maskEnums) {
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
