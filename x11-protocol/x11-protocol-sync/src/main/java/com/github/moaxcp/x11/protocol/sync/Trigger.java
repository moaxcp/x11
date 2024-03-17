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
public class Trigger implements XStruct {
  public static final String PLUGIN_NAME = "sync";

  private int counter;

  private int waitType;

  @NonNull
  private Int64 waitValue;

  private int testType;

  public static Trigger readTrigger(X11Input in) throws IOException {
    Trigger.TriggerBuilder javaBuilder = Trigger.builder();
    int counter = in.readCard32();
    int waitType = in.readCard32();
    Int64 waitValue = Int64.readInt64(in);
    int testType = in.readCard32();
    javaBuilder.counter(counter);
    javaBuilder.waitType(waitType);
    javaBuilder.waitValue(waitValue);
    javaBuilder.testType(testType);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(counter);
    out.writeCard32(waitType);
    waitValue.write(out);
    out.writeCard32(testType);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class TriggerBuilder {
    public Trigger.TriggerBuilder waitType(Valuetype waitType) {
      this.waitType = (int) waitType.getValue();
      return this;
    }

    public Trigger.TriggerBuilder waitType(int waitType) {
      this.waitType = waitType;
      return this;
    }

    public Trigger.TriggerBuilder testType(Testtype testType) {
      this.testType = (int) testType.getValue();
      return this;
    }

    public Trigger.TriggerBuilder testType(int testType) {
      this.testType = testType;
      return this;
    }

    public int getSize() {
      return 20;
    }
  }
}
