package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ExtensionDeviceNotifyEvent implements XEvent, XkbObject {
  public static final byte NUMBER = 11;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private short reason;

  private short ledClass;

  private short ledID;

  private int ledsDefined;

  private int ledState;

  private byte firstButton;

  private byte nButtons;

  private short supported;

  private short unsupported;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ExtensionDeviceNotifyEvent readExtensionDeviceNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder javaBuilder = ExtensionDeviceNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte[] pad5 = in.readPad(1);
    short reason = in.readCard16();
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    int ledsDefined = in.readCard32();
    int ledState = in.readCard32();
    byte firstButton = in.readCard8();
    byte nButtons = in.readCard8();
    short supported = in.readCard16();
    short unsupported = in.readCard16();
    byte[] pad15 = in.readPad(2);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.reason(reason);
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    javaBuilder.ledsDefined(ledsDefined);
    javaBuilder.ledState(ledState);
    javaBuilder.firstButton(firstButton);
    javaBuilder.nButtons(nButtons);
    javaBuilder.supported(supported);
    javaBuilder.unsupported(unsupported);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writePad(1);
    out.writeCard16(reason);
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
    out.writeCard32(ledsDefined);
    out.writeCard32(ledState);
    out.writeCard8(firstButton);
    out.writeCard8(nButtons);
    out.writeCard16(supported);
    out.writeCard16(unsupported);
    out.writePad(2);
  }

  public boolean isReasonEnabled(@NonNull XIFeature... maskEnums) {
    for(XIFeature m : maskEnums) {
      if(!m.isEnabled(reason)) {
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
    return 32;
  }

  public static class ExtensionDeviceNotifyEventBuilder {
    public boolean isReasonEnabled(@NonNull XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        if(!m.isEnabled(reason)) {
          return false;
        }
      }
      return true;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder reasonEnable(
        XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        reason((short) m.enableFor(reason));
      }
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder reasonDisable(
        XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        reason((short) m.disableFor(reason));
      }
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder ledClass(
        LedClassResult ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
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

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder supportedEnable(
        XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        supported((short) m.enableFor(supported));
      }
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder supportedDisable(
        XIFeature... maskEnums) {
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

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder unsupportedEnable(
        XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        unsupported((short) m.enableFor(unsupported));
      }
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder unsupportedDisable(
        XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        unsupported((short) m.disableFor(unsupported));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
