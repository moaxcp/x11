package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class KbdFeedbackState implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte feedbackId;

  private short len;

  private short pitch;

  private short duration;

  private int ledMask;

  private int ledValues;

  private boolean globalAutoRepeat;

  private byte click;

  private byte percent;

  @NonNull
  private List<Byte> autoRepeats;

  public static KbdFeedbackState readKbdFeedbackState(X11Input in) throws IOException {
    KbdFeedbackState.KbdFeedbackStateBuilder javaBuilder = KbdFeedbackState.builder();
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
    short pitch = in.readCard16();
    short duration = in.readCard16();
    int ledMask = in.readCard32();
    int ledValues = in.readCard32();
    boolean globalAutoRepeat = in.readBool();
    byte click = in.readCard8();
    byte percent = in.readCard8();
    byte[] pad10 = in.readPad(1);
    List<Byte> autoRepeats = in.readCard8(32);
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.pitch(pitch);
    javaBuilder.duration(duration);
    javaBuilder.ledMask(ledMask);
    javaBuilder.ledValues(ledValues);
    javaBuilder.globalAutoRepeat(globalAutoRepeat);
    javaBuilder.click(click);
    javaBuilder.percent(percent);
    javaBuilder.autoRepeats(autoRepeats);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writeCard16(pitch);
    out.writeCard16(duration);
    out.writeCard32(ledMask);
    out.writeCard32(ledValues);
    out.writeBool(globalAutoRepeat);
    out.writeCard8(click);
    out.writeCard8(percent);
    out.writePad(1);
    out.writeCard8(autoRepeats);
  }

  @Override
  public int getSize() {
    return 20 + 1 * autoRepeats.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KbdFeedbackStateBuilder {
    public KbdFeedbackState.KbdFeedbackStateBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public KbdFeedbackState.KbdFeedbackStateBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 20 + 1 * autoRepeats.size();
    }
  }
}
