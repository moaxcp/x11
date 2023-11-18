package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LedFeedbackState implements XStruct, XinputObject {
  private byte classId;

  private byte feedbackId;

  private short len;

  private int ledMask;

  private int ledValues;

  public static LedFeedbackState readLedFeedbackState(X11Input in) throws IOException {
    LedFeedbackState.LedFeedbackStateBuilder javaBuilder = LedFeedbackState.builder();
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
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

  public static class LedFeedbackStateBuilder {
    public LedFeedbackState.LedFeedbackStateBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public LedFeedbackState.LedFeedbackStateBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
