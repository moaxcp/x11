package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SelectInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "screensaver";

  public static final byte OPCODE = 2;

  private int drawable;

  private int eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectInput readSelectInput(X11Input in) throws IOException {
    SelectInput.SelectInputBuilder javaBuilder = SelectInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int eventMask = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(eventMask);
  }

  public boolean isEventMaskEnabled(@NonNull Event... maskEnums) {
    for(Event m : maskEnums) {
      if(!m.isEnabled(eventMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SelectInputBuilder {
    public boolean isEventMaskEnabled(@NonNull Event... maskEnums) {
      for(Event m : maskEnums) {
        if(!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public SelectInput.SelectInputBuilder eventMaskEnable(Event... maskEnums) {
      for(Event m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SelectInput.SelectInputBuilder eventMaskDisable(Event... maskEnums) {
      for(Event m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
