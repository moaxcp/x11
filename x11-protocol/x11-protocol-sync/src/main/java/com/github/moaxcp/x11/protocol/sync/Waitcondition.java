package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Waitcondition implements XStruct {
  public static final String PLUGIN_NAME = "sync";

  @NonNull
  private Trigger trigger;

  @NonNull
  private Int64 eventThreshold;

  public static Waitcondition readWaitcondition(X11Input in) throws IOException {
    Waitcondition.WaitconditionBuilder javaBuilder = Waitcondition.builder();
    Trigger trigger = Trigger.readTrigger(in);
    Int64 eventThreshold = Int64.readInt64(in);
    javaBuilder.trigger(trigger);
    javaBuilder.eventThreshold(eventThreshold);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    trigger.write(out);
    eventThreshold.write(out);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class WaitconditionBuilder {
    public int getSize() {
      return 28;
    }
  }
}
