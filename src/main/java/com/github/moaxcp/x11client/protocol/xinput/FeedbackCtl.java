package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XStruct;

import java.io.IOException;

public interface FeedbackCtl extends XStruct {
  static FeedbackCtl readFeedbackCtl(X11Input in) throws IOException {
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
    FeedbackClass ref = FeedbackClass.getByCode(classId);
    if(ref == FeedbackClass.KEYBOARD) {
      return FeedbackCtlKeyboard.readFeedbackCtlKeyboard(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.POINTER) {
      return FeedbackCtlPointer.readFeedbackCtlPointer(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.STRING) {
      return FeedbackCtlString.readFeedbackCtlString(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.INTEGER) {
      return FeedbackCtlInteger.readFeedbackCtlInteger(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.LED) {
      return FeedbackCtlLed.readFeedbackCtlLed(classId, feedbackId, len, in);
    }
    if(ref == FeedbackClass.BELL) {
      return FeedbackCtlBell.readFeedbackCtlBell(classId, feedbackId, len, in);
    }
    throw new IllegalStateException("Could not find class for " + ref);
  }
}
