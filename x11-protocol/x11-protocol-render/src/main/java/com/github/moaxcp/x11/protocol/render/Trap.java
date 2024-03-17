package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Trap implements XStruct {
  public static final String PLUGIN_NAME = "render";

  @NonNull
  private Spanfix top;

  @NonNull
  private Spanfix bot;

  public static Trap readTrap(X11Input in) throws IOException {
    Trap.TrapBuilder javaBuilder = Trap.builder();
    Spanfix top = Spanfix.readSpanfix(in);
    Spanfix bot = Spanfix.readSpanfix(in);
    javaBuilder.top(top);
    javaBuilder.bot(bot);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    top.write(out);
    bot.write(out);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class TrapBuilder {
    public int getSize() {
      return 24;
    }
  }
}
