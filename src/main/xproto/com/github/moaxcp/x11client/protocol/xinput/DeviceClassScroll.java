package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DeviceClassScroll implements DeviceClass, XinputObject {
  private short type;

  private short len;

  private short sourceid;

  private short number;

  private short scrollType;

  private int flags;

  @NonNull
  private Fp3232 increment;

  public static DeviceClassScroll readDeviceClassScroll(short type, short len, short sourceid,
      X11Input in) throws IOException {
    DeviceClassScroll.DeviceClassScrollBuilder javaBuilder = DeviceClassScroll.builder();
    short number = in.readCard16();
    short scrollType = in.readCard16();
    byte[] pad5 = in.readPad(2);
    int flags = in.readCard32();
    Fp3232 increment = Fp3232.readFp3232(in);
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.sourceid(sourceid);
    javaBuilder.number(number);
    javaBuilder.scrollType(scrollType);
    javaBuilder.flags(flags);
    javaBuilder.increment(increment);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    out.writeCard16(sourceid);
    out.writeCard16(number);
    out.writeCard16(scrollType);
    out.writePad(2);
    out.writeCard32(flags);
    increment.write(out);
  }

  public boolean isFlagsEnabled(@NonNull ScrollFlags... maskEnums) {
    for(ScrollFlags m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class DeviceClassScrollBuilder {
    public DeviceClassScroll.DeviceClassScrollBuilder type(DeviceClassType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public DeviceClassScroll.DeviceClassScrollBuilder type(short type) {
      this.type = type;
      return this;
    }

    public DeviceClassScroll.DeviceClassScrollBuilder scrollType(ScrollType scrollType) {
      this.scrollType = (short) scrollType.getValue();
      return this;
    }

    public DeviceClassScroll.DeviceClassScrollBuilder scrollType(short scrollType) {
      this.scrollType = scrollType;
      return this;
    }

    public boolean isFlagsEnabled(@NonNull ScrollFlags... maskEnums) {
      for(ScrollFlags m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public DeviceClassScroll.DeviceClassScrollBuilder flagsEnable(ScrollFlags... maskEnums) {
      for(ScrollFlags m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public DeviceClassScroll.DeviceClassScrollBuilder flagsDisable(ScrollFlags... maskEnums) {
      for(ScrollFlags m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 24;
    }
  }
}
