package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.BooleanList;

@Value
@Builder
public class GetBooleanvReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private boolean datum;

  @NonNull
  private BooleanList data;

  public static GetBooleanvReply readGetBooleanvReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetBooleanvReply.GetBooleanvReplyBuilder javaBuilder = GetBooleanvReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(4);
    int n = in.readCard32();
    boolean datum = in.readBool();
    byte[] pad7 = in.readPad(15);
    BooleanList data = in.readBool((int) (Integer.toUnsignedLong(n)));
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
    out.writeBool(datum);
    out.writePad(15);
    out.writeBool(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetBooleanvReplyBuilder {
    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
