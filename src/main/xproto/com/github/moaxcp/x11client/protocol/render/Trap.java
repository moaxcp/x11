package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Trap implements XStruct, RenderObject {
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

  public static class TrapBuilder {
    public int getSize() {
      return 24;
    }
  }
}
