package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "randr";

  public static final byte NUMBER = 1;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte subCode;

  private short sequenceNumber;

  @NonNull
  private NotifyDataUnion u;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NotifyEvent readNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in)
      throws IOException {
    NotifyEvent.NotifyEventBuilder javaBuilder = NotifyEvent.builder();
    byte subCode = in.readCard8();
    short sequenceNumber = in.readCard16();
    NotifyDataUnion u = NotifyDataUnion.readNotifyDataUnion(in, subCode);
    javaBuilder.subCode(subCode);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.u(u);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeCard8(subCode);
    out.writeCard16(sequenceNumber);
    u.write(out);
  }

  @Override
  public int getSize() {
    return 200;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class NotifyEventBuilder {
    public NotifyEvent.NotifyEventBuilder subCode(Notify subCode) {
      this.subCode = (byte) subCode.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder subCode(byte subCode) {
      this.subCode = subCode;
      return this;
    }

    public int getSize() {
      return 200;
    }
  }
}
