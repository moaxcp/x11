package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class StringFeedbackCtl implements XStruct, XinputObject {
  private byte classId;

  private byte feedbackId;

  private short len;

  @NonNull
  private List<Integer> keysyms;

  public static StringFeedbackCtl readStringFeedbackCtl(X11Input in) throws IOException {
    StringFeedbackCtl.StringFeedbackCtlBuilder javaBuilder = StringFeedbackCtl.builder();
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
    byte[] pad3 = in.readPad(2);
    short numKeysyms = in.readCard16();
    List<Integer> keysyms = in.readCard32(Short.toUnsignedInt(numKeysyms));
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.keysyms(keysyms);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writePad(2);
    short numKeysyms = (short) keysyms.size();
    out.writeCard16(numKeysyms);
    out.writeCard32(keysyms);
  }

  @Override
  public int getSize() {
    return 8 + 4 * keysyms.size();
  }

  public static class StringFeedbackCtlBuilder {
    public StringFeedbackCtl.StringFeedbackCtlBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public StringFeedbackCtl.StringFeedbackCtlBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 8 + 4 * keysyms.size();
    }
  }
}
