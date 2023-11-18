package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetOutputPrimaryReply implements XReply, RandrObject {
  private short sequenceNumber;

  private int output;

  public static GetOutputPrimaryReply readGetOutputPrimaryReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetOutputPrimaryReply.GetOutputPrimaryReplyBuilder javaBuilder = GetOutputPrimaryReply.builder();
    int length = in.readCard32();
    int output = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.output(output);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(output);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetOutputPrimaryReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
