package com.github.moaxcp.x11.protocol.xproto;

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
public class GetMotionEventsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private ImmutableList<Timecoord> events;

  public static GetMotionEventsReply readGetMotionEventsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetMotionEventsReply.GetMotionEventsReplyBuilder javaBuilder = GetMotionEventsReply.builder();
    int length = in.readCard32();
    int eventsLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<Timecoord> events = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(eventsLen)));
    for(int i = 0; i < Integer.toUnsignedLong(eventsLen); i++) {
      events.add(Timecoord.readTimecoord(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.events(events.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetMotionEventsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(events);
    }
  }
}
