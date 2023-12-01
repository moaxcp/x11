package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.ModMask;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ActionMessageEvent implements XEvent, XkbObject {
  public static final byte NUMBER = 9;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private byte keycode;

  private boolean press;

  private boolean keyEventFollows;

  private byte mods;

  private byte group;

  @NonNull
  private List<Byte> message;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ActionMessageEvent readActionMessageEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    ActionMessageEvent.ActionMessageEventBuilder javaBuilder = ActionMessageEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte keycode = in.readCard8();
    boolean press = in.readBool();
    boolean keyEventFollows = in.readBool();
    byte mods = in.readCard8();
    byte group = in.readCard8();
    List<Byte> message = in.readChar(8);
    byte[] pad11 = in.readPad(10);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.keycode(keycode);
    javaBuilder.press(press);
    javaBuilder.keyEventFollows(keyEventFollows);
    javaBuilder.mods(mods);
    javaBuilder.group(group);
    javaBuilder.message(message);

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
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writeCard8(keycode);
    out.writeBool(press);
    out.writeBool(keyEventFollows);
    out.writeCard8(mods);
    out.writeCard8(group);
    out.writeChar(message);
    out.writePad(10);
    out.writePadAlign(getSize());
  }

  public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(mods)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 24 + 1 * message.size();
  }

  public static class ActionMessageEventBuilder {
    public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mods)) {
          return false;
        }
      }
      return true;
    }

    public ActionMessageEvent.ActionMessageEventBuilder modsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public ActionMessageEvent.ActionMessageEventBuilder modsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
      }
      return this;
    }

    public ActionMessageEvent.ActionMessageEventBuilder group(Group group) {
      this.group = (byte) group.getValue();
      return this;
    }

    public ActionMessageEvent.ActionMessageEventBuilder group(byte group) {
      this.group = group;
      return this;
    }

    public int getSize() {
      return 24 + 1 * message.size();
    }
  }
}
