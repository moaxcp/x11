package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class RawKeyPressEvent implements XGenericEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private short deviceid;

  private int time;

  private int detail;

  private short sourceid;

  private int flags;

  @NonNull
  private IntList valuatorMask;

  @NonNull
  private ImmutableList<Fp3232> axisvalues;

  @NonNull
  private ImmutableList<Fp3232> axisvaluesRaw;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static RawKeyPressEvent readRawKeyPressEvent(byte firstEventOffset, boolean sentEvent,
      byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws
      IOException {
    RawKeyPressEvent.RawKeyPressEventBuilder javaBuilder = RawKeyPressEvent.builder();
    short deviceid = in.readCard16();
    int time = in.readCard32();
    int detail = in.readCard32();
    short sourceid = in.readCard16();
    short valuatorsLen = in.readCard16();
    int flags = in.readCard32();
    byte[] pad11 = in.readPad(4);
    IntList valuatorMask = in.readCard32(Short.toUnsignedInt(valuatorsLen));
    MutableList<Fp3232> axisvalues = Lists.mutable.withInitialCapacity((int) valuatorMask.sum());
    for(int i = 0; i < (int) valuatorMask.sum(); i++) {
      axisvalues.add(Fp3232.readFp3232(in));
    }
    MutableList<Fp3232> axisvaluesRaw = Lists.mutable.withInitialCapacity((int) valuatorMask.sum());
    for(int i = 0; i < (int) valuatorMask.sum(); i++) {
      axisvaluesRaw.add(Fp3232.readFp3232(in));
    }
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.deviceid(deviceid);
    javaBuilder.time(time);
    javaBuilder.detail(detail);
    javaBuilder.sourceid(sourceid);
    javaBuilder.flags(flags);
    javaBuilder.valuatorMask(valuatorMask.toImmutable());
    javaBuilder.axisvalues(axisvalues.toImmutable());
    javaBuilder.axisvaluesRaw(axisvaluesRaw.toImmutable());

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength() - 32);
    out.writeCard16(eventType);
    out.writeCard16(deviceid);
    out.writeCard32(time);
    out.writeCard32(detail);
    out.writeCard16(sourceid);
    short valuatorsLen = (short) valuatorMask.size();
    out.writeCard16(valuatorsLen);
    out.writeCard32(flags);
    out.writePad(4);
    out.writeCard32(valuatorMask);
    for(Fp3232 t : axisvalues) {
      t.write(out);
    }
    for(Fp3232 t : axisvaluesRaw) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean isFlagsEnabled(@NonNull KeyEventFlags... maskEnums) {
    for(KeyEventFlags m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + 4 * valuatorMask.size() + XObject.sizeOf(axisvalues) + XObject.sizeOf(axisvaluesRaw);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class RawKeyPressEventBuilder {
    public boolean isFlagsEnabled(@NonNull KeyEventFlags... maskEnums) {
      for(KeyEventFlags m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public RawKeyPressEvent.RawKeyPressEventBuilder flagsEnable(KeyEventFlags... maskEnums) {
      for(KeyEventFlags m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public RawKeyPressEvent.RawKeyPressEventBuilder flagsDisable(KeyEventFlags... maskEnums) {
      for(KeyEventFlags m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 32 + 4 * valuatorMask.size() + XObject.sizeOf(axisvalues) + XObject.sizeOf(axisvaluesRaw);
    }
  }
}
