package com.github.moaxcp.x11client.protocol.xinput;

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
public class QueryDeviceStateReply implements XReply, XinputObject {
  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private List<InputState> classes;

  public static QueryDeviceStateReply readQueryDeviceStateReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    QueryDeviceStateReply.QueryDeviceStateReplyBuilder javaBuilder = QueryDeviceStateReply.builder();
    int length = in.readCard32();
    byte numClasses = in.readCard8();
    byte[] pad5 = in.readPad(23);
    List<InputState> classes = new ArrayList<>(Byte.toUnsignedInt(numClasses));
    for(int i = 0; i < Byte.toUnsignedInt(numClasses); i++) {
      classes.add(InputState.readInputState(in));
    }
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.classes(classes);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
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

  public static class QueryDeviceStateReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(classes);
    }
  }
}
