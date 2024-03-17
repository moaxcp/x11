package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackStateBell implements FeedbackState {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte feedbackId;

  private short len;

  private byte percent;

  private short pitch;

  private short duration;

  public static FeedbackStateBell readFeedbackStateBell(byte classId, byte feedbackId, short len,
      X11Input in) throws IOException {
    FeedbackStateBell.FeedbackStateBellBuilder javaBuilder = FeedbackStateBell.builder();
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FeedbackStateBellBuilder {
    public FeedbackStateBell.FeedbackStateBellBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public FeedbackStateBell.FeedbackStateBellBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
