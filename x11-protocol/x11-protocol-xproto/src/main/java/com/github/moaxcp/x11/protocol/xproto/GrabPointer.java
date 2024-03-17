package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GrabPointer implements TwoWayRequest<GrabPointerReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 26;

  private boolean ownerEvents;

  private int grabWindow;

  private short eventMask;

  private byte pointerMode;

  private byte keyboardMode;

  private int confineTo;

  private int cursor;

  private int time;

  public XReplyFunction<GrabPointerReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GrabPointerReply.readGrabPointerReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabPointer readGrabPointer(X11Input in) throws IOException {
    GrabPointer.GrabPointerBuilder javaBuilder = GrabPointer.builder();
    boolean ownerEvents = in.readBool();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short eventMask = in.readCard16();
    byte pointerMode = in.readByte();
    byte keyboardMode = in.readByte();
    int confineTo = in.readCard32();
    int cursor = in.readCard32();
    int time = in.readCard32();
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.eventMask(eventMask);
    javaBuilder.pointerMode(pointerMode);
    javaBuilder.keyboardMode(keyboardMode);
    javaBuilder.confineTo(confineTo);
    javaBuilder.cursor(cursor);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeBool(ownerEvents);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard16(eventMask);
    out.writeByte(pointerMode);
    out.writeByte(keyboardMode);
    out.writeCard32(confineTo);
    out.writeCard32(cursor);
    out.writeCard32(time);
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
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GrabPointerBuilder {
    public boolean isEventMaskEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public GrabPointer.GrabPointerBuilder eventMaskEnable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((short) m.enableFor(eventMask));
      }
      return this;
    }

    public GrabPointer.GrabPointerBuilder eventMaskDisable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((short) m.disableFor(eventMask));
      }
      return this;
    }

    public GrabPointer.GrabPointerBuilder pointerMode(GrabMode pointerMode) {
      this.pointerMode = (byte) pointerMode.getValue();
      return this;
    }

    public GrabPointer.GrabPointerBuilder pointerMode(byte pointerMode) {
      this.pointerMode = pointerMode;
      return this;
    }

    public GrabPointer.GrabPointerBuilder keyboardMode(GrabMode keyboardMode) {
      this.keyboardMode = (byte) keyboardMode.getValue();
      return this;
    }

    public GrabPointer.GrabPointerBuilder keyboardMode(byte keyboardMode) {
      this.keyboardMode = keyboardMode;
      return this;
    }

    public int getSize() {
      return 24;
    }
  }
}
