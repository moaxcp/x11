package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class LeaveEvent implements XGenericEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private short deviceid;

  private int time;

  private short sourceid;

  private byte mode;

  private byte detail;

  private int root;

  private int event;

  private int child;

  private int rootX;

  private int rootY;

  private int eventX;

  private int eventY;

  private boolean sameScreen;

  private boolean focus;

  @NonNull
  private ModifierInfo mods;

  @NonNull
  private GroupInfo group;

  @NonNull
  private List<Integer> buttons;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static LeaveEvent readLeaveEvent(byte firstEventOffset, boolean sentEvent, byte extension,
      short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    LeaveEvent.LeaveEventBuilder javaBuilder = LeaveEvent.builder();
    short deviceid = in.readCard16();
    int time = in.readCard32();
    short sourceid = in.readCard16();
    byte mode = in.readCard8();
    byte detail = in.readCard8();
    int root = in.readCard32();
    int event = in.readCard32();
    int child = in.readCard32();
    int rootX = in.readInt32();
    int rootY = in.readInt32();
    int eventX = in.readInt32();
    int eventY = in.readInt32();
    boolean sameScreen = in.readBool();
    boolean focus = in.readBool();
    short buttonsLen = in.readCard16();
    ModifierInfo mods = ModifierInfo.readModifierInfo(in);
    GroupInfo group = GroupInfo.readGroupInfo(in);
    List<Integer> buttons = in.readCard32(Short.toUnsignedInt(buttonsLen));
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.deviceid(deviceid);
    javaBuilder.time(time);
    javaBuilder.sourceid(sourceid);
    javaBuilder.mode(mode);
    javaBuilder.detail(detail);
    javaBuilder.root(root);
    javaBuilder.event(event);
    javaBuilder.child(child);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.eventX(eventX);
    javaBuilder.eventY(eventY);
    javaBuilder.sameScreen(sameScreen);
    javaBuilder.focus(focus);
    javaBuilder.mods(mods);
    javaBuilder.group(group);
    javaBuilder.buttons(buttons);

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
    out.writeCard16(sourceid);
    out.writeCard8(mode);
    out.writeCard8(detail);
    out.writeCard32(root);
    out.writeCard32(event);
    out.writeCard32(child);
    out.writeInt32(rootX);
    out.writeInt32(rootY);
    out.writeInt32(eventX);
    out.writeInt32(eventY);
    out.writeBool(sameScreen);
    out.writeBool(focus);
    short buttonsLen = (short) buttons.size();
    out.writeCard16(buttonsLen);
    mods.write(out);
    group.write(out);
    out.writeCard32(buttons);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 72 + 4 * buttons.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class LeaveEventBuilder {
    public LeaveEvent.LeaveEventBuilder mode(NotifyMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public LeaveEvent.LeaveEventBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public LeaveEvent.LeaveEventBuilder detail(NotifyDetail detail) {
      this.detail = (byte) detail.getValue();
      return this;
    }

    public LeaveEvent.LeaveEventBuilder detail(byte detail) {
      this.detail = detail;
      return this;
    }

    public int getSize() {
      return 72 + 4 * buttons.size();
    }
  }
}
