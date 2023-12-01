package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.xproto.ModMask;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UngrabDeviceButton implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 18;

  private int grabWindow;

  private short modifiers;

  private byte modifierDevice;

  private byte button;

  private byte grabbedDevice;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UngrabDeviceButton readUngrabDeviceButton(X11Input in) throws IOException {
    UngrabDeviceButton.UngrabDeviceButtonBuilder javaBuilder = UngrabDeviceButton.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short modifiers = in.readCard16();
    byte modifierDevice = in.readCard8();
    byte button = in.readCard8();
    byte grabbedDevice = in.readCard8();
    byte[] pad8 = in.readPad(3);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.modifiers(modifiers);
    javaBuilder.modifierDevice(modifierDevice);
    javaBuilder.button(button);
    javaBuilder.grabbedDevice(grabbedDevice);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard16(modifiers);
    out.writeCard8(modifierDevice);
    out.writeCard8(button);
    out.writeCard8(grabbedDevice);
    out.writePad(3);
  }

  public boolean isModifiersEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(modifiers)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class UngrabDeviceButtonBuilder {
    public boolean isModifiersEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(modifiers)) {
          return false;
        }
      }
      return true;
    }

    public UngrabDeviceButton.UngrabDeviceButtonBuilder modifiersEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modifiers((short) m.enableFor(modifiers));
      }
      return this;
    }

    public UngrabDeviceButton.UngrabDeviceButtonBuilder modifiersDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modifiers((short) m.disableFor(modifiers));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
