package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ShortList;

@Value
@Builder
public class CreatePointerBarrier implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 31;

  private int barrier;

  private int window;

  private short x1;

  private short y1;

  private short x2;

  private short y2;

  private int directions;

  @NonNull
  private ShortList devices;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreatePointerBarrier readCreatePointerBarrier(X11Input in) throws IOException {
    CreatePointerBarrier.CreatePointerBarrierBuilder javaBuilder = CreatePointerBarrier.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int barrier = in.readCard32();
    int window = in.readCard32();
    short x1 = in.readCard16();
    short y1 = in.readCard16();
    short x2 = in.readCard16();
    short y2 = in.readCard16();
    int directions = in.readCard32();
    byte[] pad10 = in.readPad(2);
    short numDevices = in.readCard16();
    ShortList devices = in.readCard16(Short.toUnsignedInt(numDevices));
    javaBuilder.barrier(barrier);
    javaBuilder.window(window);
    javaBuilder.x1(x1);
    javaBuilder.y1(y1);
    javaBuilder.x2(x2);
    javaBuilder.y2(y2);
    javaBuilder.directions(directions);
    javaBuilder.devices(devices.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(barrier);
    out.writeCard32(window);
    out.writeCard16(x1);
    out.writeCard16(y1);
    out.writeCard16(x2);
    out.writeCard16(y2);
    out.writeCard32(directions);
    out.writePad(2);
    short numDevices = (short) devices.size();
    out.writeCard16(numDevices);
    out.writeCard16(devices);
    out.writePadAlign(getSize());
  }

  public boolean isDirectionsEnabled(@NonNull BarrierDirections... maskEnums) {
    for(BarrierDirections m : maskEnums) {
      if(!m.isEnabled(directions)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28 + 2 * devices.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreatePointerBarrierBuilder {
    public boolean isDirectionsEnabled(@NonNull BarrierDirections... maskEnums) {
      for(BarrierDirections m : maskEnums) {
        if(!m.isEnabled(directions)) {
          return false;
        }
      }
      return true;
    }

    public CreatePointerBarrier.CreatePointerBarrierBuilder directionsEnable(
        BarrierDirections... maskEnums) {
      for(BarrierDirections m : maskEnums) {
        directions((int) m.enableFor(directions));
      }
      return this;
    }

    public CreatePointerBarrier.CreatePointerBarrierBuilder directionsDisable(
        BarrierDirections... maskEnums) {
      for(BarrierDirections m : maskEnums) {
        directions((int) m.disableFor(directions));
      }
      return this;
    }

    public int getSize() {
      return 28 + 2 * devices.size();
    }
  }
}
