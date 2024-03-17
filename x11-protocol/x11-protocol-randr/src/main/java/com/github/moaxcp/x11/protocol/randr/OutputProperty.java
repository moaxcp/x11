package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.Property;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OutputProperty implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";

  private int window;

  private int output;

  private int atom;

  private int timestamp;

  private byte status;

  public static OutputProperty readOutputProperty(X11Input in) throws IOException {
    OutputProperty.OutputPropertyBuilder javaBuilder = OutputProperty.builder();
    int window = in.readCard32();
    int output = in.readCard32();
    int atom = in.readCard32();
    int timestamp = in.readCard32();
    byte status = in.readCard8();
    byte[] pad5 = in.readPad(11);
    javaBuilder.window(window);
    javaBuilder.output(output);
    javaBuilder.atom(atom);
    javaBuilder.timestamp(timestamp);
    javaBuilder.status(status);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(window);
    out.writeCard32(output);
    out.writeCard32(atom);
    out.writeCard32(timestamp);
    out.writeCard8(status);
    out.writePad(11);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class OutputPropertyBuilder {
    public OutputProperty.OutputPropertyBuilder status(Property status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public OutputProperty.OutputPropertyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
