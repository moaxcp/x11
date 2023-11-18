package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AllocColorReply implements XReply, XprotoObject {
  private short sequenceNumber;

  private short red;

  private short green;

  private short blue;

  private int pixel;

  public static AllocColorReply readAllocColorReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    AllocColorReply.AllocColorReplyBuilder javaBuilder = AllocColorReply.builder();
    int length = in.readCard32();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    byte[] pad7 = in.readPad(2);
    int pixel = in.readCard32();
    in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    javaBuilder.pixel(pixel);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePad(2);
    out.writeCard32(pixel);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class AllocColorReplyBuilder {
    public int getSize() {
      return 20;
    }
  }
}
