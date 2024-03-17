package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BellFeedbackCtl implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte feedbackId;

  private short len;

  private byte percent;

  private short pitch;

  private short duration;

  public static BellFeedbackCtl readBellFeedbackCtl(X11Input in) throws IOException {
    BellFeedbackCtl.BellFeedbackCtlBuilder javaBuilder = BellFeedbackCtl.builder();
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
    byte percent = in.readInt8();
    byte[] pad4 = in.readPad(3);
    short pitch = in.readInt16();
    short duration = in.readInt16();
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
    out.writeInt8(percent);
    out.writePad(3);
    out.writeInt16(pitch);
    out.writeInt16(duration);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class BellFeedbackCtlBuilder {
    public BellFeedbackCtl.BellFeedbackCtlBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public BellFeedbackCtl.BellFeedbackCtlBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
