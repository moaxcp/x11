package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIGetFocusReply implements XReply, XinputObject {
  private short sequenceNumber;

  private int focus;

  public static XIGetFocusReply readXIGetFocusReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    XIGetFocusReply.XIGetFocusReplyBuilder javaBuilder = XIGetFocusReply.builder();
    int length = in.readCard32();
    int focus = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.focus(focus);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(focus);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class XIGetFocusReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
