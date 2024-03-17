package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AttachFormat implements XStruct {
  public static final String PLUGIN_NAME = "dri2";

  private int attachment;

  private int format;

  public static AttachFormat readAttachFormat(X11Input in) throws IOException {
    AttachFormat.AttachFormatBuilder javaBuilder = AttachFormat.builder();
    int attachment = in.readCard32();
    int format = in.readCard32();
    javaBuilder.attachment(attachment);
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(attachment);
    out.writeCard32(format);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AttachFormatBuilder {
    public AttachFormat.AttachFormatBuilder attachment(Attachment attachment) {
      this.attachment = (int) attachment.getValue();
      return this;
    }

    public AttachFormat.AttachFormatBuilder attachment(int attachment) {
      this.attachment = attachment;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
