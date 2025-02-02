package com.github.moaxcp.x11.protocol.xproto;

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
public class ListInstalledColormapsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private IntList cmaps;

  public static ListInstalledColormapsReply readListInstalledColormapsReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    ListInstalledColormapsReply.ListInstalledColormapsReplyBuilder javaBuilder = ListInstalledColormapsReply.builder();
    int length = in.readCard32();
    short cmapsLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    IntList cmaps = in.readCard32(Short.toUnsignedInt(cmapsLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.cmaps(cmaps.toImmutable());
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
    short cmapsLen = (short) cmaps.size();
    out.writeCard16(cmapsLen);
    out.writePad(22);
    out.writeCard32(cmaps);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * cmaps.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListInstalledColormapsReplyBuilder {
    public int getSize() {
      return 32 + 4 * cmaps.size();
    }
  }
}
