package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.render.SubPixel;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ScreenChangeNotifyEvent implements XEvent, RandrObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte rotation;

  private short sequenceNumber;

  private int timestamp;

  private int configTimestamp;

  private int root;

  private int requestWindow;

  private short sizeID;

  private short subpixelOrder;

  private short width;

  private short height;

  private short mwidth;

  private short mheight;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ScreenChangeNotifyEvent readScreenChangeNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder javaBuilder = ScreenChangeNotifyEvent.builder();
    byte rotation = in.readCard8();
    short sequenceNumber = in.readCard16();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    int root = in.readCard32();
    int requestWindow = in.readCard32();
    short sizeID = in.readCard16();
    short subpixelOrder = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    short mwidth = in.readCard16();
    short mheight = in.readCard16();
    javaBuilder.rotation(rotation);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.root(root);
    javaBuilder.requestWindow(requestWindow);
    javaBuilder.sizeID(sizeID);
    javaBuilder.subpixelOrder(subpixelOrder);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.mwidth(mwidth);
    javaBuilder.mheight(mheight);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(rotation);
    out.writeCard16(sequenceNumber);
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    out.writeCard32(root);
    out.writeCard32(requestWindow);
    out.writeCard16(sizeID);
    out.writeCard16(subpixelOrder);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(mwidth);
    out.writeCard16(mheight);
  }

  public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
    for(Rotation m : maskEnums) {
      if(!m.isEnabled(rotation)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class ScreenChangeNotifyEventBuilder {
    public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        if(!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder rotationEnable(
        Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((byte) m.enableFor(rotation));
      }
      return this;
    }

    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder rotationDisable(
        Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((byte) m.disableFor(rotation));
      }
      return this;
    }

    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder subpixelOrder(
        SubPixel subpixelOrder) {
      this.subpixelOrder = (short) subpixelOrder.getValue();
      return this;
    }

    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder subpixelOrder(
        short subpixelOrder) {
      this.subpixelOrder = subpixelOrder;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
