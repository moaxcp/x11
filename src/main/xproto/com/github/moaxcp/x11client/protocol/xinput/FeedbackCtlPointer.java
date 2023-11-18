package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackCtlPointer implements FeedbackCtl, XinputObject {
  private byte classId;

  private byte feedbackId;

  private short len;

  private short num;

  private short denom;

  private short threshold;

  public static FeedbackCtlPointer readFeedbackCtlPointer(byte classId, byte feedbackId, short len,
      X11Input in) throws IOException {
    FeedbackCtlPointer.FeedbackCtlPointerBuilder javaBuilder = FeedbackCtlPointer.builder();
    byte[] pad3 = in.readPad(2);
    short num = in.readInt16();
    short denom = in.readInt16();
    short threshold = in.readInt16();
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.num(num);
    javaBuilder.denom(denom);
    javaBuilder.threshold(threshold);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writePad(2);
    out.writeInt16(num);
    out.writeInt16(denom);
    out.writeInt16(threshold);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class FeedbackCtlPointerBuilder {
    public FeedbackCtlPointer.FeedbackCtlPointerBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public FeedbackCtlPointer.FeedbackCtlPointerBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
