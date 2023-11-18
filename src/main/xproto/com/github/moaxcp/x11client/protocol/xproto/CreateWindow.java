package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateWindow implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 1;

  private byte depth;

  private int wid;

  private int parent;

  private short x;

  private short y;

  private short width;

  private short height;

  private short borderWidth;

  private short clazz;

  private int visual;

  private int valueMask;

  private int backgroundPixmap;

  private int backgroundPixel;

  private int borderPixmap;

  private int borderPixel;

  private int bitGravity;

  private int winGravity;

  private int backingStore;

  private int backingPlanes;

  private int backingPixel;

  private int overrideRedirect;

  private int saveUnder;

  private int eventMask;

  private int doNotPropogateMask;

  private int colormap;

  private int cursor;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateWindow readCreateWindow(X11Input in) throws IOException {
    CreateWindow.CreateWindowBuilder javaBuilder = CreateWindow.builder();
    byte depth = in.readCard8();
    short length = in.readCard16();
    int wid = in.readCard32();
    int parent = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short borderWidth = in.readCard16();
    short clazz = in.readCard16();
    int visual = in.readCard32();
    int valueMask = in.readCard32();
    int backgroundPixmap = 0;
    int backgroundPixel = 0;
    int borderPixmap = 0;
    int borderPixel = 0;
    int bitGravity = 0;
    int winGravity = 0;
    int backingStore = 0;
    int backingPlanes = 0;
    int backingPixel = 0;
    int overrideRedirect = 0;
    int saveUnder = 0;
    int eventMask = 0;
    int doNotPropogateMask = 0;
    int colormap = 0;
    int cursor = 0;
    javaBuilder.depth(depth);
    javaBuilder.wid(wid);
    javaBuilder.parent(parent);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.borderWidth(borderWidth);
    javaBuilder.clazz(clazz);
    javaBuilder.visual(visual);
    javaBuilder.valueMask(valueMask);
    if(Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backgroundPixmap = in.readCard32();
      javaBuilder.backgroundPixmap(backgroundPixmap);
    }
    if(Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backgroundPixel = in.readCard32();
      javaBuilder.backgroundPixel(backgroundPixel);
    }
    if(Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      borderPixmap = in.readCard32();
      javaBuilder.borderPixmap(borderPixmap);
    }
    if(Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      borderPixel = in.readCard32();
      javaBuilder.borderPixel(borderPixel);
    }
    if(Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      bitGravity = in.readCard32();
      javaBuilder.bitGravity(bitGravity);
    }
    if(Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      winGravity = in.readCard32();
      javaBuilder.winGravity(winGravity);
    }
    if(Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backingStore = in.readCard32();
      javaBuilder.backingStore(backingStore);
    }
    if(Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backingPlanes = in.readCard32();
      javaBuilder.backingPlanes(backingPlanes);
    }
    if(Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backingPixel = in.readCard32();
      javaBuilder.backingPixel(backingPixel);
    }
    if(Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      overrideRedirect = in.readCard32();
      javaBuilder.overrideRedirect(overrideRedirect);
    }
    if(Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      saveUnder = in.readCard32();
      javaBuilder.saveUnder(saveUnder);
    }
    if(Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      eventMask = in.readCard32();
      javaBuilder.eventMask(eventMask);
    }
    if(Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      doNotPropogateMask = in.readCard32();
      javaBuilder.doNotPropogateMask(doNotPropogateMask);
    }
    if(Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      colormap = in.readCard32();
      javaBuilder.colormap(colormap);
    }
    if(Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      cursor = in.readCard32();
      javaBuilder.cursor(cursor);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(depth);
    out.writeCard16((short) getLength());
    out.writeCard32(wid);
    out.writeCard32(parent);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(borderWidth);
    out.writeCard16(clazz);
    out.writeCard32(visual);
    out.writeCard32(valueMask);
    if(Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backgroundPixmap);
    }
    if(Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backgroundPixel);
    }
    if(Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(borderPixmap);
    }
    if(Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(borderPixel);
    }
    if(Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(bitGravity);
    }
    if(Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(winGravity);
    }
    if(Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backingStore);
    }
    if(Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backingPlanes);
    }
    if(Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backingPixel);
    }
    if(Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(overrideRedirect);
    }
    if(Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(saveUnder);
    }
    if(Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(eventMask);
    }
    if(Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(doNotPropogateMask);
    }
    if(Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(colormap);
    }
    if(Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(cursor);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(@NonNull Cw... maskEnums) {
    for(Cw m : maskEnums) {
      if(!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isEventMaskEnabled(@NonNull EventMask... maskEnums) {
    for(EventMask m : maskEnums) {
      if(!m.isEnabled(eventMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isDoNotPropogateMaskEnabled(@NonNull EventMask... maskEnums) {
    for(EventMask m : maskEnums) {
      if(!m.isEnabled(doNotPropogateMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public static class CreateWindowBuilder {
    public CreateWindow.CreateWindowBuilder clazz(WindowClass clazz) {
      this.clazz = (short) clazz.getValue();
      return this;
    }

    public CreateWindow.CreateWindowBuilder clazz(short clazz) {
      this.clazz = clazz;
      return this;
    }

    private CreateWindow.CreateWindowBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(@NonNull Cw... maskEnums) {
      for(Cw m : maskEnums) {
        if(!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private CreateWindow.CreateWindowBuilder valueMaskEnable(Cw... maskEnums) {
      for(Cw m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private CreateWindow.CreateWindowBuilder valueMaskDisable(Cw... maskEnums) {
      for(Cw m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public CreateWindow.CreateWindowBuilder backgroundPixmap(int backgroundPixmap) {
      this.backgroundPixmap = backgroundPixmap;
      valueMaskEnable(Cw.BACK_PIXMAP);
      return this;
    }

    public CreateWindow.CreateWindowBuilder backgroundPixel(int backgroundPixel) {
      this.backgroundPixel = backgroundPixel;
      valueMaskEnable(Cw.BACK_PIXEL);
      return this;
    }

    public CreateWindow.CreateWindowBuilder borderPixmap(int borderPixmap) {
      this.borderPixmap = borderPixmap;
      valueMaskEnable(Cw.BORDER_PIXMAP);
      return this;
    }

    public CreateWindow.CreateWindowBuilder borderPixel(int borderPixel) {
      this.borderPixel = borderPixel;
      valueMaskEnable(Cw.BORDER_PIXEL);
      return this;
    }

    public CreateWindow.CreateWindowBuilder bitGravity(int bitGravity) {
      this.bitGravity = bitGravity;
      valueMaskEnable(Cw.BIT_GRAVITY);
      return this;
    }

    public CreateWindow.CreateWindowBuilder bitGravity(Gravity bitGravity) {
      this.bitGravity = (int) bitGravity.getValue();
      valueMaskEnable(Cw.BIT_GRAVITY);
      return this;
    }

    public CreateWindow.CreateWindowBuilder winGravity(int winGravity) {
      this.winGravity = winGravity;
      valueMaskEnable(Cw.WIN_GRAVITY);
      return this;
    }

    public CreateWindow.CreateWindowBuilder winGravity(Gravity winGravity) {
      this.winGravity = (int) winGravity.getValue();
      valueMaskEnable(Cw.WIN_GRAVITY);
      return this;
    }

    public CreateWindow.CreateWindowBuilder backingStore(int backingStore) {
      this.backingStore = backingStore;
      valueMaskEnable(Cw.BACKING_STORE);
      return this;
    }

    public CreateWindow.CreateWindowBuilder backingStore(BackingStore backingStore) {
      this.backingStore = (int) backingStore.getValue();
      valueMaskEnable(Cw.BACKING_STORE);
      return this;
    }

    public CreateWindow.CreateWindowBuilder backingPlanes(int backingPlanes) {
      this.backingPlanes = backingPlanes;
      valueMaskEnable(Cw.BACKING_PLANES);
      return this;
    }

    public CreateWindow.CreateWindowBuilder backingPixel(int backingPixel) {
      this.backingPixel = backingPixel;
      valueMaskEnable(Cw.BACKING_PIXEL);
      return this;
    }

    public CreateWindow.CreateWindowBuilder overrideRedirect(int overrideRedirect) {
      this.overrideRedirect = overrideRedirect;
      valueMaskEnable(Cw.OVERRIDE_REDIRECT);
      return this;
    }

    public CreateWindow.CreateWindowBuilder saveUnder(int saveUnder) {
      this.saveUnder = saveUnder;
      valueMaskEnable(Cw.SAVE_UNDER);
      return this;
    }

    public CreateWindow.CreateWindowBuilder eventMask(int eventMask) {
      this.eventMask = eventMask;
      valueMaskEnable(Cw.EVENT_MASK);
      return this;
    }

    public boolean isEventMaskEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public CreateWindow.CreateWindowBuilder eventMaskEnable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public CreateWindow.CreateWindowBuilder eventMaskDisable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public CreateWindow.CreateWindowBuilder doNotPropogateMask(int doNotPropogateMask) {
      this.doNotPropogateMask = doNotPropogateMask;
      valueMaskEnable(Cw.DONT_PROPAGATE);
      return this;
    }

    public boolean isDoNotPropogateMaskEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(doNotPropogateMask)) {
          return false;
        }
      }
      return true;
    }

    public CreateWindow.CreateWindowBuilder doNotPropogateMaskEnable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        doNotPropogateMask((int) m.enableFor(doNotPropogateMask));
      }
      return this;
    }

    public CreateWindow.CreateWindowBuilder doNotPropogateMaskDisable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        doNotPropogateMask((int) m.disableFor(doNotPropogateMask));
      }
      return this;
    }

    public CreateWindow.CreateWindowBuilder colormap(int colormap) {
      this.colormap = colormap;
      valueMaskEnable(Cw.COLORMAP);
      return this;
    }

    public CreateWindow.CreateWindowBuilder cursor(int cursor) {
      this.cursor = cursor;
      valueMaskEnable(Cw.CURSOR);
      return this;
    }

    public int getSize() {
      return 32 + (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }
  }
}
