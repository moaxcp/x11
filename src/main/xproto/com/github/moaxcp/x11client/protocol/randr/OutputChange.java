package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import com.github.moaxcp.x11client.protocol.render.SubPixel;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class OutputChange implements NotifyDataUnion, XStruct, RandrObject {
  private int timestamp;

  private int configTimestamp;

  private int window;

  private int output;

  private int crtc;

  private int mode;

  private short rotation;

  private byte connection;

  private byte subpixelOrder;

  public static OutputChange readOutputChange(X11Input in) throws IOException {
    OutputChange.OutputChangeBuilder javaBuilder = OutputChange.builder();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    int window = in.readCard32();
    int output = in.readCard32();
    int crtc = in.readCard32();
    int mode = in.readCard32();
    short rotation = in.readCard16();
    byte connection = in.readCard8();
    byte subpixelOrder = in.readCard8();
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.window(window);
    javaBuilder.output(output);
    javaBuilder.crtc(crtc);
    javaBuilder.mode(mode);
    javaBuilder.rotation(rotation);
    javaBuilder.connection(connection);
    javaBuilder.subpixelOrder(subpixelOrder);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    out.writeCard32(window);
    out.writeCard32(output);
    out.writeCard32(crtc);
    out.writeCard32(mode);
    out.writeCard16(rotation);
    out.writeCard8(connection);
    out.writeCard8(subpixelOrder);
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

  public static class OutputChangeBuilder {
    public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        if(!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public OutputChange.OutputChangeBuilder rotationEnable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public OutputChange.OutputChangeBuilder rotationDisable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public OutputChange.OutputChangeBuilder connection(Connection connection) {
      this.connection = (byte) connection.getValue();
      return this;
    }

    public OutputChange.OutputChangeBuilder connection(byte connection) {
      this.connection = connection;
      return this;
    }

    public OutputChange.OutputChangeBuilder subpixelOrder(SubPixel subpixelOrder) {
      this.subpixelOrder = (byte) subpixelOrder.getValue();
      return this;
    }

    public OutputChange.OutputChangeBuilder subpixelOrder(byte subpixelOrder) {
      this.subpixelOrder = subpixelOrder;
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
