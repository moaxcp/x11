package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SendEvent implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 25;

  private boolean propagate;

  private int destination;

  private int eventMask;

  @NonNull
  private ByteList event;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SendEvent readSendEvent(X11Input in) throws IOException {
    SendEvent.SendEventBuilder javaBuilder = SendEvent.builder();
    boolean propagate = in.readBool();
    short length = in.readCard16();
    int destination = in.readCard32();
    int eventMask = in.readCard32();
    ByteList event = in.readChar(32);
    javaBuilder.propagate(propagate);
    javaBuilder.destination(destination);
    javaBuilder.eventMask(eventMask);
    javaBuilder.event(event.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
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
