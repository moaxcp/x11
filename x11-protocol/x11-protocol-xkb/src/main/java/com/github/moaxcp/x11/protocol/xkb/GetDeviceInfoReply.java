package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetDeviceInfoReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private byte deviceID;

  private short sequenceNumber;

  private short present;

  private short supported;

  private short unsupported;

  private byte firstBtnWanted;

  private byte nBtnsWanted;

  private byte firstBtnRtrn;

  private byte totalBtns;

  private boolean hasOwnState;

  private short dfltKbdFB;

  private short dfltLedFB;

  private int devType;

  @NonNull
  private List<Byte> name;

  @NonNull
  private List<ActionUnion> btnActions;

  @NonNull
  private List<DeviceLedInfo> leds;

  public static GetDeviceInfoReply readGetDeviceInfoReply(byte deviceID, short sequenceNumber,
      X11Input in) throws IOException {
    GetDeviceInfoReply.GetDeviceInfoReplyBuilder javaBuilder = GetDeviceInfoReply.builder();
    int length = in.readCard32();
    short present = in.readCard16();
    short supported = in.readCard16();
    short unsupported = in.readCard16();
    short nDeviceLedFBs = in.readCard16();
    byte firstBtnWanted = in.readCard8();
    byte nBtnsWanted = in.readCard8();
    byte firstBtnRtrn = in.readCard8();
    byte nBtnsRtrn = in.readCard8();
    byte totalBtns = in.readCard8();
    boolean hasOwnState = in.readBool();
    short dfltKbdFB = in.readCard16();
    short dfltLedFB = in.readCard16();
    byte[] pad16 = in.readPad(2);
    int devType = in.readCard32();
    short nameLen = in.readCard16();
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameLen));
    in.readPadAlign(Short.toUnsignedInt(nameLen));
    List<ActionUnion> btnActions = new ArrayList<>(Byte.toUnsignedInt(nBtnsRtrn));
    for(int i = 0; i < Byte.toUnsignedInt(nBtnsRtrn); i++) {
      btnActions.add(ActionUnion.readActionUnion(in));
    }
    List<DeviceLedInfo> leds = new ArrayList<>(Short.toUnsignedInt(nDeviceLedFBs));
    for(int i = 0; i < Short.toUnsignedInt(nDeviceLedFBs); i++) {
      leds.add(DeviceLedInfo.readDeviceLedInfo(in));
    }
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.present(present);
    javaBuilder.supported(supported);
    javaBuilder.unsupported(unsupported);
    javaBuilder.firstBtnWanted(firstBtnWanted);
    javaBuilder.nBtnsWanted(nBtnsWanted);
    javaBuilder.firstBtnRtrn(firstBtnRtrn);
    javaBuilder.totalBtns(totalBtns);
    javaBuilder.hasOwnState(hasOwnState);
    javaBuilder.dfltKbdFB(dfltKbdFB);
    javaBuilder.dfltLedFB(dfltLedFB);
    javaBuilder.devType(devType);
    javaBuilder.name(name);
    javaBuilder.btnActions(btnActions);
    javaBuilder.leds(leds);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(present);
    out.writeCard16(supported);
    out.writeCard16(unsupported);
    short nDeviceLedFBs = (short) leds.size();
    out.writeCard16(nDeviceLedFBs);
    out.writeCard8(firstBtnWanted);
    out.writeCard8(nBtnsWanted);
    out.writeCard8(firstBtnRtrn);
    byte nBtnsRtrn = (byte) btnActions.size();
    out.writeCard8(nBtnsRtrn);
    out.writeCard8(totalBtns);
    out.writeBool(hasOwnState);
    out.writeCard16(dfltKbdFB);
    out.writeCard16(dfltLedFB);
    out.writePad(2);
    out.writeCard32(devType);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writeChar(name);
    out.writePadAlign(Short.toUnsignedInt(nameLen));
    for(ActionUnion t : btnActions) {
      t.write(out);
    }
    for(DeviceLedInfo t : leds) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean isPresentEnabled(@NonNull XIFeature... maskEnums) {
    for(XIFeature m : maskEnums) {
      if(!m.isEnabled(present)) {
        return false;
      }
    }
    return true;
  }

  public boolean isSupportedEnabled(@NonNull XIFeature... maskEnums) {
    for(XIFeature m : maskEnums) {
      if(!m.isEnabled(supported)) {
        return false;
      }
    }
    return true;
  }

  public boolean isUnsupportedEnabled(@NonNull XIFeature... maskEnums) {
    for(XIFeature m : maskEnums) {
      if(!m.isEnabled(unsupported)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 34 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + XObject.sizeOf(btnActions) + XObject.sizeOf(leds);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceInfoReplyBuilder {
    public boolean isPresentEnabled(@NonNull XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        if(!m.isEnabled(present)) {
          return false;
        }
      }
      return true;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder presentEnable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        present((short) m.enableFor(present));
      }
      return this;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder presentDisable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        present((short) m.disableFor(present));
      }
      return this;
    }

    public boolean isSupportedEnabled(@NonNull XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        if(!m.isEnabled(supported)) {
          return false;
        }
      }
      return true;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder supportedEnable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        supported((short) m.enableFor(supported));
      }
      return this;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder supportedDisable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        supported((short) m.disableFor(supported));
      }
      return this;
    }

    public boolean isUnsupportedEnabled(@NonNull XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        if(!m.isEnabled(unsupported)) {
          return false;
        }
      }
      return true;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder unsupportedEnable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        unsupported((short) m.enableFor(unsupported));
      }
      return this;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder unsupportedDisable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        unsupported((short) m.disableFor(unsupported));
      }
      return this;
    }

    public int getSize() {
      return 34 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + XObject.sizeOf(btnActions) + XObject.sizeOf(leds);
    }
  }
}
