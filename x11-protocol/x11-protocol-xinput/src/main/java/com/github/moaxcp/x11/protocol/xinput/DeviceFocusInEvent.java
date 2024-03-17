package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.NotifyDetail;
import com.github.moaxcp.x11.protocol.xproto.NotifyMode;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceFocusInEvent implements XEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 6;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte detail;

  private short sequenceNumber;

  private int time;

  private int window;

  private byte mode;

  private byte deviceId;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DeviceFocusInEvent readDeviceFocusInEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    DeviceFocusInEvent.DeviceFocusInEventBuilder javaBuilder = DeviceFocusInEvent.builder();
    byte detail = in.readByte();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int window = in.readCard32();
    byte mode = in.readByte();
    byte deviceId = in.readCard8();
    byte[] pad7 = in.readPad(18);
    javaBuilder.detail(detail);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.window(window);
    javaBuilder.mode(mode);
    javaBuilder.deviceId(deviceId);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeByte(detail);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(window);
    out.writeByte(mode);
    out.writeCard8(deviceId);
    out.writePad(18);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceFocusInEventBuilder {
    public DeviceFocusInEvent.DeviceFocusInEventBuilder detail(NotifyDetail detail) {
      this.detail = (byte) detail.getValue();
      return this;
    }

    public DeviceFocusInEvent.DeviceFocusInEventBuilder detail(byte detail) {
      this.detail = detail;
      return this;
    }

    public DeviceFocusInEvent.DeviceFocusInEventBuilder mode(NotifyMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public DeviceFocusInEvent.DeviceFocusInEventBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
