package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BellFeedbackState implements XStruct, XinputObject {
  private byte classId;

  private byte feedbackId;

  private short len;

  private byte percent;

  private short pitch;

  private short duration;

  public static BellFeedbackState readBellFeedbackState(X11Input in) throws IOException {
    BellFeedbackState.BellFeedbackStateBuilder javaBuilder = BellFeedbackState.builder();
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
    byte percent = in.readCard8();
    byte[] pad4 = in.readPad(3);
    short pitch = in.readCard16();
    short duration = in.readCard16();
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.percent(percent);
    javaBuilder.pitch(pitch);
    javaBuilder.duration(duration);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writeCard8(percent);
    out.writePad(3);
    out.writeCard16(pitch);
    out.writeCard16(duration);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class BellFeedbackStateBuilder {
    public BellFeedbackState.BellFeedbackStateBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public BellFeedbackState.BellFeedbackStateBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
