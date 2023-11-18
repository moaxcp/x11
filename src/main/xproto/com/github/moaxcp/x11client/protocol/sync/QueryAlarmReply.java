package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryAlarmReply implements XReply, SyncObject {
  private short sequenceNumber;

  @NonNull
  private Trigger trigger;

  @NonNull
  private Int64 delta;

  private boolean events;

  private byte state;

  public static QueryAlarmReply readQueryAlarmReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    QueryAlarmReply.QueryAlarmReplyBuilder javaBuilder = QueryAlarmReply.builder();
    int length = in.readCard32();
    Trigger trigger = Trigger.readTrigger(in);
    Int64 delta = Int64.readInt64(in);
    boolean events = in.readBool();
    byte state = in.readCard8();
    byte[] pad8 = in.readPad(2);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.trigger(trigger);
    javaBuilder.delta(delta);
    javaBuilder.events(events);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    trigger.write(out);
    delta.write(out);
    out.writeBool(events);
    out.writeCard8(state);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public static class QueryAlarmReplyBuilder {
    public QueryAlarmReply.QueryAlarmReplyBuilder state(Alarmstate state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public QueryAlarmReply.QueryAlarmReplyBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public int getSize() {
      return 40;
    }
  }
}
