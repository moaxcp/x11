package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackStateInteger implements FeedbackState, XinputObject {
  private byte classId;

  private byte feedbackId;

  private short len;

  private int resolution;

  private int minValue;

  private int maxValue;

  public static FeedbackStateInteger readFeedbackStateInteger(byte classId, byte feedbackId,
      short len, X11Input in) throws IOException {
    FeedbackStateInteger.FeedbackStateIntegerBuilder javaBuilder = FeedbackStateInteger.builder();
    int resolution = in.readCard32();
    int minValue = in.readInt32();
    int maxValue = in.readInt32();
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.resolution(resolution);
    javaBuilder.minValue(minValue);
    javaBuilder.maxValue(maxValue);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writeCard32(resolution);
    out.writeInt32(minValue);
    out.writeInt32(maxValue);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class FeedbackStateIntegerBuilder {
    public FeedbackStateInteger.FeedbackStateIntegerBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public FeedbackStateInteger.FeedbackStateIntegerBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
