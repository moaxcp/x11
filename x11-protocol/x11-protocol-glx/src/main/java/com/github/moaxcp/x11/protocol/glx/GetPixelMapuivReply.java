package com.github.moaxcp.x11.protocol.glx;

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
public class GetPixelMapuivReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private int datum;

  @NonNull
  private IntList data;

  public static GetPixelMapuivReply readGetPixelMapuivReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetPixelMapuivReply.GetPixelMapuivReplyBuilder javaBuilder = GetPixelMapuivReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(4);
    int n = in.readCard32();
    int datum = in.readCard32();
    byte[] pad7 = in.readPad(12);
    IntList data = in.readCard32((int) (Integer.toUnsignedLong(n)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.datum(datum);
    javaBuilder.data(data.toImmutable());
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
    out.writePad(4);
    int n = data.size();
    out.writeCard32(n);
    out.writeCard32(datum);
    out.writePad(12);
    out.writeCard32(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetPixelMapuivReplyBuilder {
    public int getSize() {
      return 32 + 4 * data.size();
    }
  }
}
