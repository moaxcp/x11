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
public class KeyPressEvent implements XGenericEvent, XinputObject {
  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private short deviceid;

  private int time;

  private int detail;

  private int root;

  private int event;

  private int child;

  private int rootX;

  private int rootY;

  private int eventX;

  private int eventY;

  private short sourceid;

  private int flags;

  @NonNull
  private ModifierInfo mods;

  @NonNull
  private GroupInfo group;

  @NonNull
  private List<Integer> buttonMask;

  @NonNull
  private List<Integer> valuatorMask;

  @NonNull
  private List<Fp3232> axisvalues;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static KeyPressEvent readKeyPressEvent(byte firstEventOffset, boolean sentEvent,
      byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws
      IOException {
    KeyPressEvent.KeyPressEventBuilder javaBuilder = KeyPressEvent.builder();
    short deviceid = in.readCard16();
    int time = in.readCard32();
    int detail = in.readCard32();
    int root = in.readCard32();
    int event = in.readCard32();
    int child = in.readCard32();
    int rootX = in.readInt32();
    int rootY = in.readInt32();
    int eventX = in.readInt32();
    int eventY = in.readInt32();
    short buttonsLen = in.readCard16();
    short valuatorsLen = in.readCard16();
    short sourceid = in.readCard16();
    byte[] pad18 = in.readPad(2);
    int flags = in.readCard32();
    ModifierInfo mods = ModifierInfo.readModifierInfo(in);
    GroupInfo group = GroupInfo.readGroupInfo(in);
    List<Integer> buttonMask = in.readCard32(Short.toUnsignedInt(buttonsLen));
    List<Integer> valuatorMask = in.readCard32(Short.toUnsignedInt(valuatorsLen));
    List<Fp3232> axisvalues = new ArrayList<>(valuatorMask.stream().mapToInt(mapToInt -> mapToInt).sum());
    for(int i = 0; i < valuatorMask.stream().mapToInt(mapToInt -> mapToInt).sum(); i++) {
      axisvalues.add(Fp3232.readFp3232(in));
    }
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.deviceid(deviceid);
    javaBuilder.time(time);
    javaBuilder.detail(detail);
    javaBuilder.root(root);
    javaBuilder.event(event);
    javaBuilder.child(child);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.eventX(eventX);
    javaBuilder.eventY(eventY);
    javaBuilder.sourceid(sourceid);
    javaBuilder.flags(flags);
    javaBuilder.mods(mods);
    javaBuilder.group(group);
    javaBuilder.buttonMask(buttonMask);
    javaBuilder.valuatorMask(valuatorMask);
    javaBuilder.axisvalues(axisvalues);

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
    out.writeCard32(detail);
    out.writeCard32(root);
    out.writeCard32(event);
    out.writeCard32(child);
    out.writeInt32(rootX);
    out.writeInt32(rootY);
    out.writeInt32(eventX);
    out.writeInt32(eventY);
    short buttonsLen = (short) buttonMask.size();
    out.writeCard16(buttonsLen);
    short valuatorsLen = (short) valuatorMask.size();
    out.writeCard16(valuatorsLen);
    out.writeCard16(sourceid);
    out.writePad(2);
    out.writeCard32(flags);
    mods.write(out);
    group.write(out);
    out.writeCard32(buttonMask);
    out.writeCard32(valuatorMask);
    for(Fp3232 t : axisvalues) {
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
    return 80 + 4 * buttonMask.size() + 4 * valuatorMask.size() + XObject.sizeOf(axisvalues);
  }

  public static class KeyPressEventBuilder {
    public boolean isFlagsEnabled(@NonNull KeyEventFlags... maskEnums) {
      for(KeyEventFlags m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public KeyPressEvent.KeyPressEventBuilder flagsEnable(KeyEventFlags... maskEnums) {
      for(KeyEventFlags m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public KeyPressEvent.KeyPressEventBuilder flagsDisable(KeyEventFlags... maskEnums) {
      for(KeyEventFlags m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 80 + 4 * buttonMask.size() + 4 * valuatorMask.size() + XObject.sizeOf(axisvalues);
    }
  }
}
