package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryAlarm implements TwoWayRequest<QueryAlarmReply> {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 10;

  private int alarm;

  public XReplyFunction<QueryAlarmReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryAlarmReply.readQueryAlarmReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryAlarm readQueryAlarm(X11Input in) throws IOException {
    QueryAlarm.QueryAlarmBuilder javaBuilder = QueryAlarm.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int alarm = in.readCard32();
    javaBuilder.alarm(alarm);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(alarm);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryAlarmBuilder {
    public int getSize() {
      return 8;
    }
  }
}
