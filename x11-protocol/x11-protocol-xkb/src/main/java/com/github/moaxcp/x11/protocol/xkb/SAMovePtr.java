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
public class SAMovePtr implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte flags;

  private byte xHigh;

  private byte xLow;

  private byte yHigh;

  private byte yLow;

  public static SAMovePtr readSAMovePtr(X11Input in) throws IOException {
    SAMovePtr.SAMovePtrBuilder javaBuilder = SAMovePtr.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte xHigh = in.readInt8();
    byte xLow = in.readCard8();
    byte yHigh = in.readInt8();
    byte yLow = in.readCard8();
    byte[] pad6 = in.readPad(2);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.xHigh(xHigh);
    javaBuilder.xLow(xLow);
    javaBuilder.yHigh(yHigh);
    javaBuilder.yLow(yLow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeInt8(xHigh);
    out.writeCard8(xLow);
    out.writeInt8(yHigh);
    out.writeCard8(yLow);
    out.writePad(2);
  }

  public boolean isFlagsEnabled(@NonNull SAMovePtrFlag... maskEnums) {
    for(SAMovePtrFlag m : maskEnums) {
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

  public static class SAMovePtrBuilder {
    public SAMovePtr.SAMovePtrBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SAMovePtr.SAMovePtrBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(@NonNull SAMovePtrFlag... maskEnums) {
      for(SAMovePtrFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SAMovePtr.SAMovePtrBuilder flagsEnable(SAMovePtrFlag... maskEnums) {
      for(SAMovePtrFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SAMovePtr.SAMovePtrBuilder flagsDisable(SAMovePtrFlag... maskEnums) {
      for(SAMovePtrFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
