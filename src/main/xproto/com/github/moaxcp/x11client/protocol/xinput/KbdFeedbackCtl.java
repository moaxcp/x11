package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KbdFeedbackCtl implements XStruct, XinputObject {
  private byte classId;

  private byte feedbackId;

  private short len;

  private byte key;

  private byte autoRepeatMode;

  private byte keyClickPercent;

  private byte bellPercent;

  private short bellPitch;

  private short bellDuration;

  private int ledMask;

  private int ledValues;

  public static KbdFeedbackCtl readKbdFeedbackCtl(X11Input in) throws IOException {
    KbdFeedbackCtl.KbdFeedbackCtlBuilder javaBuilder = KbdFeedbackCtl.builder();
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
    byte key = in.readCard8();
    byte autoRepeatMode = in.readCard8();
    byte keyClickPercent = in.readInt8();
    byte bellPercent = in.readInt8();
    short bellPitch = in.readInt16();
    short bellDuration = in.readInt16();
    int ledMask = in.readCard32();
    int ledValues = in.readCard32();
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.key(key);
    javaBuilder.autoRepeatMode(autoRepeatMode);
    javaBuilder.keyClickPercent(keyClickPercent);
    javaBuilder.bellPercent(bellPercent);
    javaBuilder.bellPitch(bellPitch);
    javaBuilder.bellDuration(bellDuration);
    javaBuilder.ledMask(ledMask);
    javaBuilder.ledValues(ledValues);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writeCard8(key);
    out.writeCard8(autoRepeatMode);
    out.writeInt8(keyClickPercent);
    out.writeInt8(bellPercent);
    out.writeInt16(bellPitch);
    out.writeInt16(bellDuration);
    out.writeCard32(ledMask);
    out.writeCard32(ledValues);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class KbdFeedbackCtlBuilder {
    public KbdFeedbackCtl.KbdFeedbackCtlBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public KbdFeedbackCtl.KbdFeedbackCtlBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 20;
    }
  }
}
