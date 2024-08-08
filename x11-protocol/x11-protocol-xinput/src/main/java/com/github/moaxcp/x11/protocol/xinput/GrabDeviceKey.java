package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.GrabMode;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class GrabDeviceKey implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

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
  private IntList classes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabDeviceKey readGrabDeviceKey(X11Input in) throws IOException {
    GrabDeviceKey.GrabDeviceKeyBuilder javaBuilder = GrabDeviceKey.builder();
    byte majorOpcode = in.readCard8();
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
    IntList classes = in.readCard32(Short.toUnsignedInt(numClasses));
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.modifiers(modifiers);
    javaBuilder.modifierDevice(modifierDevice);
    javaBuilder.grabbedDevice(grabbedDevice);
    javaBuilder.key(key);
    javaBuilder.thisDeviceMode(thisDeviceMode);
    javaBuilder.otherDeviceMode(otherDeviceMode);
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.classes(classes.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
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
