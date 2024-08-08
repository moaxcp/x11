package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import com.github.moaxcp.x11.protocol.xproto.GrabMode;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class XIPassiveGrabDevice implements TwoWayRequest<XIPassiveGrabDeviceReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 54;

  private int time;

  private int grabWindow;

  private int cursor;

  private int detail;

  private short deviceid;

  private byte grabType;

  private byte grabMode;

  private byte pairedDeviceMode;

  private boolean ownerEvents;

  @NonNull
  private IntList mask;

  @NonNull
  private IntList modifiers;

  public XReplyFunction<XIPassiveGrabDeviceReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIPassiveGrabDeviceReply.readXIPassiveGrabDeviceReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIPassiveGrabDevice readXIPassiveGrabDevice(X11Input in) throws IOException {
    XIPassiveGrabDevice.XIPassiveGrabDeviceBuilder javaBuilder = XIPassiveGrabDevice.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int time = in.readCard32();
    int grabWindow = in.readCard32();
    int cursor = in.readCard32();
    int detail = in.readCard32();
    short deviceid = in.readCard16();
    short numModifiers = in.readCard16();
    short maskLen = in.readCard16();
    byte grabType = in.readCard8();
    byte grabMode = in.readCard8();
    byte pairedDeviceMode = in.readCard8();
    boolean ownerEvents = in.readBool();
    byte[] pad14 = in.readPad(2);
    IntList mask = in.readCard32(Short.toUnsignedInt(maskLen));
    IntList modifiers = in.readCard32(Short.toUnsignedInt(numModifiers));
    javaBuilder.time(time);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.cursor(cursor);
    javaBuilder.detail(detail);
    javaBuilder.deviceid(deviceid);
    javaBuilder.grabType(grabType);
    javaBuilder.grabMode(grabMode);
    javaBuilder.pairedDeviceMode(pairedDeviceMode);
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.mask(mask.toImmutable());
    javaBuilder.modifiers(modifiers.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(time);
    out.writeCard32(grabWindow);
    out.writeCard32(cursor);
    out.writeCard32(detail);
    out.writeCard16(deviceid);
    short numModifiers = (short) modifiers.size();
    out.writeCard16(numModifiers);
    short maskLen = (short) mask.size();
    out.writeCard16(maskLen);
    out.writeCard8(grabType);
    out.writeCard8(grabMode);
    out.writeCard8(pairedDeviceMode);
    out.writeBool(ownerEvents);
    out.writePad(2);
    out.writeCard32(mask);
    out.writeCard32(modifiers);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * mask.size() + 4 * modifiers.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIPassiveGrabDeviceBuilder {
    public XIPassiveGrabDevice.XIPassiveGrabDeviceBuilder grabType(GrabType grabType) {
      this.grabType = (byte) grabType.getValue();
      return this;
    }

    public XIPassiveGrabDevice.XIPassiveGrabDeviceBuilder grabType(byte grabType) {
      this.grabType = grabType;
      return this;
    }

    public XIPassiveGrabDevice.XIPassiveGrabDeviceBuilder grabMode(GrabMode22 grabMode) {
      this.grabMode = (byte) grabMode.getValue();
      return this;
    }

    public XIPassiveGrabDevice.XIPassiveGrabDeviceBuilder grabMode(byte grabMode) {
      this.grabMode = grabMode;
      return this;
    }

    public XIPassiveGrabDevice.XIPassiveGrabDeviceBuilder pairedDeviceMode(
        GrabMode pairedDeviceMode) {
      this.pairedDeviceMode = (byte) pairedDeviceMode.getValue();
      return this;
    }

    public XIPassiveGrabDevice.XIPassiveGrabDeviceBuilder pairedDeviceMode(byte pairedDeviceMode) {
      this.pairedDeviceMode = pairedDeviceMode;
      return this;
    }

    public XIPassiveGrabDevice.XIPassiveGrabDeviceBuilder ownerEvents(GrabOwner ownerEvents) {
      this.ownerEvents = ownerEvents.getValue() > 0;
      return this;
    }

    public XIPassiveGrabDevice.XIPassiveGrabDeviceBuilder ownerEvents(boolean ownerEvents) {
      this.ownerEvents = ownerEvents;
      return this;
    }

    public int getSize() {
      return 32 + 4 * mask.size() + 4 * modifiers.size();
    }
  }
}
