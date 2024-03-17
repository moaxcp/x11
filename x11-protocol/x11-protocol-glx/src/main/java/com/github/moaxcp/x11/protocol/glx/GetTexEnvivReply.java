package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetTexEnvivReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private int datum;

  @NonNull
  private List<Integer> data;

  public static GetTexEnvivReply readGetTexEnvivReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetTexEnvivReply.GetTexEnvivReplyBuilder javaBuilder = GetTexEnvivReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(4);
    int n = in.readCard32();
    int datum = in.readInt32();
    byte[] pad7 = in.readPad(12);
    List<Integer> data = in.readInt32((int) (Integer.toUnsignedLong(n)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.datum(datum);
    javaBuilder.data(data);
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
    out.writePad(4);
    int n = data.size();
    out.writeCard32(n);
    out.writeInt32(datum);
    out.writePad(12);
    out.writeInt32(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetTexEnvivReplyBuilder {
    public int getSize() {
      return 32 + 4 * data.size();
    }
  }
}
