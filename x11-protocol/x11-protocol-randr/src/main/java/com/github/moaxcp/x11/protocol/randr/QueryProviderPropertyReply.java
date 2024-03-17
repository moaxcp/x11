package com.github.moaxcp.x11.protocol.randr;

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
public class QueryProviderPropertyReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private short sequenceNumber;

  private boolean pending;

  private boolean range;

  private boolean immutable;

  @NonNull
  private List<Integer> validValues;

  public static QueryProviderPropertyReply readQueryProviderPropertyReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder javaBuilder = QueryProviderPropertyReply.builder();
    int length = in.readCard32();
    boolean pending = in.readBool();
    boolean range = in.readBool();
    boolean immutable = in.readBool();
    byte[] pad7 = in.readPad(21);
    List<Integer> validValues = in.readInt32((int) (Integer.toUnsignedLong(length)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.pending(pending);
    javaBuilder.range(range);
    javaBuilder.immutable(immutable);
    javaBuilder.validValues(validValues);
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
    int length = validValues.size();
    out.writeCard32(getLength());
    out.writeBool(pending);
    out.writeBool(range);
    out.writeBool(immutable);
    out.writePad(21);
    out.writeInt32(validValues);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * validValues.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryProviderPropertyReplyBuilder {
    public int getSize() {
      return 32 + 4 * validValues.size();
    }
  }
}
