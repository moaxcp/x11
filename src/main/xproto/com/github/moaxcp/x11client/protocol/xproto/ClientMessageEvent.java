package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ClientMessageEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 33;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte format;

  private short sequenceNumber;

  private int window;

  private int type;

  @NonNull
  private ClientMessageDataUnion data;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ClientMessageEvent readClientMessageEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    ClientMessageEvent.ClientMessageEventBuilder javaBuilder = ClientMessageEvent.builder();
    byte format = in.readCard8();
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    int type = in.readCard32();
    ClientMessageDataUnion data = ClientMessageDataUnion.readClientMessageDataUnion(in, format);
    javaBuilder.format(format);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.type(type);
    javaBuilder.data(data);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(format);
    out.writeCard16(sequenceNumber);
    out.writeCard32(window);
    out.writeCard32(type);
    data.write(out);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class ClientMessageEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
