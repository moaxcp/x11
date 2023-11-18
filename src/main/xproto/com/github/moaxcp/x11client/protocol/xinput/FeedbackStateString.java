package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class FeedbackStateString implements FeedbackState, XinputObject {
  private byte classId;

  private byte feedbackId;

  private short len;

  private short maxSymbols;

  @NonNull
  private List<Integer> keysyms;

  public static FeedbackStateString readFeedbackStateString(byte classId, byte feedbackId,
      short len, X11Input in) throws IOException {
    FeedbackStateString.FeedbackStateStringBuilder javaBuilder = FeedbackStateString.builder();
    short maxSymbols = in.readCard16();
    short numKeysyms = in.readCard16();
    List<Integer> keysyms = in.readCard32(Short.toUnsignedInt(numKeysyms));
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.maxSymbols(maxSymbols);
    javaBuilder.keysyms(keysyms);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(feedbackId);
    out.writeCard16(len);
    out.writeCard16(maxSymbols);
    short numKeysyms = (short) keysyms.size();
    out.writeCard16(numKeysyms);
    out.writeCard32(keysyms);
  }

  @Override
  public int getSize() {
    return 8 + 4 * keysyms.size();
  }

  public static class FeedbackStateStringBuilder {
    public FeedbackStateString.FeedbackStateStringBuilder classId(FeedbackClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public FeedbackStateString.FeedbackStateStringBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 8 + 4 * keysyms.size();
    }
  }
}
