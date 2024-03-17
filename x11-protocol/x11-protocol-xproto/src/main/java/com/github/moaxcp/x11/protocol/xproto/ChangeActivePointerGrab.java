package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeActivePointerGrab implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 30;

  private int cursor;

  private int time;

  private short eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeActivePointerGrab readChangeActivePointerGrab(X11Input in) throws
      IOException {
    ChangeActivePointerGrab.ChangeActivePointerGrabBuilder javaBuilder = ChangeActivePointerGrab.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cursor = in.readCard32();
    int time = in.readCard32();
    short eventMask = in.readCard16();
    byte[] pad6 = in.readPad(2);
    javaBuilder.cursor(cursor);
    javaBuilder.time(time);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cursor);
    out.writeCard32(time);
    out.writeCard16(eventMask);
    out.writePad(2);
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
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeActivePointerGrabBuilder {
    public boolean isEventMaskEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public ChangeActivePointerGrab.ChangeActivePointerGrabBuilder eventMaskEnable(
        EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((short) m.enableFor(eventMask));
      }
      return this;
    }

    public ChangeActivePointerGrab.ChangeActivePointerGrabBuilder eventMaskDisable(
        EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((short) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
