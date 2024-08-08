package com.github.moaxcp.x11.protocol.dri3;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.LongList;

@Value
@Builder
public class GetSupportedModifiersReply implements XReply {
  public static final String PLUGIN_NAME = "dri3";

  private short sequenceNumber;

  @NonNull
  private LongList windowModifiers;

  @NonNull
  private LongList screenModifiers;

  public static GetSupportedModifiersReply readGetSupportedModifiersReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetSupportedModifiersReply.GetSupportedModifiersReplyBuilder javaBuilder = GetSupportedModifiersReply.builder();
    int length = in.readCard32();
    int numWindowModifiers = in.readCard32();
    int numScreenModifiers = in.readCard32();
    byte[] pad6 = in.readPad(16);
    LongList windowModifiers = in.readCard64((int) (Integer.toUnsignedLong(numWindowModifiers)));
    LongList screenModifiers = in.readCard64((int) (Integer.toUnsignedLong(numScreenModifiers)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.windowModifiers(windowModifiers.toImmutable());
    javaBuilder.screenModifiers(screenModifiers.toImmutable());
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
    int numWindowModifiers = windowModifiers.size();
    out.writeCard32(numWindowModifiers);
    int numScreenModifiers = screenModifiers.size();
    out.writeCard32(numScreenModifiers);
    out.writePad(16);
    out.writeCard64(windowModifiers);
    out.writeCard64(screenModifiers);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 8 * windowModifiers.size() + 8 * screenModifiers.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetSupportedModifiersReplyBuilder {
    public int getSize() {
      return 32 + 8 * windowModifiers.size() + 8 * screenModifiers.size();
    }
  }
}
