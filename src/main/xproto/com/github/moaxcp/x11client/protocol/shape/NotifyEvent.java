package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NotifyEvent implements XEvent, ShapeObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte shapeKind;

  private short sequenceNumber;

  private int affectedWindow;

  private short extentsX;

  private short extentsY;

  private short extentsWidth;

  private short extentsHeight;

  private int serverTime;

  private boolean shaped;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NotifyEvent readNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in)
      throws IOException {
    NotifyEvent.NotifyEventBuilder javaBuilder = NotifyEvent.builder();
    byte shapeKind = in.readCard8();
    short sequenceNumber = in.readCard16();
    int affectedWindow = in.readCard32();
    short extentsX = in.readInt16();
    short extentsY = in.readInt16();
    short extentsWidth = in.readCard16();
    short extentsHeight = in.readCard16();
    int serverTime = in.readCard32();
    boolean shaped = in.readBool();
    byte[] pad10 = in.readPad(11);
    javaBuilder.shapeKind(shapeKind);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.affectedWindow(affectedWindow);
    javaBuilder.extentsX(extentsX);
    javaBuilder.extentsY(extentsY);
    javaBuilder.extentsWidth(extentsWidth);
    javaBuilder.extentsHeight(extentsHeight);
    javaBuilder.serverTime(serverTime);
    javaBuilder.shaped(shaped);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(shapeKind);
    out.writeCard16(sequenceNumber);
    out.writeCard32(affectedWindow);
    out.writeInt16(extentsX);
    out.writeInt16(extentsY);
    out.writeCard16(extentsWidth);
    out.writeCard16(extentsHeight);
    out.writeCard32(serverTime);
    out.writeBool(shaped);
    out.writePad(11);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class NotifyEventBuilder {
    public NotifyEvent.NotifyEventBuilder shapeKind(Sk shapeKind) {
      this.shapeKind = (byte) shapeKind.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder shapeKind(byte shapeKind) {
      this.shapeKind = shapeKind;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
