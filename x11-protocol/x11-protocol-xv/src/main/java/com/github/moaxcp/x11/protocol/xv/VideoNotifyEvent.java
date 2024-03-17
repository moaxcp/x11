package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VideoNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xv";

  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte reason;

  private short sequenceNumber;

  private int time;

  private int drawable;

  private int port;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static VideoNotifyEvent readVideoNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    VideoNotifyEvent.VideoNotifyEventBuilder javaBuilder = VideoNotifyEvent.builder();
    byte reason = in.readByte();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int drawable = in.readCard32();
    int port = in.readCard32();
    byte[] pad6 = in.readPad(16);
    javaBuilder.reason(reason);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.drawable(drawable);
    javaBuilder.port(port);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeByte(reason);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(drawable);
    out.writeCard32(port);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class VideoNotifyEventBuilder {
    public VideoNotifyEvent.VideoNotifyEventBuilder reason(VideoNotifyReason reason) {
      this.reason = (byte) reason.getValue();
      return this;
    }

    public VideoNotifyEvent.VideoNotifyEventBuilder reason(byte reason) {
      this.reason = reason;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
