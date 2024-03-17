package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetFeedbackControlReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private List<FeedbackState> feedbacks;

  public static GetFeedbackControlReply readGetFeedbackControlReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    GetFeedbackControlReply.GetFeedbackControlReplyBuilder javaBuilder = GetFeedbackControlReply.builder();
    int length = in.readCard32();
    short numFeedbacks = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<FeedbackState> feedbacks = new ArrayList<>(Short.toUnsignedInt(numFeedbacks));
    for(int i = 0; i < Short.toUnsignedInt(numFeedbacks); i++) {
      feedbacks.add(FeedbackState.readFeedbackState(in));
    }
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.feedbacks(feedbacks);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short numFeedbacks = (short) feedbacks.size();
    out.writeCard16(numFeedbacks);
    out.writePad(22);
    for(FeedbackState t : feedbacks) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(feedbacks);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetFeedbackControlReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(feedbacks);
    }
  }
}
