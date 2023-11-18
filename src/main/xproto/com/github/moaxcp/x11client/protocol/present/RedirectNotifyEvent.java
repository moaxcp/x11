package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.xproto.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RedirectNotifyEvent implements XGenericEvent, PresentObject {
  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private boolean updateWindow;

  private int event;

  private int eventWindow;

  private int window;

  private int pixmap;

  private int serial;

  private int validRegion;

  private int updateRegion;

  @NonNull
  private Rectangle validRect;

  @NonNull
  private Rectangle updateRect;

  private short xOff;

  private short yOff;

  private int targetCrtc;

  private int waitFence;

  private int idleFence;

  private int options;

  private long targetMsc;

  private long divisor;

  private long remainder;

  @NonNull
  private List<Notify> notifies;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static RedirectNotifyEvent readRedirectNotifyEvent(byte firstEventOffset,
      boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType,
      X11Input in) throws IOException {
    RedirectNotifyEvent.RedirectNotifyEventBuilder javaBuilder = RedirectNotifyEvent.builder();
    int javaStart = 10;
    boolean updateWindow = in.readBool();
    javaStart += 1;
    byte[] pad6 = in.readPad(1);
    javaStart += 1;
    int event = in.readCard32();
    javaStart += 4;
    int eventWindow = in.readCard32();
    javaStart += 4;
    int window = in.readCard32();
    javaStart += 4;
    int pixmap = in.readCard32();
    javaStart += 4;
    int serial = in.readCard32();
    javaStart += 4;
    int validRegion = in.readCard32();
    javaStart += 4;
    int updateRegion = in.readCard32();
    javaStart += 4;
    Rectangle validRect = Rectangle.readRectangle(in);
    javaStart += validRect.getSize();
    Rectangle updateRect = Rectangle.readRectangle(in);
    javaStart += updateRect.getSize();
    short xOff = in.readInt16();
    javaStart += 2;
    short yOff = in.readInt16();
    javaStart += 2;
    int targetCrtc = in.readCard32();
    javaStart += 4;
    int waitFence = in.readCard32();
    javaStart += 4;
    int idleFence = in.readCard32();
    javaStart += 4;
    int options = in.readCard32();
    javaStart += 4;
    byte[] pad22 = in.readPad(4);
    javaStart += 4;
    long targetMsc = in.readCard64();
    javaStart += 8;
    long divisor = in.readCard64();
    javaStart += 8;
    long remainder = in.readCard64();
    javaStart += 8;
    List<Notify> notifies = new ArrayList<>(length - javaStart);
    while(javaStart < length * 4) {
      Notify baseObject = Notify.readNotify(in);
      notifies.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.updateWindow(updateWindow);
    javaBuilder.event(event);
    javaBuilder.eventWindow(eventWindow);
    javaBuilder.window(window);
    javaBuilder.pixmap(pixmap);
    javaBuilder.serial(serial);
    javaBuilder.validRegion(validRegion);
    javaBuilder.updateRegion(updateRegion);
    javaBuilder.validRect(validRect);
    javaBuilder.updateRect(updateRect);
    javaBuilder.xOff(xOff);
    javaBuilder.yOff(yOff);
    javaBuilder.targetCrtc(targetCrtc);
    javaBuilder.waitFence(waitFence);
    javaBuilder.idleFence(idleFence);
    javaBuilder.options(options);
    javaBuilder.targetMsc(targetMsc);
    javaBuilder.divisor(divisor);
    javaBuilder.remainder(remainder);
    javaBuilder.notifies(notifies);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength() - 32);
    out.writeCard16(eventType);
    out.writeBool(updateWindow);
    out.writePad(1);
    out.writeCard32(event);
    out.writeCard32(eventWindow);
    out.writeCard32(window);
    out.writeCard32(pixmap);
    out.writeCard32(serial);
    out.writeCard32(validRegion);
    out.writeCard32(updateRegion);
    validRect.write(out);
    updateRect.write(out);
    out.writeInt16(xOff);
    out.writeInt16(yOff);
    out.writeCard32(targetCrtc);
    out.writeCard32(waitFence);
    out.writeCard32(idleFence);
    out.writeCard32(options);
    out.writePad(4);
    out.writeCard64(targetMsc);
    out.writeCard64(divisor);
    out.writeCard64(remainder);
    for(Notify t : notifies) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 104 + XObject.sizeOf(notifies);
  }

  public static class RedirectNotifyEventBuilder {
    public int getSize() {
      return 104 + XObject.sizeOf(notifies);
    }
  }
}
