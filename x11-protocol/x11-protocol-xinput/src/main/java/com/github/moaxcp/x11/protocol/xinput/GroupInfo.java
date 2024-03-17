package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GroupInfo implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private byte base;

  private byte latched;

  private byte locked;

  private byte effective;

  public static GroupInfo readGroupInfo(X11Input in) throws IOException {
    GroupInfo.GroupInfoBuilder javaBuilder = GroupInfo.builder();
    byte base = in.readCard8();
    byte latched = in.readCard8();
    byte locked = in.readCard8();
    byte effective = in.readCard8();
    javaBuilder.base(base);
    javaBuilder.latched(latched);
    javaBuilder.locked(locked);
    javaBuilder.effective(effective);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(base);
    out.writeCard8(latched);
    out.writeCard8(locked);
    out.writeCard8(effective);
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GroupInfoBuilder {
    public int getSize() {
      return 4;
    }
  }
}
