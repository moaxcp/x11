package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SendEvent implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 25;

  private boolean propagate;

  private int destination;

  private int eventMask;

  @NonNull
  private List<Byte> event;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SendEvent readSendEvent(X11Input in) throws IOException {
    SendEvent.SendEventBuilder javaBuilder = SendEvent.builder();
    boolean propagate = in.readBool();
    short length = in.readCard16();
    int destination = in.readCard32();
    int eventMask = in.readCard32();
    List<Byte> event = in.readChar(32);
    javaBuilder.propagate(propagate);
    javaBuilder.destination(destination);
    javaBuilder.eventMask(eventMask);
    javaBuilder.event(event);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(propagate);
    out.writeCard16((short) getLength());
    out.writeCard32(destination);
    out.writeCard32(eventMask);
    out.writeChar(event);
    out.writePadAlign(getSize());
  }

  public boolean isEventMaskEnabled(@NonNull EventMask... maskEnums) {
    for(EventMask m : maskEnums) {
      if(!m.isEnabled(eventMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12 + 1 * event.size();
  }

  public static class SendEventBuilder {
    public boolean isEventMaskEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public SendEvent.SendEventBuilder eventMaskEnable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SendEvent.SendEventBuilder eventMaskDisable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 12 + 1 * event.size();
    }
  }
}
