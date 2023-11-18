package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IntegerFeedbackCtl implements XStruct, XinputObject {
  private byte classId;

  private byte feedbackId;

  private short len;

  private int intToDisplay;

  public static IntegerFeedbackCtl readIntegerFeedbackCtl(X11Input in) throws IOException {
    IntegerFeedbackCtl.IntegerFeedbackCtlBuilder javaBuilder = IntegerFeedbackCtl.builder();
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
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

  public static class IntegerFeedbackCtlBuilder {
    public IntegerFeedbackCtl.IntegerFeedbackCtlBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public IntegerFeedbackCtl.IntegerFeedbackCtlBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
