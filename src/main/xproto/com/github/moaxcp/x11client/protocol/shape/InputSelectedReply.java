package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InputSelectedReply implements XReply, ShapeObject {
  private boolean enabled;

  private short sequenceNumber;

  public static InputSelectedReply readInputSelectedReply(byte enabled, short sequenceNumber,
      X11Input in) throws IOException {
    InputSelectedReply.InputSelectedReplyBuilder javaBuilder = InputSelectedReply.builder();
    int length = in.readCard32();
    in.readPad(24);
    javaBuilder.enabled(enabled > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(enabled);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class InputSelectedReplyBuilder {
    public int getSize() {
      return 8;
    }
  }
}
