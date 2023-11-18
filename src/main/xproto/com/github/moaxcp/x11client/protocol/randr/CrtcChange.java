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
public class CrtcChange implements NotifyDataUnion, XStruct, RandrObject {
  private int timestamp;

  private int window;

  private int crtc;

  private int mode;

  private short rotation;

  private short x;

  private short y;

  private short width;

  private short height;

  public static CrtcChange readCrtcChange(X11Input in) throws IOException {
    CrtcChange.CrtcChangeBuilder javaBuilder = CrtcChange.builder();
    int timestamp = in.readCard32();
    int window = in.readCard32();
    int crtc = in.readCard32();
    int mode = in.readCard32();
    short rotation = in.readCard16();
    byte[] pad5 = in.readPad(2);
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.timestamp(timestamp);
    javaBuilder.window(window);
    javaBuilder.crtc(crtc);
    javaBuilder.mode(mode);
    javaBuilder.rotation(rotation);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(window);
    out.writeCard32(crtc);
    out.writeCard32(mode);
    out.writeCard16(rotation);
    out.writePad(2);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
    for(Rotation m : maskEnums) {
      if(!m.isEnabled(rotation)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28;
  }

  public static class CrtcChangeBuilder {
    public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        if(!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public CrtcChange.CrtcChangeBuilder rotationEnable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public CrtcChange.CrtcChangeBuilder rotationDisable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
