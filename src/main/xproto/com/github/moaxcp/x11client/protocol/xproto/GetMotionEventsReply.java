package com.github.moaxcp.x11client.protocol.xproto;

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
public class GetMotionEventsReply implements XReply, XprotoObject {
  private short sequenceNumber;

  @NonNull
  private List<Timecoord> events;

  public static GetMotionEventsReply readGetMotionEventsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetMotionEventsReply.GetMotionEventsReplyBuilder javaBuilder = GetMotionEventsReply.builder();
    int length = in.readCard32();
    int eventsLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Timecoord> events = new ArrayList<>((int) (Integer.toUnsignedLong(eventsLen)));
    for(int i = 0; i < Integer.toUnsignedLong(eventsLen); i++) {
      events.add(Timecoord.readTimecoord(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.events(events);
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
    int eventsLen = events.size();
    out.writeCard32(eventsLen);
    out.writePad(20);
    for(Timecoord t : events) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(events);
  }

  public static class GetMotionEventsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(events);
    }
  }
}
