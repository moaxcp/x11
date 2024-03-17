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
public class ConfigureWindow implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 12;

  private int window;

  private short valueMask;

  private int x;

  private int y;

  private int width;

  private int height;

  private int borderWidth;

  private int sibling;

  private int stackMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ConfigureWindow readConfigureWindow(X11Input in) throws IOException {
    ConfigureWindow.ConfigureWindowBuilder javaBuilder = ConfigureWindow.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    short valueMask = in.readCard16();
    byte[] pad5 = in.readPad(2);
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    int borderWidth = 0;
    int sibling = 0;
    int stackMode = 0;
    javaBuilder.window(window);
    javaBuilder.valueMask(valueMask);
    if(ConfigWindow.X.isEnabled(Short.toUnsignedInt(valueMask))) {
      x = in.readInt32();
      javaBuilder.x(x);
    }
    if(ConfigWindow.Y.isEnabled(Short.toUnsignedInt(valueMask))) {
      y = in.readInt32();
      javaBuilder.y(y);
    }
    if(ConfigWindow.WIDTH.isEnabled(Short.toUnsignedInt(valueMask))) {
      width = in.readCard32();
      javaBuilder.width(width);
    }
    if(ConfigWindow.HEIGHT.isEnabled(Short.toUnsignedInt(valueMask))) {
      height = in.readCard32();
      javaBuilder.height(height);
    }
    if(ConfigWindow.BORDER_WIDTH.isEnabled(Short.toUnsignedInt(valueMask))) {
      borderWidth = in.readCard32();
      javaBuilder.borderWidth(borderWidth);
    }
    if(ConfigWindow.SIBLING.isEnabled(Short.toUnsignedInt(valueMask))) {
      sibling = in.readCard32();
      javaBuilder.sibling(sibling);
    }
    if(ConfigWindow.STACK_MODE.isEnabled(Short.toUnsignedInt(valueMask))) {
      stackMode = in.readCard32();
      javaBuilder.stackMode(stackMode);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard16(valueMask);
    out.writePad(2);
    if(ConfigWindow.X.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeInt32(x);
    }
    if(ConfigWindow.Y.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeInt32(y);
    }
    if(ConfigWindow.WIDTH.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(width);
    }
    if(ConfigWindow.HEIGHT.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(height);
    }
    if(ConfigWindow.BORDER_WIDTH.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(borderWidth);
    }
    if(ConfigWindow.SIBLING.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(sibling);
    }
    if(ConfigWindow.STACK_MODE.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(stackMode);
    }
    out.writePadAlign(getSize());
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
    return 12 + (ConfigWindow.X.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.Y.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.WIDTH.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.HEIGHT.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.BORDER_WIDTH.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.SIBLING.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.STACK_MODE.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ConfigureWindowBuilder {
    private ConfigureWindow.ConfigureWindowBuilder valueMask(short valueMask) {
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

    private ConfigureWindow.ConfigureWindowBuilder valueMaskEnable(ConfigWindow... maskEnums) {
      for(ConfigWindow m : maskEnums) {
        valueMask((short) m.enableFor(valueMask));
      }
      return this;
    }

    private ConfigureWindow.ConfigureWindowBuilder valueMaskDisable(ConfigWindow... maskEnums) {
      for(ConfigWindow m : maskEnums) {
        valueMask((short) m.disableFor(valueMask));
      }
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder x(int x) {
      this.x = x;
      valueMaskEnable(ConfigWindow.X);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder y(int y) {
      this.y = y;
      valueMaskEnable(ConfigWindow.Y);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder width(int width) {
      this.width = width;
      valueMaskEnable(ConfigWindow.WIDTH);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder height(int height) {
      this.height = height;
      valueMaskEnable(ConfigWindow.HEIGHT);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder borderWidth(int borderWidth) {
      this.borderWidth = borderWidth;
      valueMaskEnable(ConfigWindow.BORDER_WIDTH);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder sibling(int sibling) {
      this.sibling = sibling;
      valueMaskEnable(ConfigWindow.SIBLING);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder stackMode(int stackMode) {
      this.stackMode = stackMode;
      valueMaskEnable(ConfigWindow.STACK_MODE);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder stackMode(StackMode stackMode) {
      this.stackMode = (int) stackMode.getValue();
      valueMaskEnable(ConfigWindow.STACK_MODE);
      return this;
    }

    public int getSize() {
      return 12 + (ConfigWindow.X.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.Y.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.WIDTH.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.HEIGHT.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.BORDER_WIDTH.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.SIBLING.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.STACK_MODE.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0);
    }
  }
}
