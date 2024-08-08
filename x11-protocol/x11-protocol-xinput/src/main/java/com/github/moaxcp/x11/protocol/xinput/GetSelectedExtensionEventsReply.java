package com.github.moaxcp.x11.protocol.xinput;

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
public class GetSelectedExtensionEventsReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private IntList thisClasses;

  @NonNull
  private IntList allClasses;

  public static GetSelectedExtensionEventsReply readGetSelectedExtensionEventsReply(
      byte xiReplyType, short sequenceNumber, X11Input in) throws IOException {
    GetSelectedExtensionEventsReply.GetSelectedExtensionEventsReplyBuilder javaBuilder = GetSelectedExtensionEventsReply.builder();
    int length = in.readCard32();
    short numThisClasses = in.readCard16();
    short numAllClasses = in.readCard16();
    byte[] pad6 = in.readPad(20);
    IntList thisClasses = in.readCard32(Short.toUnsignedInt(numThisClasses));
    IntList allClasses = in.readCard32(Short.toUnsignedInt(numAllClasses));
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.thisClasses(thisClasses.toImmutable());
    javaBuilder.allClasses(allClasses.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short numThisClasses = (short) thisClasses.size();
    out.writeCard16(numThisClasses);
    short numAllClasses = (short) allClasses.size();
    out.writeCard16(numAllClasses);
    out.writePad(20);
    out.writeCard32(thisClasses);
    out.writeCard32(allClasses);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * thisClasses.size() + 4 * allClasses.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetSelectedExtensionEventsReplyBuilder {
    public int getSize() {
      return 32 + 4 * thisClasses.size() + 4 * allClasses.size();
    }
  }
}
