package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.FloatList;

@Value
@Builder
public class GetMinmaxParameterfvReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private float datum;

  @NonNull
  private FloatList data;

  public static GetMinmaxParameterfvReply readGetMinmaxParameterfvReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetMinmaxParameterfvReply.GetMinmaxParameterfvReplyBuilder javaBuilder = GetMinmaxParameterfvReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(4);
    int n = in.readCard32();
    float datum = in.readFloat();
    byte[] pad7 = in.readPad(12);
    FloatList data = in.readFloat((int) (Integer.toUnsignedLong(n)));
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
    out.writeFloat(datum);
    out.writePad(12);
    out.writeFloat(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetMinmaxParameterfvReplyBuilder {
    public int getSize() {
      return 32 + 4 * data.size();
    }
  }
}
