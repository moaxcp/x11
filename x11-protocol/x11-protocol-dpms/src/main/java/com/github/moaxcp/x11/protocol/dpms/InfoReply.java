package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InfoReply implements XReply {
  public static final String PLUGIN_NAME = "dpms";

  private short sequenceNumber;

  private short powerLevel;

  private boolean state;

  public static InfoReply readInfoReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    InfoReply.InfoReplyBuilder javaBuilder = InfoReply.builder();
    int length = in.readCard32();
    short powerLevel = in.readCard16();
    boolean state = in.readBool();
    byte[] pad6 = in.readPad(21);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.powerLevel(powerLevel);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(powerLevel);
    out.writeBool(state);
    out.writePad(21);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class InfoReplyBuilder {
    public InfoReply.InfoReplyBuilder powerLevel(DPMSMode powerLevel) {
      this.powerLevel = (short) powerLevel.getValue();
      return this;
    }

    public InfoReply.InfoReplyBuilder powerLevel(short powerLevel) {
      this.powerLevel = powerLevel;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
