package com.github.moaxcp.x11.protocol.xinput;

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
public class QueryDeviceStateReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private ImmutableList<InputState> classes;

  public static QueryDeviceStateReply readQueryDeviceStateReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    QueryDeviceStateReply.QueryDeviceStateReplyBuilder javaBuilder = QueryDeviceStateReply.builder();
    int length = in.readCard32();
    byte numClasses = in.readCard8();
    byte[] pad5 = in.readPad(23);
    MutableList<InputState> classes = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(numClasses));
    for(int i = 0; i < Byte.toUnsignedInt(numClasses); i++) {
      classes.add(InputState.readInputState(in));
    }
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
    byte numClasses = (byte) classes.size();
    out.writeCard8(numClasses);
    out.writePad(23);
    for(InputState t : classes) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(classes);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryDeviceStateReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(classes);
    }
  }
}
