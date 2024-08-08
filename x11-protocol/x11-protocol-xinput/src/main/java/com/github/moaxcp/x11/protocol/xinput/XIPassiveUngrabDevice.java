package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class XIPassiveUngrabDevice implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 55;

  private int grabWindow;

  private int detail;

  private short deviceid;

  private byte grabType;

  @NonNull
  private IntList modifiers;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIPassiveUngrabDevice readXIPassiveUngrabDevice(X11Input in) throws IOException {
    XIPassiveUngrabDevice.XIPassiveUngrabDeviceBuilder javaBuilder = XIPassiveUngrabDevice.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    int detail = in.readCard32();
    short deviceid = in.readCard16();
    short numModifiers = in.readCard16();
    byte grabType = in.readCard8();
    byte[] pad8 = in.readPad(3);
    IntList modifiers = in.readCard32(Short.toUnsignedInt(numModifiers));
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.detail(detail);
    javaBuilder.deviceid(deviceid);
    javaBuilder.grabType(grabType);
    javaBuilder.modifiers(modifiers.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard32(detail);
    out.writeCard16(deviceid);
    short numModifiers = (short) modifiers.size();
    out.writeCard16(numModifiers);
    out.writeCard8(grabType);
    out.writePad(3);
    out.writeCard32(modifiers);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 20 + 4 * modifiers.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIPassiveUngrabDeviceBuilder {
    public XIPassiveUngrabDevice.XIPassiveUngrabDeviceBuilder grabType(GrabType grabType) {
      this.grabType = (byte) grabType.getValue();
      return this;
    }

    public XIPassiveUngrabDevice.XIPassiveUngrabDeviceBuilder grabType(byte grabType) {
      this.grabType = grabType;
      return this;
    }

    public int getSize() {
      return 20 + 4 * modifiers.size();
    }
  }
}
