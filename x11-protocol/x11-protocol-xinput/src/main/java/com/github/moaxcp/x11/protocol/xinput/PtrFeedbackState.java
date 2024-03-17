package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PtrFeedbackState implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte feedbackId;

  private short len;

  private short accelNum;

  private short accelDenom;

  private short threshold;

  public static PtrFeedbackState readPtrFeedbackState(X11Input in) throws IOException {
    PtrFeedbackState.PtrFeedbackStateBuilder javaBuilder = PtrFeedbackState.builder();
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
    byte[] pad3 = in.readPad(2);
    short accelNum = in.readCard16();
    short accelDenom = in.readCard16();
    short threshold = in.readCard16();
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.accelNum(accelNum);
    javaBuilder.accelDenom(accelDenom);
    javaBuilder.threshold(threshold);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writePad(2);
    out.writeCard16(accelNum);
    out.writeCard16(accelDenom);
    out.writeCard16(threshold);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PtrFeedbackStateBuilder {
    public PtrFeedbackState.PtrFeedbackStateBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public PtrFeedbackState.PtrFeedbackStateBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
