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
public class ButtonPressEvent implements XGenericEvent {
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
  private IntList buttonMask;

  @NonNull
  private IntList valuatorMask;

  @NonNull
  private ImmutableList<Fp3232> axisvalues;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ButtonPressEvent readButtonPressEvent(byte firstEventOffset, boolean sentEvent,
      byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws
      IOException {
    ButtonPressEvent.ButtonPressEventBuilder javaBuilder = ButtonPressEvent.builder();
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
    IntList buttonMask = in.readCard32(Short.toUnsignedInt(buttonsLen));
    IntList valuatorMask = in.readCard32(Short.toUnsignedInt(valuatorsLen));
    MutableList<Fp3232> axisvalues = Lists.mutable.withInitialCapacity((int) valuatorMask.sum());
    for(int i = 0; i < (int) valuatorMask.sum(); i++) {
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
    javaBuilder.buttonMask(buttonMask.toImmutable());
    javaBuilder.valuatorMask(valuatorMask.toImmutable());
    javaBuilder.axisvalues(axisvalues.toImmutable());

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

  public boolean isFlagsEnabled(@NonNull PointerEventFlags... maskEnums) {
    for(PointerEventFlags m : maskEnums) {
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ButtonPressEventBuilder {
    public boolean isFlagsEnabled(@NonNull PointerEventFlags... maskEnums) {
      for(PointerEventFlags m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public ButtonPressEvent.ButtonPressEventBuilder flagsEnable(PointerEventFlags... maskEnums) {
      for(PointerEventFlags m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public ButtonPressEvent.ButtonPressEventBuilder flagsDisable(PointerEventFlags... maskEnums) {
      for(PointerEventFlags m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 80 + 4 * buttonMask.size() + 4 * valuatorMask.size() + XObject.sizeOf(axisvalues);
    }
  }
}
