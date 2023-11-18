package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PrintGetOneAttributesReply implements XReply, XprintObject {
  private short sequenceNumber;

  @NonNull
  private List<Byte> value;

  public static PrintGetOneAttributesReply readPrintGetOneAttributesReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    PrintGetOneAttributesReply.PrintGetOneAttributesReplyBuilder javaBuilder = PrintGetOneAttributesReply.builder();
    int length = in.readCard32();
    int valueLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Byte> value = in.readChar((int) (Integer.toUnsignedLong(valueLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.value(value);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int valueLen = value.size();
    out.writeCard32(valueLen);
    out.writePad(20);
    out.writeChar(value);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * value.size();
  }

  public static class PrintGetOneAttributesReplyBuilder {
    public int getSize() {
      return 32 + 1 * value.size();
    }
  }
}
