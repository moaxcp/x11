package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetWindowAttributesReply implements XReply, XprotoObject {
  private byte backingStore;

  private short sequenceNumber;

  private int visual;

  private short clazz;

  private byte bitGravity;

  private byte winGravity;

  private int backingPlanes;

  private int backingPixel;

  private boolean saveUnder;

  private boolean mapIsInstalled;

  private byte mapState;

  private boolean overrideRedirect;

  private int colormap;

  private int allEventMasks;

  private int yourEventMask;

  private short doNotPropagateMask;

  public static GetWindowAttributesReply readGetWindowAttributesReply(byte backingStore,
      short sequenceNumber, X11Input in) throws IOException {
    GetWindowAttributesReply.GetWindowAttributesReplyBuilder javaBuilder = GetWindowAttributesReply.builder();
    int length = in.readCard32();
    int visual = in.readCard32();
    short clazz = in.readCard16();
    byte bitGravity = in.readCard8();
    byte winGravity = in.readCard8();
    int backingPlanes = in.readCard32();
    int backingPixel = in.readCard32();
    boolean saveUnder = in.readBool();
    boolean mapIsInstalled = in.readBool();
    byte mapState = in.readCard8();
    boolean overrideRedirect = in.readBool();
    int colormap = in.readCard32();
    int allEventMasks = in.readCard32();
    int yourEventMask = in.readCard32();
    short doNotPropagateMask = in.readCard16();
    byte[] pad18 = in.readPad(2);
    javaBuilder.backingStore(backingStore);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.visual(visual);
    javaBuilder.clazz(clazz);
    javaBuilder.bitGravity(bitGravity);
    javaBuilder.winGravity(winGravity);
    javaBuilder.backingPlanes(backingPlanes);
    javaBuilder.backingPixel(backingPixel);
    javaBuilder.saveUnder(saveUnder);
    javaBuilder.mapIsInstalled(mapIsInstalled);
    javaBuilder.mapState(mapState);
    javaBuilder.overrideRedirect(overrideRedirect);
    javaBuilder.colormap(colormap);
    javaBuilder.allEventMasks(allEventMasks);
    javaBuilder.yourEventMask(yourEventMask);
    javaBuilder.doNotPropagateMask(doNotPropagateMask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(backingStore);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(visual);
    out.writeCard16(clazz);
    out.writeCard8(bitGravity);
    out.writeCard8(winGravity);
    out.writeCard32(backingPlanes);
    out.writeCard32(backingPixel);
    out.writeBool(saveUnder);
    out.writeBool(mapIsInstalled);
    out.writeCard8(mapState);
    out.writeBool(overrideRedirect);
    out.writeCard32(colormap);
    out.writeCard32(allEventMasks);
    out.writeCard32(yourEventMask);
    out.writeCard16(doNotPropagateMask);
    out.writePad(2);
  }

  public boolean isAllEventMasksEnabled(@NonNull EventMask... maskEnums) {
    for(EventMask m : maskEnums) {
      if(!m.isEnabled(allEventMasks)) {
        return false;
      }
    }
    return true;
  }

  public boolean isYourEventMaskEnabled(@NonNull EventMask... maskEnums) {
    for(EventMask m : maskEnums) {
      if(!m.isEnabled(yourEventMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isDoNotPropagateMaskEnabled(@NonNull EventMask... maskEnums) {
    for(EventMask m : maskEnums) {
      if(!m.isEnabled(doNotPropagateMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 44;
  }

  public static class GetWindowAttributesReplyBuilder {
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder backingStore(
        BackingStore backingStore) {
      this.backingStore = (byte) backingStore.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder backingStore(
        byte backingStore) {
      this.backingStore = backingStore;
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder clazz(WindowClass clazz) {
      this.clazz = (short) clazz.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder clazz(short clazz) {
      this.clazz = clazz;
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder bitGravity(Gravity bitGravity) {
      this.bitGravity = (byte) bitGravity.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder bitGravity(byte bitGravity) {
      this.bitGravity = bitGravity;
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder winGravity(Gravity winGravity) {
      this.winGravity = (byte) winGravity.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder winGravity(byte winGravity) {
      this.winGravity = winGravity;
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder mapState(MapState mapState) {
      this.mapState = (byte) mapState.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder mapState(byte mapState) {
      this.mapState = mapState;
      return this;
    }

    public boolean isAllEventMasksEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(allEventMasks)) {
          return false;
        }
      }
      return true;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder allEventMasksEnable(
        EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        allEventMasks((int) m.enableFor(allEventMasks));
      }
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder allEventMasksDisable(
        EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        allEventMasks((int) m.disableFor(allEventMasks));
      }
      return this;
    }

    public boolean isYourEventMaskEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(yourEventMask)) {
          return false;
        }
      }
      return true;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder yourEventMaskEnable(
        EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        yourEventMask((int) m.enableFor(yourEventMask));
      }
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder yourEventMaskDisable(
        EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        yourEventMask((int) m.disableFor(yourEventMask));
      }
      return this;
    }

    public boolean isDoNotPropagateMaskEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(doNotPropagateMask)) {
          return false;
        }
      }
      return true;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder doNotPropagateMaskEnable(
        EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        doNotPropagateMask((short) m.enableFor(doNotPropagateMask));
      }
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder doNotPropagateMaskDisable(
        EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        doNotPropagateMask((short) m.disableFor(doNotPropagateMask));
      }
      return this;
    }

    public int getSize() {
      return 44;
    }
  }
}
