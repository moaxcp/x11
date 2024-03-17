package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetDeviceInfo implements TwoWayRequest<GetDeviceInfoReply> {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 24;

  private short deviceSpec;

  private short wanted;

  private boolean allButtons;

  private byte firstButton;

  private byte nButtons;

  private short ledClass;

  private short ledID;

  public XReplyFunction<GetDeviceInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDeviceInfoReply.readGetDeviceInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDeviceInfo readGetDeviceInfo(X11Input in) throws IOException {
    GetDeviceInfo.GetDeviceInfoBuilder javaBuilder = GetDeviceInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short wanted = in.readCard16();
    boolean allButtons = in.readBool();
    byte firstButton = in.readCard8();
    byte nButtons = in.readCard8();
    byte[] pad8 = in.readPad(1);
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.wanted(wanted);
    javaBuilder.allButtons(allButtons);
    javaBuilder.firstButton(firstButton);
    javaBuilder.nButtons(nButtons);
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(wanted);
    out.writeBool(allButtons);
    out.writeCard8(firstButton);
    out.writeCard8(nButtons);
    out.writePad(1);
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
  }

  public boolean isWantedEnabled(@NonNull XIFeature... maskEnums) {
    for(XIFeature m : maskEnums) {
      if(!m.isEnabled(wanted)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceInfoBuilder {
    public boolean isWantedEnabled(@NonNull XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        if(!m.isEnabled(wanted)) {
          return false;
        }
      }
      return true;
    }

    public GetDeviceInfo.GetDeviceInfoBuilder wantedEnable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        wanted((short) m.enableFor(wanted));
      }
      return this;
    }

    public GetDeviceInfo.GetDeviceInfoBuilder wantedDisable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        wanted((short) m.disableFor(wanted));
      }
      return this;
    }

    public GetDeviceInfo.GetDeviceInfoBuilder ledClass(LedClass ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public GetDeviceInfo.GetDeviceInfoBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
