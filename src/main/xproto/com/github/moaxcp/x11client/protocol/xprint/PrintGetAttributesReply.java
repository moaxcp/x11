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
public class PrintGetAttributesReply implements XReply, XprintObject {
  private short sequenceNumber;

  @NonNull
  private List<Byte> attributes;

  public static PrintGetAttributesReply readPrintGetAttributesReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    PrintGetAttributesReply.PrintGetAttributesReplyBuilder javaBuilder = PrintGetAttributesReply.builder();
    int length = in.readCard32();
    int stringLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Byte> attributes = in.readChar((int) (Integer.toUnsignedLong(stringLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.attributes(attributes);
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
    int stringLen = attributes.size();
    out.writeCard32(stringLen);
    out.writePad(20);
    out.writeChar(attributes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * attributes.size();
  }

  public static class PrintGetAttributesReplyBuilder {
    public int getSize() {
      return 32 + 1 * attributes.size();
    }
  }
}
