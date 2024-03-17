package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ConfigureRequestEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte NUMBER = 23;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte stackMode;

  private short sequenceNumber;

  private int parent;

  private int window;

  private int sibling;

  private short x;

  private short y;

  private short width;

  private short height;

  private short borderWidth;

  private short valueMask;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ConfigureRequestEvent readConfigureRequestEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    ConfigureRequestEvent.ConfigureRequestEventBuilder javaBuilder = ConfigureRequestEvent.builder();
    byte stackMode = in.readByte();
    short sequenceNumber = in.readCard16();
    int parent = in.readCard32();
    int window = in.readCard32();
    int sibling = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short borderWidth = in.readCard16();
    short valueMask = in.readCard16();
    byte[] pad12 = in.readPad(4);
    javaBuilder.stackMode(stackMode);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.parent(parent);
    javaBuilder.window(window);
    javaBuilder.sibling(sibling);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.borderWidth(borderWidth);
    javaBuilder.valueMask(valueMask);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeByte(stackMode);
    out.writeCard16(sequenceNumber);
    out.writeCard32(parent);
    out.writeCard32(window);
    out.writeCard32(sibling);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(borderWidth);
    out.writeCard16(valueMask);
    out.writePad(4);
  }

  public boolean isValueMaskEnabled(@NonNull ConfigWindow... maskEnums) {
    for(ConfigWindow m : maskEnums) {
      if(!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ConfigureRequestEventBuilder {
    public ConfigureRequestEvent.ConfigureRequestEventBuilder stackMode(StackMode stackMode) {
      this.stackMode = (byte) stackMode.getValue();
      return this;
    }

    public ConfigureRequestEvent.ConfigureRequestEventBuilder stackMode(byte stackMode) {
      this.stackMode = stackMode;
      return this;
    }

    private ConfigureRequestEvent.ConfigureRequestEventBuilder valueMask(short valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(@NonNull ConfigWindow... maskEnums) {
      for(ConfigWindow m : maskEnums) {
        if(!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private ConfigureRequestEvent.ConfigureRequestEventBuilder valueMaskEnable(
        ConfigWindow... maskEnums) {
      for(ConfigWindow m : maskEnums) {
        valueMask((short) m.enableFor(valueMask));
      }
      return this;
    }

    private ConfigureRequestEvent.ConfigureRequestEventBuilder valueMaskDisable(
        ConfigWindow... maskEnums) {
      for(ConfigWindow m : maskEnums) {
        valueMask((short) m.disableFor(valueMask));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
