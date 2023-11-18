package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GrabButton implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 28;

  private boolean ownerEvents;

  private int grabWindow;

  private short eventMask;

  private byte pointerMode;

  private byte keyboardMode;

  private int confineTo;

  private int cursor;

  private byte button;

  private short modifiers;

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabButton readGrabButton(X11Input in) throws IOException {
    GrabButton.GrabButtonBuilder javaBuilder = GrabButton.builder();
    boolean ownerEvents = in.readBool();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short eventMask = in.readCard16();
    byte pointerMode = in.readCard8();
    byte keyboardMode = in.readCard8();
    int confineTo = in.readCard32();
    int cursor = in.readCard32();
    byte button = in.readCard8();
    byte[] pad10 = in.readPad(1);
    short modifiers = in.readCard16();
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.eventMask(eventMask);
    javaBuilder.pointerMode(pointerMode);
    javaBuilder.keyboardMode(keyboardMode);
    javaBuilder.confineTo(confineTo);
    javaBuilder.cursor(cursor);
    javaBuilder.button(button);
    javaBuilder.modifiers(modifiers);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeBool(ownerEvents);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard16(eventMask);
    out.writeCard8(pointerMode);
    out.writeCard8(keyboardMode);
    out.writeCard32(confineTo);
    out.writeCard32(cursor);
    out.writeCard8(button);
    out.writePad(1);
    out.writeCard16(modifiers);
  }

  public boolean isEventMaskEnabled(@NonNull EventMask... maskEnums) {
    for(EventMask m : maskEnums) {
      if(!m.isEnabled(eventMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isModifiersEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(modifiers)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class GrabButtonBuilder {
    public boolean isEventMaskEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public GrabButton.GrabButtonBuilder eventMaskEnable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((short) m.enableFor(eventMask));
      }
      return this;
    }

    public GrabButton.GrabButtonBuilder eventMaskDisable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((short) m.disableFor(eventMask));
      }
      return this;
    }

    public GrabButton.GrabButtonBuilder pointerMode(GrabMode pointerMode) {
      this.pointerMode = (byte) pointerMode.getValue();
      return this;
    }

    public GrabButton.GrabButtonBuilder pointerMode(byte pointerMode) {
      this.pointerMode = pointerMode;
      return this;
    }

    public GrabButton.GrabButtonBuilder keyboardMode(GrabMode keyboardMode) {
      this.keyboardMode = (byte) keyboardMode.getValue();
      return this;
    }

    public GrabButton.GrabButtonBuilder keyboardMode(byte keyboardMode) {
      this.keyboardMode = keyboardMode;
      return this;
    }

    public GrabButton.GrabButtonBuilder button(ButtonIndex button) {
      this.button = (byte) button.getValue();
      return this;
    }

    public GrabButton.GrabButtonBuilder button(byte button) {
      this.button = button;
      return this;
    }

    public boolean isModifiersEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(modifiers)) {
          return false;
        }
      }
      return true;
    }

    public GrabButton.GrabButtonBuilder modifiersEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modifiers((short) m.enableFor(modifiers));
      }
      return this;
    }

    public GrabButton.GrabButtonBuilder modifiersDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modifiers((short) m.disableFor(modifiers));
      }
      return this;
    }

    public int getSize() {
      return 24;
    }
  }
}
