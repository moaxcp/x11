package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class BarrierHitEvent implements XGenericEvent, XinputObject {
  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private short deviceid;

  private int time;

  private int eventid;

  private int root;

  private int event;

  private int barrier;

  private int dtime;

  private int flags;

  private short sourceid;

  private int rootX;

  private int rootY;

  @NonNull
  private Fp3232 dx;

  @NonNull
  private Fp3232 dy;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static BarrierHitEvent readBarrierHitEvent(byte firstEventOffset, boolean sentEvent,
      byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws
      IOException {
    BarrierHitEvent.BarrierHitEventBuilder javaBuilder = BarrierHitEvent.builder();
    short deviceid = in.readCard16();
    int time = in.readCard32();
    int eventid = in.readCard32();
    int root = in.readCard32();
    int event = in.readCard32();
    int barrier = in.readCard32();
    int dtime = in.readCard32();
    int flags = in.readCard32();
    short sourceid = in.readCard16();
    byte[] pad14 = in.readPad(2);
    int rootX = in.readInt32();
    int rootY = in.readInt32();
    Fp3232 dx = Fp3232.readFp3232(in);
    Fp3232 dy = Fp3232.readFp3232(in);
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.deviceid(deviceid);
    javaBuilder.time(time);
    javaBuilder.eventid(eventid);
    javaBuilder.root(root);
    javaBuilder.event(event);
    javaBuilder.barrier(barrier);
    javaBuilder.dtime(dtime);
    javaBuilder.flags(flags);
    javaBuilder.sourceid(sourceid);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.dx(dx);
    javaBuilder.dy(dy);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength() - 32);
    out.writeCard16(eventType);
    out.writeCard16(deviceid);
    out.writeCard32(time);
    out.writeCard32(eventid);
    out.writeCard32(root);
    out.writeCard32(event);
    out.writeCard32(barrier);
    out.writeCard32(dtime);
    out.writeCard32(flags);
    out.writeCard16(sourceid);
    out.writePad(2);
    out.writeInt32(rootX);
    out.writeInt32(rootY);
    dx.write(out);
    dy.write(out);
  }

  public boolean isFlagsEnabled(@NonNull BarrierFlags... maskEnums) {
    for(BarrierFlags m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 68;
  }

  public static class BarrierHitEventBuilder {
    public boolean isFlagsEnabled(@NonNull BarrierFlags... maskEnums) {
      for(BarrierFlags m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public BarrierHitEvent.BarrierHitEventBuilder flagsEnable(BarrierFlags... maskEnums) {
      for(BarrierFlags m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public BarrierHitEvent.BarrierHitEventBuilder flagsDisable(BarrierFlags... maskEnums) {
      for(BarrierFlags m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 68;
    }
  }
}
