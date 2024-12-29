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
public class GrabDevice implements TwoWayRequest<GrabDeviceReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 13;

  private int grabWindow;

  private int time;

  private byte thisDeviceMode;

  private byte otherDeviceMode;

  private boolean ownerEvents;

  private byte deviceId;

  @NonNull
  private IntList classes;

  public XReplyFunction<GrabDeviceReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GrabDeviceReply.readGrabDeviceReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabDevice readGrabDevice(X11Input in) throws IOException {
    GrabDevice.GrabDeviceBuilder javaBuilder = GrabDevice.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    int time = in.readCard32();
    short numClasses = in.readCard16();
    byte thisDeviceMode = in.readCard8();
    byte otherDeviceMode = in.readCard8();
    boolean ownerEvents = in.readBool();
    byte deviceId = in.readCard8();
    byte[] pad10 = in.readPad(2);
    IntList classes = in.readCard32(Short.toUnsignedInt(numClasses));
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.time(time);
    javaBuilder.thisDeviceMode(thisDeviceMode);
    javaBuilder.otherDeviceMode(otherDeviceMode);
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.deviceId(deviceId);
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
    out.writeCard32(time);
    short numClasses = (short) classes.size();
    out.writeCard16(numClasses);
    out.writeCard8(thisDeviceMode);
    out.writeCard8(otherDeviceMode);
    out.writeBool(ownerEvents);
    out.writeCard8(deviceId);
    out.writePad(2);
    out.writeCard32(classes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 20 + 4 * classes.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GrabDeviceBuilder {
    public GrabDevice.GrabDeviceBuilder thisDeviceMode(GrabMode thisDeviceMode) {
      this.thisDeviceMode = (byte) thisDeviceMode.getValue();
      return this;
    }

    public GrabDevice.GrabDeviceBuilder thisDeviceMode(byte thisDeviceMode) {
      this.thisDeviceMode = thisDeviceMode;
      return this;
    }

    public GrabDevice.GrabDeviceBuilder otherDeviceMode(GrabMode otherDeviceMode) {
      this.otherDeviceMode = (byte) otherDeviceMode.getValue();
      return this;
    }

    public GrabDevice.GrabDeviceBuilder otherDeviceMode(byte otherDeviceMode) {
      this.otherDeviceMode = otherDeviceMode;
      return this;
    }

    public int getSize() {
      return 20 + 4 * classes.size();
    }
  }
}
