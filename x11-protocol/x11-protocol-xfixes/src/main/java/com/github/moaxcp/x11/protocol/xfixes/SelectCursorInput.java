package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SelectCursorInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 3;

  private int window;

  private int eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectCursorInput readSelectCursorInput(X11Input in) throws IOException {
    SelectCursorInput.SelectCursorInputBuilder javaBuilder = SelectCursorInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int eventMask = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(eventMask);
  }

  public boolean isEventMaskEnabled(@NonNull CursorNotifyMask... maskEnums) {
    for(CursorNotifyMask m : maskEnums) {
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

  public static class SelectCursorInputBuilder {
    public boolean isEventMaskEnabled(@NonNull CursorNotifyMask... maskEnums) {
      for(CursorNotifyMask m : maskEnums) {
        if(!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public SelectCursorInput.SelectCursorInputBuilder eventMaskEnable(
        CursorNotifyMask... maskEnums) {
      for(CursorNotifyMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SelectCursorInput.SelectCursorInputBuilder eventMaskDisable(
        CursorNotifyMask... maskEnums) {
      for(CursorNotifyMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
