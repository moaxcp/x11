package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetDeviceInfo implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 25;

  private short deviceSpec;

  private byte firstBtn;

  private short change;

  @NonNull
  private List<ActionUnion> btnActions;

  @NonNull
  private List<DeviceLedInfo> leds;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceInfo readSetDeviceInfo(X11Input in) throws IOException {
    SetDeviceInfo.SetDeviceInfoBuilder javaBuilder = SetDeviceInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte firstBtn = in.readCard8();
    byte nBtns = in.readCard8();
    short change = in.readCard16();
    short nDeviceLedFBs = in.readCard16();
    List<ActionUnion> btnActions = new ArrayList<>(Byte.toUnsignedInt(nBtns));
    for(int i = 0; i < Byte.toUnsignedInt(nBtns); i++) {
      btnActions.add(ActionUnion.readActionUnion(in));
    }
    List<DeviceLedInfo> leds = new ArrayList<>(Short.toUnsignedInt(nDeviceLedFBs));
    for(int i = 0; i < Short.toUnsignedInt(nDeviceLedFBs); i++) {
      leds.add(DeviceLedInfo.readDeviceLedInfo(in));
    }
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.firstBtn(firstBtn);
    javaBuilder.change(change);
    javaBuilder.btnActions(btnActions);
    javaBuilder.leds(leds);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard8(firstBtn);
    byte nBtns = (byte) btnActions.size();
    out.writeCard8(nBtns);
    out.writeCard16(change);
    short nDeviceLedFBs = (short) leds.size();
    out.writeCard16(nDeviceLedFBs);
    for(ActionUnion t : btnActions) {
      t.write(out);
    }
    for(DeviceLedInfo t : leds) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean isChangeEnabled(@NonNull XIFeature... maskEnums) {
    for(XIFeature m : maskEnums) {
      if(!m.isEnabled(change)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(btnActions) + XObject.sizeOf(leds);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetDeviceInfoBuilder {
    public boolean isChangeEnabled(@NonNull XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        if(!m.isEnabled(change)) {
          return false;
        }
      }
      return true;
    }

    public SetDeviceInfo.SetDeviceInfoBuilder changeEnable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        change((short) m.enableFor(change));
      }
      return this;
    }

    public SetDeviceInfo.SetDeviceInfoBuilder changeDisable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        change((short) m.disableFor(change));
      }
      return this;
    }

    public int getSize() {
      return 12 + XObject.sizeOf(btnActions) + XObject.sizeOf(leds);
    }
  }
}
