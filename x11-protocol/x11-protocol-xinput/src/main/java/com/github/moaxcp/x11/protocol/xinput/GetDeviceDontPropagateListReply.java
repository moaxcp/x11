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
public class GetDeviceDontPropagateListReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private IntList classes;

  public static GetDeviceDontPropagateListReply readGetDeviceDontPropagateListReply(
      byte xiReplyType, short sequenceNumber, X11Input in) throws IOException {
    GetDeviceDontPropagateListReply.GetDeviceDontPropagateListReplyBuilder javaBuilder = GetDeviceDontPropagateListReply.builder();
    int length = in.readCard32();
    short numClasses = in.readCard16();
    byte[] pad5 = in.readPad(22);
    IntList classes = in.readCard32(Short.toUnsignedInt(numClasses));
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.classes(classes.toImmutable());
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
    short numClasses = (short) classes.size();
    out.writeCard16(numClasses);
    out.writePad(22);
    out.writeCard32(classes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * classes.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceDontPropagateListReplyBuilder {
    public int getSize() {
      return 32 + 4 * classes.size();
    }
  }
}
