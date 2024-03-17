package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateModeReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private short sequenceNumber;

  private int mode;

  public static CreateModeReply readCreateModeReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    CreateModeReply.CreateModeReplyBuilder javaBuilder = CreateModeReply.builder();
    int length = in.readCard32();
    int mode = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(mode);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateModeReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
