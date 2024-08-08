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
public class PrintGetOneAttributesReply implements XReply {
  public static final String PLUGIN_NAME = "xprint";

  private short sequenceNumber;

  @NonNull
  private ByteList value;

  public static PrintGetOneAttributesReply readPrintGetOneAttributesReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    PrintGetOneAttributesReply.PrintGetOneAttributesReplyBuilder javaBuilder = PrintGetOneAttributesReply.builder();
    int length = in.readCard32();
    int valueLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    ByteList value = in.readChar((int) (Integer.toUnsignedLong(valueLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.value(value.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetOneAttributesReplyBuilder {
    public int getSize() {
      return 32 + 1 * value.size();
    }
  }
}
