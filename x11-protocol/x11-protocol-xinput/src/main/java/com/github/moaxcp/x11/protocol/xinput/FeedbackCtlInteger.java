package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackCtlInteger implements FeedbackCtl {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte feedbackId;

  private short len;

  private int intToDisplay;

  public static FeedbackCtlInteger readFeedbackCtlInteger(byte classId, byte feedbackId, short len,
      X11Input in) throws IOException {
    FeedbackCtlInteger.FeedbackCtlIntegerBuilder javaBuilder = FeedbackCtlInteger.builder();
    int intToDisplay = in.readInt32();
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.intToDisplay(intToDisplay);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writeInt32(intToDisplay);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FeedbackCtlIntegerBuilder {
    public FeedbackCtlInteger.FeedbackCtlIntegerBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public FeedbackCtlInteger.FeedbackCtlIntegerBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
