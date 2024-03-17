package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.BackingStore;
import com.github.moaxcp.x11.protocol.xproto.Cw;
import com.github.moaxcp.x11.protocol.xproto.EventMask;
import com.github.moaxcp.x11.protocol.xproto.Gravity;
import com.github.moaxcp.x11.protocol.xproto.WindowClass;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetAttributes implements OneWayRequest {
  public static final String PLUGIN_NAME = "screensaver";

  public static final byte OPCODE = 3;

  private int drawable;

  private short x;

  private short y;

  private short width;

  private short height;

  private short borderWidth;

  private byte clazz;

  private byte depth;

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

  public static SetAttributes readSetAttributes(X11Input in) throws IOException {
    SetAttributes.SetAttributesBuilder javaBuilder = SetAttributes.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short borderWidth = in.readCard16();
    byte clazz = in.readByte();
    byte depth = in.readCard8();
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
    javaBuilder.drawable(drawable);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.borderWidth(borderWidth);
    javaBuilder.clazz(clazz);
    javaBuilder.depth(depth);
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(borderWidth);
    out.writeByte(clazz);
    out.writeCard8(depth);
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
    return 28 + (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetAttributesBuilder {
    public SetAttributes.SetAttributesBuilder clazz(WindowClass clazz) {
      this.clazz = (byte) clazz.getValue();
      return this;
    }

    public SetAttributes.SetAttributesBuilder clazz(byte clazz) {
      this.clazz = clazz;
      return this;
    }

    private SetAttributes.SetAttributesBuilder valueMask(int valueMask) {
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

    private SetAttributes.SetAttributesBuilder valueMaskEnable(Cw... maskEnums) {
      for(Cw m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private SetAttributes.SetAttributesBuilder valueMaskDisable(Cw... maskEnums) {
      for(Cw m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder backgroundPixmap(int backgroundPixmap) {
      this.backgroundPixmap = backgroundPixmap;
      valueMaskEnable(Cw.BACK_PIXMAP);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backgroundPixel(int backgroundPixel) {
      this.backgroundPixel = backgroundPixel;
      valueMaskEnable(Cw.BACK_PIXEL);
      return this;
    }

    public SetAttributes.SetAttributesBuilder borderPixmap(int borderPixmap) {
      this.borderPixmap = borderPixmap;
      valueMaskEnable(Cw.BORDER_PIXMAP);
      return this;
    }

    public SetAttributes.SetAttributesBuilder borderPixel(int borderPixel) {
      this.borderPixel = borderPixel;
      valueMaskEnable(Cw.BORDER_PIXEL);
      return this;
    }

    public SetAttributes.SetAttributesBuilder bitGravity(int bitGravity) {
      this.bitGravity = bitGravity;
      valueMaskEnable(Cw.BIT_GRAVITY);
      return this;
    }

    public SetAttributes.SetAttributesBuilder bitGravity(Gravity bitGravity) {
      this.bitGravity = (int) bitGravity.getValue();
      valueMaskEnable(Cw.BIT_GRAVITY);
      return this;
    }

    public SetAttributes.SetAttributesBuilder winGravity(int winGravity) {
      this.winGravity = winGravity;
      valueMaskEnable(Cw.WIN_GRAVITY);
      return this;
    }

    public SetAttributes.SetAttributesBuilder winGravity(Gravity winGravity) {
      this.winGravity = (int) winGravity.getValue();
      valueMaskEnable(Cw.WIN_GRAVITY);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backingStore(int backingStore) {
      this.backingStore = backingStore;
      valueMaskEnable(Cw.BACKING_STORE);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backingStore(BackingStore backingStore) {
      this.backingStore = (int) backingStore.getValue();
      valueMaskEnable(Cw.BACKING_STORE);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backingPlanes(int backingPlanes) {
      this.backingPlanes = backingPlanes;
      valueMaskEnable(Cw.BACKING_PLANES);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backingPixel(int backingPixel) {
      this.backingPixel = backingPixel;
      valueMaskEnable(Cw.BACKING_PIXEL);
      return this;
    }

    public SetAttributes.SetAttributesBuilder overrideRedirect(int overrideRedirect) {
      this.overrideRedirect = overrideRedirect;
      valueMaskEnable(Cw.OVERRIDE_REDIRECT);
      return this;
    }

    public SetAttributes.SetAttributesBuilder saveUnder(int saveUnder) {
      this.saveUnder = saveUnder;
      valueMaskEnable(Cw.SAVE_UNDER);
      return this;
    }

    public SetAttributes.SetAttributesBuilder eventMask(int eventMask) {
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

    public SetAttributes.SetAttributesBuilder eventMaskEnable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder eventMaskDisable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder doNotPropogateMask(int doNotPropogateMask) {
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

    public SetAttributes.SetAttributesBuilder doNotPropogateMaskEnable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        doNotPropogateMask((int) m.enableFor(doNotPropogateMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder doNotPropogateMaskDisable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        doNotPropogateMask((int) m.disableFor(doNotPropogateMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder colormap(int colormap) {
      this.colormap = colormap;
      valueMaskEnable(Cw.COLORMAP);
      return this;
    }

    public SetAttributes.SetAttributesBuilder cursor(int cursor) {
      this.cursor = cursor;
      valueMaskEnable(Cw.CURSOR);
      return this;
    }

    public int getSize() {
      return 28 + (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }
  }
}
