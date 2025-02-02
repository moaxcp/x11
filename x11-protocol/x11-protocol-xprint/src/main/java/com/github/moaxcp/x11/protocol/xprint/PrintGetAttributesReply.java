package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class PrintGetAttributesReply implements XReply {
  public static final String PLUGIN_NAME = "xprint";

  private short sequenceNumber;

  @NonNull
  private ByteList attributes;

  public static PrintGetAttributesReply readPrintGetAttributesReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    PrintGetAttributesReply.PrintGetAttributesReplyBuilder javaBuilder = PrintGetAttributesReply.builder();
    int length = in.readCard32();
    int stringLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    ByteList attributes = in.readChar((int) (Integer.toUnsignedLong(stringLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.attributes(attributes.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetAttributesReplyBuilder {
    public int getSize() {
      return 32 + 1 * attributes.size();
    }
  }
}
