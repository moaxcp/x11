package com.github.moaxcp.x11client.protocol.randr;

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
public class GetMonitorsReply implements XReply, RandrObject {
  private short sequenceNumber;

  private int timestamp;

  private int nOutputs;

  @NonNull
  private List<MonitorInfo> monitors;

  public static GetMonitorsReply readGetMonitorsReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetMonitorsReply.GetMonitorsReplyBuilder javaBuilder = GetMonitorsReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    int nMonitors = in.readCard32();
    int nOutputs = in.readCard32();
    byte[] pad7 = in.readPad(12);
    List<MonitorInfo> monitors = new ArrayList<>((int) (Integer.toUnsignedLong(nMonitors)));
    for(int i = 0; i < Integer.toUnsignedLong(nMonitors); i++) {
      monitors.add(MonitorInfo.readMonitorInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.nOutputs(nOutputs);
    javaBuilder.monitors(monitors);
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
    out.writeCard32(timestamp);
    int nMonitors = monitors.size();
    out.writeCard32(nMonitors);
    out.writeCard32(nOutputs);
    out.writePad(12);
    for(MonitorInfo t : monitors) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(monitors);
  }

  public static class GetMonitorsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(monitors);
    }
  }
}
