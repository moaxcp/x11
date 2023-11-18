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
public class UngrabButton implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 29;

  private byte button;

  private int grabWindow;

  private short modifiers;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UngrabButton readUngrabButton(X11Input in) throws IOException {
    UngrabButton.UngrabButtonBuilder javaBuilder = UngrabButton.builder();
    byte button = in.readCard8();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short modifiers = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.button(button);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.modifiers(modifiers);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(button);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard16(modifiers);
    out.writePad(2);
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
    return 12;
  }

  public static class UngrabButtonBuilder {
    public UngrabButton.UngrabButtonBuilder button(ButtonIndex button) {
      this.button = (byte) button.getValue();
      return this;
    }

    public UngrabButton.UngrabButtonBuilder button(byte button) {
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

    public UngrabButton.UngrabButtonBuilder modifiersEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modifiers((short) m.enableFor(modifiers));
      }
      return this;
    }

    public UngrabButton.UngrabButtonBuilder modifiersDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modifiers((short) m.disableFor(modifiers));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
