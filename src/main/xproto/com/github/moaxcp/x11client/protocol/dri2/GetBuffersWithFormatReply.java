package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetBuffersWithFormatReply implements XReply, Dri2Object {
  private short sequenceNumber;

  private int width;

  private int height;

  @NonNull
  private List<DRI2Buffer> buffers;

  public static GetBuffersWithFormatReply readGetBuffersWithFormatReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetBuffersWithFormatReply.GetBuffersWithFormatReplyBuilder javaBuilder = GetBuffersWithFormatReply.builder();
    int length = in.readCard32();
    int width = in.readCard32();
    int height = in.readCard32();
    int count = in.readCard32();
    byte[] pad7 = in.readPad(12);
    List<DRI2Buffer> buffers = new ArrayList<>((int) (Integer.toUnsignedLong(count)));
    for(int i = 0; i < Integer.toUnsignedLong(count); i++) {
      buffers.add(DRI2Buffer.readDRI2Buffer(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.buffers(buffers);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
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

  public static class GetBuffersWithFormatReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(buffers);
    }
  }
}
