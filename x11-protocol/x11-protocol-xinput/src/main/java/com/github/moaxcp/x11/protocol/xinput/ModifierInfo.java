package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ModifierInfo implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private int base;

  private int latched;

  private int locked;

  private int effective;

  public static ModifierInfo readModifierInfo(X11Input in) throws IOException {
    ModifierInfo.ModifierInfoBuilder javaBuilder = ModifierInfo.builder();
    int base = in.readCard32();
    int latched = in.readCard32();
    int locked = in.readCard32();
    int effective = in.readCard32();
    javaBuilder.base(base);
    javaBuilder.latched(latched);
    javaBuilder.locked(locked);
    javaBuilder.effective(effective);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(base);
    out.writeCard32(latched);
    out.writeCard32(locked);
    out.writeCard32(effective);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ModifierInfoBuilder {
    public int getSize() {
      return 16;
    }
  }
}
