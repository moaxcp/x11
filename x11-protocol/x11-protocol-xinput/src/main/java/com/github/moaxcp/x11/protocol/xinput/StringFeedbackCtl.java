package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class StringFeedbackCtl implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte feedbackId;

  private short len;

  @NonNull
  private IntList keysyms;

  public static StringFeedbackCtl readStringFeedbackCtl(X11Input in) throws IOException {
    StringFeedbackCtl.StringFeedbackCtlBuilder javaBuilder = StringFeedbackCtl.builder();
    byte classId = in.readCard8();
    byte feedbackId = in.readCard8();
    short len = in.readCard16();
    byte[] pad3 = in.readPad(2);
    short numKeysyms = in.readCard16();
    IntList keysyms = in.readCard32(Short.toUnsignedInt(numKeysyms));
    javaBuilder.classId(classId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.len(len);
    javaBuilder.keysyms(keysyms.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
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
