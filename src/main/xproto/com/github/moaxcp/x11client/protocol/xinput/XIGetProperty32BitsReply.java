package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIGetProperty32BitsReply implements XIGetPropertyReply, XinputObject {
  private short sequenceNumber;

  private int type;

  private int bytesAfter;

  private int numItems;

  private byte format;

  public static XIGetProperty32BitsReply readXIGetProperty32BitsReply(byte RESPONSECODE, byte pad1,
      short sequenceNumber, int length, int type, int bytesAfter, int numItems, byte format,
      byte[] pad8, X11Input in) throws IOException {
    XIGetProperty32BitsReply.XIGetProperty32BitsReplyBuilder javaBuilder = XIGetProperty32BitsReply.builder();
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.type(type);
    javaBuilder.bytesAfter(bytesAfter);
    javaBuilder.numItems(numItems);
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(type);
    out.writeCard32(bytesAfter);
    out.writeCard32(numItems);
    out.writeCard8(format);
    out.writePad(11);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class XIGetProperty32BitsReplyBuilder {
    public XIGetProperty32BitsReply.XIGetProperty32BitsReplyBuilder format(PropertyFormat format) {
      this.format = (byte) format.getValue();
      return this;
    }

    public XIGetProperty32BitsReply.XIGetProperty32BitsReplyBuilder format(byte format) {
      this.format = format;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
