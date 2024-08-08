package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class GetBuffersReply implements XReply {
  public static final String PLUGIN_NAME = "dri2";

  private short sequenceNumber;

  private int width;

  private int height;

  @NonNull
  private ImmutableList<DRI2Buffer> buffers;

  public static GetBuffersReply readGetBuffersReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetBuffersReply.GetBuffersReplyBuilder javaBuilder = GetBuffersReply.builder();
    int length = in.readCard32();
    int width = in.readCard32();
    int height = in.readCard32();
    int count = in.readCard32();
    byte[] pad7 = in.readPad(12);
    MutableList<DRI2Buffer> buffers = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(count)));
    for(int i = 0; i < Integer.toUnsignedLong(count); i++) {
      buffers.add(DRI2Buffer.readDRI2Buffer(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.buffers(buffers.toImmutable());
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
    out.writeCard32(width);
    out.writeCard32(height);
    int count = buffers.size();
    out.writeCard32(count);
    out.writePad(12);
    for(DRI2Buffer t : buffers) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(buffers);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetBuffersReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(buffers);
    }
  }
}
