package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SwapBuffersReply implements XReply {
  public static final String PLUGIN_NAME = "dri2";

  private short sequenceNumber;

  private int swapHi;

  private int swapLo;

  public static SwapBuffersReply readSwapBuffersReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    SwapBuffersReply.SwapBuffersReplyBuilder javaBuilder = SwapBuffersReply.builder();
    int length = in.readCard32();
    int swapHi = in.readCard32();
    int swapLo = in.readCard32();
    in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.swapHi(swapHi);
    javaBuilder.swapLo(swapLo);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(swapHi);
    out.writeCard32(swapLo);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SwapBuffersReplyBuilder {
    public int getSize() {
      return 16;
    }
  }
}
