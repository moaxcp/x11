package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HierarchyEvent implements XGenericEvent, XinputObject {
  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private short deviceid;

  private int time;

  private int flags;

  @NonNull
  private List<HierarchyInfo> infos;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static HierarchyEvent readHierarchyEvent(byte firstEventOffset, boolean sentEvent,
      byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws
      IOException {
    HierarchyEvent.HierarchyEventBuilder javaBuilder = HierarchyEvent.builder();
    short deviceid = in.readCard16();
    int time = in.readCard32();
    int flags = in.readCard32();
    short numInfos = in.readCard16();
    byte[] pad9 = in.readPad(10);
    List<HierarchyInfo> infos = new ArrayList<>(Short.toUnsignedInt(numInfos));
    for(int i = 0; i < Short.toUnsignedInt(numInfos); i++) {
      infos.add(HierarchyInfo.readHierarchyInfo(in));
    }
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.deviceid(deviceid);
    javaBuilder.time(time);
    javaBuilder.flags(flags);
    javaBuilder.infos(infos);

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
    out.writeCard16(deviceid);
    out.writeCard32(time);
    out.writeCard32(flags);
    short numInfos = (short) infos.size();
    out.writeCard16(numInfos);
    out.writePad(10);
    for(HierarchyInfo t : infos) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean isFlagsEnabled(@NonNull HierarchyMask... maskEnums) {
    for(HierarchyMask m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(infos);
  }

  public static class HierarchyEventBuilder {
    public boolean isFlagsEnabled(@NonNull HierarchyMask... maskEnums) {
      for(HierarchyMask m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public HierarchyEvent.HierarchyEventBuilder flagsEnable(HierarchyMask... maskEnums) {
      for(HierarchyMask m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public HierarchyEvent.HierarchyEventBuilder flagsDisable(HierarchyMask... maskEnums) {
      for(HierarchyMask m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(infos);
    }
  }
}
