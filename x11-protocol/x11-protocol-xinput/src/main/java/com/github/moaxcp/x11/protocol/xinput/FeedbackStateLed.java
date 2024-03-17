package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackStateLed implements FeedbackState {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte feedbackId;

  private short len;

  private int ledMask;

  private int ledValues;

  public static FeedbackStateLed readFeedbackStateLed(byte classId, byte feedbackId, short len,
      X11Input in) throws IOException {
    FeedbackStateLed.FeedbackStateLedBuilder javaBuilder = FeedbackStateLed.builder();
    int ledMask = in.readCard32();
    int ledValues = in.readCard32();
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.ledMask(ledMask);
    javaBuilder.ledValues(ledValues);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writeCard32(ledMask);
    out.writeCard32(ledValues);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FeedbackStateLedBuilder {
    public FeedbackStateLed.FeedbackStateLedBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public FeedbackStateLed.FeedbackStateLedBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
