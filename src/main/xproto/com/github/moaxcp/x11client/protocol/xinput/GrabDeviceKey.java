package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.xproto.GrabMode;
import com.github.moaxcp.x11client.protocol.xproto.ModMask;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GrabDeviceKey implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 15;

  private int grabWindow;

  private short modifiers;

  private byte modifierDevice;

  private byte grabbedDevice;

  private byte key;

  private byte thisDeviceMode;

  private byte otherDeviceMode;

  private boolean ownerEvents;

  @NonNull
  private List<Integer> classes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabDeviceKey readGrabDeviceKey(X11Input in) throws IOException {
    GrabDeviceKey.GrabDeviceKeyBuilder javaBuilder = GrabDeviceKey.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short numClasses = in.readCard16();
    short modifiers = in.readCard16();
    byte modifierDevice = in.readCard8();
    byte grabbedDevice = in.readCard8();
    byte key = in.readCard8();
    byte thisDeviceMode = in.readCard8();
    byte otherDeviceMode = in.readCard8();
    boolean ownerEvents = in.readBool();
    byte[] pad12 = in.readPad(2);
    List<Integer> classes = in.readCard32(Short.toUnsignedInt(numClasses));
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.modifiers(modifiers);
    javaBuilder.modifierDevice(modifierDevice);
    javaBuilder.grabbedDevice(grabbedDevice);
    javaBuilder.key(key);
    javaBuilder.thisDeviceMode(thisDeviceMode);
    javaBuilder.otherDeviceMode(otherDeviceMode);
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.classes(classes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    short numClasses = (short) classes.size();
    out.writeCard16(numClasses);
    out.writeCard16(modifiers);
    out.writeCard8(modifierDevice);
    out.writeCard8(grabbedDevice);
    out.writeCard8(key);
    out.writeCard8(thisDeviceMode);
    out.writeCard8(otherDeviceMode);
    out.writeBool(ownerEvents);
    out.writePad(2);
    out.writeCard32(classes);
    out.writePadAlign(getSize());
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
    return 20 + 4 * classes.size();
  }

  public static class GrabDeviceKeyBuilder {
    public boolean isModifiersEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(modifiers)) {
          return false;
        }
      }
      return true;
    }

    public GrabDeviceKey.GrabDeviceKeyBuilder modifiersEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modifiers((short) m.enableFor(modifiers));
      }
      return this;
    }

    public GrabDeviceKey.GrabDeviceKeyBuilder modifiersDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modifiers((short) m.disableFor(modifiers));
      }
      return this;
    }

    public GrabDeviceKey.GrabDeviceKeyBuilder thisDeviceMode(GrabMode thisDeviceMode) {
      this.thisDeviceMode = (byte) thisDeviceMode.getValue();
      return this;
    }

    public GrabDeviceKey.GrabDeviceKeyBuilder thisDeviceMode(byte thisDeviceMode) {
      this.thisDeviceMode = thisDeviceMode;
      return this;
    }

    public GrabDeviceKey.GrabDeviceKeyBuilder otherDeviceMode(GrabMode otherDeviceMode) {
      this.otherDeviceMode = (byte) otherDeviceMode.getValue();
      return this;
    }

    public GrabDeviceKey.GrabDeviceKeyBuilder otherDeviceMode(byte otherDeviceMode) {
      this.otherDeviceMode = otherDeviceMode;
      return this;
    }

    public int getSize() {
      return 20 + 4 * classes.size();
    }
  }
}
