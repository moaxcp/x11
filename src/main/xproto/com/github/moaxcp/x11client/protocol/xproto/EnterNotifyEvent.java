package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class EnterNotifyEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 7;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte detail;

  private short sequenceNumber;

  private int time;

  private int root;

  private int event;

  private int child;

  private short rootX;

  private short rootY;

  private short eventX;

  private short eventY;

  private short state;

  private byte mode;

  private byte sameScreenFocus;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static EnterNotifyEvent readEnterNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    EnterNotifyEvent.EnterNotifyEventBuilder javaBuilder = EnterNotifyEvent.builder();
    byte detail = in.readByte();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int root = in.readCard32();
    int event = in.readCard32();
    int child = in.readCard32();
    short rootX = in.readInt16();
    short rootY = in.readInt16();
    short eventX = in.readInt16();
    short eventY = in.readInt16();
    short state = in.readCard16();
    byte mode = in.readByte();
    byte sameScreenFocus = in.readByte();
    javaBuilder.detail(detail);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.root(root);
    javaBuilder.event(event);
    javaBuilder.child(child);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.eventX(eventX);
    javaBuilder.eventY(eventY);
    javaBuilder.state(state);
    javaBuilder.mode(mode);
    javaBuilder.sameScreenFocus(sameScreenFocus);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeByte(detail);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(root);
    out.writeCard32(event);
    out.writeCard32(child);
    out.writeInt16(rootX);
    out.writeInt16(rootY);
    out.writeInt16(eventX);
    out.writeInt16(eventY);
    out.writeCard16(state);
    out.writeByte(mode);
    out.writeByte(sameScreenFocus);
  }

  public boolean isStateEnabled(@NonNull KeyButMask... maskEnums) {
    for(KeyButMask m : maskEnums) {
      if(!m.isEnabled(state)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class EnterNotifyEventBuilder {
    public EnterNotifyEvent.EnterNotifyEventBuilder detail(NotifyDetail detail) {
      this.detail = (byte) detail.getValue();
      return this;
    }

    public EnterNotifyEvent.EnterNotifyEventBuilder detail(byte detail) {
      this.detail = detail;
      return this;
    }

    public boolean isStateEnabled(@NonNull KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        if(!m.isEnabled(state)) {
          return false;
        }
      }
      return true;
    }

    public EnterNotifyEvent.EnterNotifyEventBuilder stateEnable(KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        state((short) m.enableFor(state));
      }
      return this;
    }

    public EnterNotifyEvent.EnterNotifyEventBuilder stateDisable(KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        state((short) m.disableFor(state));
      }
      return this;
    }

    public EnterNotifyEvent.EnterNotifyEventBuilder mode(NotifyMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public EnterNotifyEvent.EnterNotifyEventBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
