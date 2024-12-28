package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ListSystemCountersReply implements XReply {
  public static final String PLUGIN_NAME = "sync";

  private short sequenceNumber;

  @NonNull
  private List<Systemcounter> counters;

  public static ListSystemCountersReply readListSystemCountersReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    ListSystemCountersReply.ListSystemCountersReplyBuilder javaBuilder = ListSystemCountersReply.builder();
    int length = in.readCard32();
    int countersLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Systemcounter> counters = new ArrayList<>((int) (Integer.toUnsignedLong(countersLen)));
    for(int i = 0; i < Integer.toUnsignedLong(countersLen); i++) {
      counters.add(Systemcounter.readSystemcounter(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.counters(counters);
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
    int countersLen = counters.size();
    out.writeCard32(countersLen);
    out.writePad(20);
    for(Systemcounter t : counters) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(counters);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListSystemCountersReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(counters);
    }
  }
}
