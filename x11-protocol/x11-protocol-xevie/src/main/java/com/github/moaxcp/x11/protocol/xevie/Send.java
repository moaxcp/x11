package com.github.moaxcp.x11.protocol.xevie;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Send implements TwoWayRequest<SendReply> {
  public static final String PLUGIN_NAME = "xevie";

  public static final byte OPCODE = 3;

  @NonNull
  private Event event;

  private int dataType;

  public XReplyFunction<SendReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SendReply.readSendReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Send readSend(X11Input in) throws IOException {
    Send.SendBuilder javaBuilder = Send.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    Event event = Event.readEvent(in);
    int dataType = in.readCard32();
    byte[] pad5 = in.readPad(64);
    javaBuilder.event(event);
    javaBuilder.dataType(dataType);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    event.write(out);
    out.writeCard32(dataType);
    out.writePad(64);
  }

  @Override
  public int getSize() {
    return 104;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SendBuilder {
    public int getSize() {
      return 104;
    }
  }
}
