package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeFeedbackControl implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 23;

  private int mask;

  private byte deviceId;

  private byte feedbackId;

  @NonNull
  private FeedbackCtl feedback;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeFeedbackControl readChangeFeedbackControl(X11Input in) throws IOException {
    ChangeFeedbackControl.ChangeFeedbackControlBuilder javaBuilder = ChangeFeedbackControl.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int mask = in.readCard32();
    byte deviceId = in.readCard8();
    byte feedbackId = in.readCard8();
    byte[] pad6 = in.readPad(2);
    FeedbackCtl feedback = FeedbackCtl.readFeedbackCtl(in);
    javaBuilder.mask(mask);
    javaBuilder.deviceId(deviceId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.feedback(feedback);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(mask);
    out.writeCard8(deviceId);
    out.writeCard8(feedbackId);
    out.writePad(2);
    feedback.write(out);
  }

  public boolean isMaskEnabled(@NonNull ChangeFeedbackControlMask... maskEnums) {
    for(ChangeFeedbackControlMask m : maskEnums) {
      if(!m.isEnabled(mask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class ChangeFeedbackControlBuilder {
    public boolean isMaskEnabled(@NonNull ChangeFeedbackControlMask... maskEnums) {
      for(ChangeFeedbackControlMask m : maskEnums) {
        if(!m.isEnabled(mask)) {
          return false;
        }
      }
      return true;
    }

    public ChangeFeedbackControl.ChangeFeedbackControlBuilder maskEnable(
        ChangeFeedbackControlMask... maskEnums) {
      for(ChangeFeedbackControlMask m : maskEnums) {
        mask((int) m.enableFor(mask));
      }
      return this;
    }

    public ChangeFeedbackControl.ChangeFeedbackControlBuilder maskDisable(
        ChangeFeedbackControlMask... maskEnums) {
      for(ChangeFeedbackControlMask m : maskEnums) {
        mask((int) m.disableFor(mask));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
