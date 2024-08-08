package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class PrintQueryScreensReply implements XReply {
  public static final String PLUGIN_NAME = "xprint";

  private short sequenceNumber;

  @NonNull
  private IntList roots;

  public static PrintQueryScreensReply readPrintQueryScreensReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    PrintQueryScreensReply.PrintQueryScreensReplyBuilder javaBuilder = PrintQueryScreensReply.builder();
    int length = in.readCard32();
    int listCount = in.readCard32();
    byte[] pad5 = in.readPad(20);
    IntList roots = in.readCard32((int) (Integer.toUnsignedLong(listCount)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.roots(roots.toImmutable());
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
    int listCount = roots.size();
    out.writeCard32(listCount);
    out.writePad(20);
    out.writeCard32(roots);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * roots.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintQueryScreensReplyBuilder {
    public int getSize() {
      return 32 + 4 * roots.size();
    }
  }
}
