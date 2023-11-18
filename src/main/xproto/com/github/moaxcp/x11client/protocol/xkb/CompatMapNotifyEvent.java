package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CompatMapNotifyEvent implements XEvent, XkbObject {
  public static final byte NUMBER = 7;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private byte changedGroups;

  private short firstSI;

  private short nSI;

  private short nTotalSI;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CompatMapNotifyEvent readCompatMapNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    CompatMapNotifyEvent.CompatMapNotifyEventBuilder javaBuilder = CompatMapNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte changedGroups = in.readCard8();
    short firstSI = in.readCard16();
    short nSI = in.readCard16();
    short nTotalSI = in.readCard16();
    byte[] pad9 = in.readPad(16);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.changedGroups(changedGroups);
    javaBuilder.firstSI(firstSI);
    javaBuilder.nSI(nSI);
    javaBuilder.nTotalSI(nTotalSI);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writeCard8(changedGroups);
    out.writeCard16(firstSI);
    out.writeCard16(nSI);
    out.writeCard16(nTotalSI);
    out.writePad(16);
  }

  public boolean isChangedGroupsEnabled(@NonNull SetOfGroup... maskEnums) {
    for(SetOfGroup m : maskEnums) {
      if(!m.isEnabled(changedGroups)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class CompatMapNotifyEventBuilder {
    public boolean isChangedGroupsEnabled(@NonNull SetOfGroup... maskEnums) {
      for(SetOfGroup m : maskEnums) {
        if(!m.isEnabled(changedGroups)) {
          return false;
        }
      }
      return true;
    }

    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder changedGroupsEnable(
        SetOfGroup... maskEnums) {
      for(SetOfGroup m : maskEnums) {
        changedGroups((byte) m.enableFor(changedGroups));
      }
      return this;
    }

    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder changedGroupsDisable(
        SetOfGroup... maskEnums) {
      for(SetOfGroup m : maskEnums) {
        changedGroups((byte) m.disableFor(changedGroups));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
