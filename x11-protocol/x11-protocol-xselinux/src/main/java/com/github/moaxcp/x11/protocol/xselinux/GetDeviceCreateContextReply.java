package com.github.moaxcp.x11.protocol.xselinux;

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
public class GetDeviceCreateContextReply implements XReply {
  public static final String PLUGIN_NAME = "xselinux";

  private short sequenceNumber;

  @NonNull
  private ByteList context;

  public static GetDeviceCreateContextReply readGetDeviceCreateContextReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetDeviceCreateContextReply.GetDeviceCreateContextReplyBuilder javaBuilder = GetDeviceCreateContextReply.builder();
    int length = in.readCard32();
    int contextLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    ByteList context = in.readChar((int) (Integer.toUnsignedLong(contextLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.context(context.toImmutable());
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
    int contextLen = context.size();
    out.writeCard32(contextLen);
    out.writePad(20);
    out.writeChar(context);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * context.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceCreateContextReplyBuilder {
    public int getSize() {
      return 32 + 1 * context.size();
    }
  }
}
