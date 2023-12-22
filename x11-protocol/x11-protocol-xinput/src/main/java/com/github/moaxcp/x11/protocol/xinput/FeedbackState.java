package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.XStruct;

import java.io.IOException;

public interface FeedbackState extends XStruct {
  static FeedbackState readFeedbackState(X11Input in) throws IOException {
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
    FeedbackClass ref = FeedbackClass.getByCode(classId);
    if(ref == FeedbackClass.KEYBOARD) {
      return FeedbackStateKeyboard.readFeedbackStateKeyboard(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.POINTER) {
      return FeedbackStatePointer.readFeedbackStatePointer(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.STRING) {
      return FeedbackStateString.readFeedbackStateString(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.INTEGER) {
      return FeedbackStateInteger.readFeedbackStateInteger(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.LED) {
      return FeedbackStateLed.readFeedbackStateLed(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.BELL) {
      return FeedbackStateBell.readFeedbackStateBell(classId, feedbackId, len, in);
    }
    throw new IllegalStateException("Could not find class for " + ref);
  }
}
