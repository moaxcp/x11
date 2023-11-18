package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import com.github.moaxcp.x11client.protocol.xproto.GrabMode;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class XIGrabDevice implements TwoWayRequest<XIGrabDeviceReply>, XinputObject {
  public static final byte OPCODE = 51;

  private int window;

  private int time;

  private int cursor;

  private short deviceid;

  private byte mode;

  private byte pairedDeviceMode;

  private boolean ownerEvents;

  @NonNull
  private List<Integer> mask;

  public XReplyFunction<XIGrabDeviceReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIGrabDeviceReply.readXIGrabDeviceReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIGrabDevice readXIGrabDevice(X11Input in) throws IOException {
    XIGrabDevice.XIGrabDeviceBuilder javaBuilder = XIGrabDevice.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int time = in.readCard32();
    int cursor = in.readCard32();
    short deviceid = in.readCard16();
    byte mode = in.readCard8();
    byte pairedDeviceMode = in.readCard8();
    boolean ownerEvents = in.readBool();
    byte[] pad10 = in.readPad(1);
    short maskLen = in.readCard16();
    List<Integer> mask = in.readCard32(Short.toUnsignedInt(maskLen));
    javaBuilder.window(window);
    javaBuilder.time(time);
    javaBuilder.cursor(cursor);
    javaBuilder.deviceid(deviceid);
    javaBuilder.mode(mode);
    javaBuilder.pairedDeviceMode(pairedDeviceMode);
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.mask(mask);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(time);
    out.writeCard32(cursor);
    out.writeCard16(deviceid);
    out.writeCard8(mode);
    out.writeCard8(pairedDeviceMode);
    out.writeBool(ownerEvents);
    out.writePad(1);
    short maskLen = (short) mask.size();
    out.writeCard16(maskLen);
    out.writeCard32(mask);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 4 * mask.size();
  }

  public static class XIGrabDeviceBuilder {
    public XIGrabDevice.XIGrabDeviceBuilder mode(GrabMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public XIGrabDevice.XIGrabDeviceBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public XIGrabDevice.XIGrabDeviceBuilder pairedDeviceMode(GrabMode pairedDeviceMode) {
      this.pairedDeviceMode = (byte) pairedDeviceMode.getValue();
      return this;
    }

    public XIGrabDevice.XIGrabDeviceBuilder pairedDeviceMode(byte pairedDeviceMode) {
      this.pairedDeviceMode = pairedDeviceMode;
      return this;
    }

    public XIGrabDevice.XIGrabDeviceBuilder ownerEvents(GrabOwner ownerEvents) {
      this.ownerEvents = ownerEvents.getValue() > 0;
      return this;
    }

    public XIGrabDevice.XIGrabDeviceBuilder ownerEvents(boolean ownerEvents) {
      this.ownerEvents = ownerEvents;
      return this;
    }

    public int getSize() {
      return 24 + 4 * mask.size();
    }
  }
}
