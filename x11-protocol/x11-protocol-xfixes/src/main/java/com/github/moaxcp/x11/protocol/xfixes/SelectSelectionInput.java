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
public class SelectSelectionInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 2;

  private int window;

  private int selection;

  private int eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectSelectionInput readSelectSelectionInput(X11Input in) throws IOException {
    SelectSelectionInput.SelectSelectionInputBuilder javaBuilder = SelectSelectionInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int selection = in.readCard32();
    int eventMask = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.selection(selection);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(selection);
    out.writeCard32(eventMask);
  }

  public boolean isEventMaskEnabled(@NonNull SelectionEventMask... maskEnums) {
    for(SelectionEventMask m : maskEnums) {
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

  public static class SelectSelectionInputBuilder {
    public boolean isEventMaskEnabled(@NonNull SelectionEventMask... maskEnums) {
      for(SelectionEventMask m : maskEnums) {
        if(!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public SelectSelectionInput.SelectSelectionInputBuilder eventMaskEnable(
        SelectionEventMask... maskEnums) {
      for(SelectionEventMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SelectSelectionInput.SelectSelectionInputBuilder eventMaskDisable(
        SelectionEventMask... maskEnums) {
      for(SelectionEventMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
