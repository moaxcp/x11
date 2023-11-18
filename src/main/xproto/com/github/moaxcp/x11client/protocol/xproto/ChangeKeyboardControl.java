package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeKeyboardControl implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 102;

  private int valueMask;

  private int keyClickPercent;

  private int bellPercent;

  private int bellPitch;

  private int bellDuration;

  private int led;

  private int ledMode;

  private int key;

  private int autoRepeatMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeKeyboardControl readChangeKeyboardControl(X11Input in) throws IOException {
    ChangeKeyboardControl.ChangeKeyboardControlBuilder javaBuilder = ChangeKeyboardControl.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int valueMask = in.readCard32();
    int keyClickPercent = 0;
    int bellPercent = 0;
    int bellPitch = 0;
    int bellDuration = 0;
    int led = 0;
    int ledMode = 0;
    int key = 0;
    int autoRepeatMode = 0;
    javaBuilder.valueMask(valueMask);
    if(Kb.KEY_CLICK_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      keyClickPercent = in.readInt32();
      javaBuilder.keyClickPercent(keyClickPercent);
    }
    if(Kb.BELL_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      bellPercent = in.readInt32();
      javaBuilder.bellPercent(bellPercent);
    }
    if(Kb.BELL_PITCH.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      bellPitch = in.readInt32();
      javaBuilder.bellPitch(bellPitch);
    }
    if(Kb.BELL_DURATION.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      bellDuration = in.readInt32();
      javaBuilder.bellDuration(bellDuration);
    }
    if(Kb.LED.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      led = in.readCard32();
      javaBuilder.led(led);
    }
    if(Kb.LED_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      ledMode = in.readCard32();
      javaBuilder.ledMode(ledMode);
    }
    if(Kb.KEY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      key = in.readCard32();
      javaBuilder.key(key);
    }
    if(Kb.AUTO_REPEAT_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      autoRepeatMode = in.readCard32();
      javaBuilder.autoRepeatMode(autoRepeatMode);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(valueMask);
    if(Kb.KEY_CLICK_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(keyClickPercent);
    }
    if(Kb.BELL_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(bellPercent);
    }
    if(Kb.BELL_PITCH.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(bellPitch);
    }
    if(Kb.BELL_DURATION.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(bellDuration);
    }
    if(Kb.LED.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(led);
    }
    if(Kb.LED_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(ledMode);
    }
    if(Kb.KEY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(key);
    }
    if(Kb.AUTO_REPEAT_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(autoRepeatMode);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(@NonNull Kb... maskEnums) {
    for(Kb m : maskEnums) {
      if(!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8 + (Kb.KEY_CLICK_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_PITCH.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_DURATION.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.LED.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.LED_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.KEY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.AUTO_REPEAT_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public static class ChangeKeyboardControlBuilder {
    private ChangeKeyboardControl.ChangeKeyboardControlBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(@NonNull Kb... maskEnums) {
      for(Kb m : maskEnums) {
        if(!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private ChangeKeyboardControl.ChangeKeyboardControlBuilder valueMaskEnable(Kb... maskEnums) {
      for(Kb m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private ChangeKeyboardControl.ChangeKeyboardControlBuilder valueMaskDisable(Kb... maskEnums) {
      for(Kb m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder keyClickPercent(int keyClickPercent) {
      this.keyClickPercent = keyClickPercent;
      valueMaskEnable(Kb.KEY_CLICK_PERCENT);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder bellPercent(int bellPercent) {
      this.bellPercent = bellPercent;
      valueMaskEnable(Kb.BELL_PERCENT);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder bellPitch(int bellPitch) {
      this.bellPitch = bellPitch;
      valueMaskEnable(Kb.BELL_PITCH);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder bellDuration(int bellDuration) {
      this.bellDuration = bellDuration;
      valueMaskEnable(Kb.BELL_DURATION);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder led(int led) {
      this.led = led;
      valueMaskEnable(Kb.LED);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder ledMode(int ledMode) {
      this.ledMode = ledMode;
      valueMaskEnable(Kb.LED_MODE);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder ledMode(LedMode ledMode) {
      this.ledMode = (int) ledMode.getValue();
      valueMaskEnable(Kb.LED_MODE);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder key(int key) {
      this.key = key;
      valueMaskEnable(Kb.KEY);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder autoRepeatMode(int autoRepeatMode) {
      this.autoRepeatMode = autoRepeatMode;
      valueMaskEnable(Kb.AUTO_REPEAT_MODE);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder autoRepeatMode(
        AutoRepeatMode autoRepeatMode) {
      this.autoRepeatMode = (int) autoRepeatMode.getValue();
      valueMaskEnable(Kb.AUTO_REPEAT_MODE);
      return this;
    }

    public int getSize() {
      return 8 + (Kb.KEY_CLICK_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_PITCH.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_DURATION.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.LED.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.LED_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.KEY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.AUTO_REPEAT_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }
  }
}
