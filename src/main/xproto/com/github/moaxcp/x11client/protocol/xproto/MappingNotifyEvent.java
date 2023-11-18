package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MappingNotifyEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 34;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private byte request;

  private byte firstKeycode;

  private byte count;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static MappingNotifyEvent readMappingNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    MappingNotifyEvent.MappingNotifyEventBuilder javaBuilder = MappingNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    byte request = in.readByte();
    byte firstKeycode = in.readCard8();
    byte count = in.readCard8();
    byte[] pad6 = in.readPad(1);
    byte[] pad7 = in.readPad(24);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.request(request);
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.count(count);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeByte(request);
    out.writeCard8(firstKeycode);
    out.writeCard8(count);
    out.writePad(1);
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class MappingNotifyEventBuilder {
    public MappingNotifyEvent.MappingNotifyEventBuilder request(Mapping request) {
      this.request = (byte) request.getValue();
      return this;
    }

    public MappingNotifyEvent.MappingNotifyEventBuilder request(byte request) {
      this.request = request;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
